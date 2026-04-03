package nl.han.aim.oose.dea.datasource.dao;

import nl.han.aim.oose.dea.datasource.databaseconnection.IDatabaseConnector;
import nl.han.aim.oose.dea.datasource.mapper.PlaylistMapper;
import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import nl.han.aim.oose.dea.service.dto.PlaylistsResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistDaoTest {

    @Mock
    private IDatabaseConnector databaseConnector;

    @Mock
    private PlaylistMapper playlistMapper;

    @InjectMocks
    private PlaylistDao playlistDao;

    // Herbruikbaar tegen dubbele code
    private void mockPlaylistQueries(Connection connection, PlaylistDTO playlistDTO, int totalLength) throws SQLException {
        // Arrange
        PreparedStatement userStmt = mock(PreparedStatement.class);
        PreparedStatement playlistStmt = mock(PreparedStatement.class);
        PreparedStatement lengthStmt = mock(PreparedStatement.class);

        ResultSet userRs = mock(ResultSet.class);
        ResultSet playlistRs = mock(ResultSet.class);
        ResultSet lengthRs = mock(ResultSet.class);

        // User
        when(connection.prepareStatement(startsWith("SELECT id FROM users"))).thenReturn(userStmt);
        when(userStmt.executeQuery()).thenReturn(userRs);
        when(userRs.next()).thenReturn(true);
        when(userRs.getInt("id")).thenReturn(1);

        // Playlists
        when(connection.prepareStatement("SELECT * FROM playlist")).thenReturn(playlistStmt);
        when(playlistStmt.executeQuery()).thenReturn(playlistRs);
        when(playlistRs.next()).thenReturn(true, false);
        when(playlistMapper.mapToDTO(playlistRs)).thenReturn(playlistDTO);
        when(playlistRs.getInt("user_id")).thenReturn(1);

        // Total length
        when(connection.prepareStatement(startsWith("SELECT COALESCE"))).thenReturn(lengthStmt);
        when(lengthStmt.executeQuery()).thenReturn(lengthRs);
        when(lengthRs.next()).thenReturn(true);
        when(lengthRs.getInt("totalLength")).thenReturn(totalLength);
    }

    @Test
    void getAllPlaylists_shouldReturnPlaylists_whenDataExists() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        when(databaseConnector.connect()).thenReturn(connection);
        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Test", false);
        mockPlaylistQueries(connection, playlistDTO, 500);

        // Act
        PlaylistsResponseDTO result = playlistDao.getAllPlaylists("token");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPlaylists().size());
        assertEquals(500, result.getLength());
        assertTrue(result.getPlaylists().get(0).isOwner());
    }

    @Test
    void getAllPlaylists_shouldReturnEmpty_whenNoPlaylistsExist() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        when(databaseConnector.connect()).thenReturn(connection);

        PreparedStatement userStmt = mock(PreparedStatement.class);
        PreparedStatement playlistStmt = mock(PreparedStatement.class);
        PreparedStatement lengthStmt = mock(PreparedStatement.class);
        ResultSet userRs = mock(ResultSet.class);
        ResultSet playlistRs = mock(ResultSet.class);
        ResultSet lengthRs = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(userStmt, playlistStmt, lengthStmt);
        when(userStmt.executeQuery()).thenReturn(userRs);
        when(userRs.next()).thenReturn(true);
        when(userRs.getInt("id")).thenReturn(1);
        when(playlistStmt.executeQuery()).thenReturn(playlistRs);
        when(playlistRs.next()).thenReturn(false);
        when(lengthStmt.executeQuery()).thenReturn(lengthRs);
        when(lengthRs.next()).thenReturn(true);
        when(lengthRs.getInt("totalLength")).thenReturn(0);

        // Act
        PlaylistsResponseDTO result = playlistDao.getAllPlaylists("token");

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getPlaylists().size());
        assertEquals(0, result.getLength());
    }

    @Test
    void addPlaylist_shouldReturnUpdatedPlaylists() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        when(databaseConnector.connect()).thenReturn(connection);

        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Test", false);
        PlaylistDTO playlist = new PlaylistDTO(1, "New Playlist", true);

        PreparedStatement insertStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(startsWith("INSERT"))).thenReturn(insertStatement);

        mockPlaylistQueries(connection, playlistDTO, 0);

        // Act
        PlaylistsResponseDTO result = playlistDao.addPlaylist(playlist, "test-token");

        // Assert
        verify(insertStatement).executeUpdate();
        assertNotNull(result);
    }

    @Test
    void deletePlaylist_shouldReturnUpdatedPlaylists() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        when(databaseConnector.connect()).thenReturn(connection);

        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Test Playlist", true);

        PreparedStatement deleteStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(startsWith("DELETE"))).thenReturn(deleteStatement);

        mockPlaylistQueries(connection, playlistDTO, 300);

        // Act
        PlaylistsResponseDTO result = playlistDao.deletePlaylist(1, "test-token");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPlaylists().size());
        assertEquals(300, result.getLength());
        verify(deleteStatement).executeUpdate();
    }

    @Test
    void editPlaylist_shouldReturnUpdatedPlaylists() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        when(databaseConnector.connect()).thenReturn(connection);

        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Updated Playlist", true);
        PlaylistDTO playlist = new PlaylistDTO(1, "Updated Playlist", true);

        PreparedStatement updateStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(startsWith("UPDATE"))).thenReturn(updateStatement);

        mockPlaylistQueries(connection, playlistDTO, 300);

        // Act
        PlaylistsResponseDTO result = playlistDao.editPlaylist(1, playlist, "test-token");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPlaylists().size());
        assertEquals(300, result.getLength());
        verify(updateStatement).executeUpdate();
    }
}