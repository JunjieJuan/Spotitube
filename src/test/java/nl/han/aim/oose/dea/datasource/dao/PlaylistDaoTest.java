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

    @Test
    void getAllPlaylists_shouldReturnPlaylists_whenDataExists() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);

        PreparedStatement userStmt = mock(PreparedStatement.class);
        PreparedStatement playlistStmt = mock(PreparedStatement.class);
        PreparedStatement lengthStmt = mock(PreparedStatement.class);

        ResultSet userRs = mock(ResultSet.class);
        ResultSet playlistRs = mock(ResultSet.class);
        ResultSet lengthRs = mock(ResultSet.class);

        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Test", false);

        when(databaseConnector.connect()).thenReturn(connection);

        // user query
        when(connection.prepareStatement(startsWith("SELECT id FROM users")))
                .thenReturn(userStmt);
        when(userStmt.executeQuery()).thenReturn(userRs);
        when(userRs.next()).thenReturn(true);
        when(userRs.getInt("id")).thenReturn(1);

        // playlist query
        when(connection.prepareStatement("SELECT * FROM playlist"))
                .thenReturn(playlistStmt);
        when(playlistStmt.executeQuery()).thenReturn(playlistRs);
        when(playlistRs.next()).thenReturn(true, false);

        when(playlistMapper.mapToDTO(playlistRs)).thenReturn(playlistDTO);
        when(playlistRs.getInt("user_id")).thenReturn(1);

        // length query
        when(connection.prepareStatement(startsWith("SELECT COALESCE")))
                .thenReturn(lengthStmt);
        when(lengthStmt.executeQuery()).thenReturn(lengthRs);
        when(lengthRs.next()).thenReturn(true);
        when(lengthRs.getInt("totalLength")).thenReturn(500);

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

        PreparedStatement userStmt = mock(PreparedStatement.class);
        PreparedStatement playlistStmt = mock(PreparedStatement.class);
        PreparedStatement lengthStmt = mock(PreparedStatement.class);

        ResultSet userRs = mock(ResultSet.class);
        ResultSet playlistRs = mock(ResultSet.class);
        ResultSet lengthRs = mock(ResultSet.class);

        when(databaseConnector.connect()).thenReturn(connection);

        // user query
        when(connection.prepareStatement(anyString())).thenReturn(userStmt, playlistStmt, lengthStmt);

        when(userStmt.executeQuery()).thenReturn(userRs);
        when(userRs.next()).thenReturn(true);
        when(userRs.getInt("id")).thenReturn(1);

        // geen playlists
        when(playlistStmt.executeQuery()).thenReturn(playlistRs);
        when(playlistRs.next()).thenReturn(false);

        // length
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
    void getAllPlaylists_shouldReturnEmpty_whenSQLExceptionOccurs() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);

        when(databaseConnector.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException());

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
        PreparedStatement userStatement = mock(PreparedStatement.class);
        PreparedStatement insertStatement = mock(PreparedStatement.class);
        ResultSet userResultSet = mock(ResultSet.class);

        PlaylistDTO playlist = new PlaylistDTO(1, "New Playlist", true);

        when(databaseConnector.connect()).thenReturn(connection);

        when(connection.prepareStatement(startsWith("SELECT"))).thenReturn(userStatement);
        when(connection.prepareStatement(startsWith("INSERT"))).thenReturn(insertStatement);
        when(userStatement.executeQuery()).thenReturn(userResultSet);
        when(userResultSet.next()).thenReturn(true);
        when(userResultSet.getInt("id")).thenReturn(1);

        PreparedStatement playlistStmt = mock(PreparedStatement.class);
        ResultSet playlistRs = mock(ResultSet.class);
        PreparedStatement lengthStmt = mock(PreparedStatement.class);
        ResultSet lengthRs = mock(ResultSet.class);

        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Test", false);

        when(connection.prepareStatement("SELECT * FROM playlist")).thenReturn(playlistStmt);
        when(playlistStmt.executeQuery()).thenReturn(playlistRs);
        when(playlistRs.next()).thenReturn(true, false);

        when(playlistMapper.mapToDTO(playlistRs)).thenReturn(playlistDTO);
        when(playlistRs.getInt("user_id")).thenReturn(1);

        when(connection.prepareStatement(startsWith("SELECT COALESCE"))).thenReturn(lengthStmt);
        when(lengthStmt.executeQuery()).thenReturn(lengthRs);
        when(lengthRs.next()).thenReturn(true);
        when(lengthRs.getInt("totalLength")).thenReturn(0);

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
        PreparedStatement deleteStatement = mock(PreparedStatement.class);
        PreparedStatement userStatement = mock(PreparedStatement.class);
        PreparedStatement playlistStmt = mock(PreparedStatement.class);
        PreparedStatement lengthStmt = mock(PreparedStatement.class);

        ResultSet userRs = mock(ResultSet.class);
        ResultSet playlistRs = mock(ResultSet.class);
        ResultSet lengthRs = mock(ResultSet.class);

        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Test Playlist", true);

        when(databaseConnector.connect()).thenReturn(connection);

        // DELETE mock
        when(connection.prepareStatement(startsWith("DELETE"))).thenReturn(deleteStatement);

        // user query mock
        when(connection.prepareStatement(startsWith("SELECT id FROM users"))).thenReturn(userStatement);
        when(userStatement.executeQuery()).thenReturn(userRs);
        when(userRs.next()).thenReturn(true);
        when(userRs.getInt("id")).thenReturn(1);

        // playlists query mock
        when(connection.prepareStatement("SELECT * FROM playlist")).thenReturn(playlistStmt);
        when(playlistStmt.executeQuery()).thenReturn(playlistRs);
        when(playlistRs.next()).thenReturn(true, false);
        when(playlistMapper.mapToDTO(playlistRs)).thenReturn(playlistDTO);
        when(playlistRs.getInt("user_id")).thenReturn(1);

        // total length query mock
        when(connection.prepareStatement(startsWith("SELECT COALESCE"))).thenReturn(lengthStmt);
        when(lengthStmt.executeQuery()).thenReturn(lengthRs);
        when(lengthRs.next()).thenReturn(true);
        when(lengthRs.getInt("totalLength")).thenReturn(300);

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
        PreparedStatement updateStatement = mock(PreparedStatement.class);
        PreparedStatement userStatement = mock(PreparedStatement.class);
        PreparedStatement playlistStmt = mock(PreparedStatement.class);
        PreparedStatement lengthStmt = mock(PreparedStatement.class);

        ResultSet userRs = mock(ResultSet.class);
        ResultSet playlistRs = mock(ResultSet.class);
        ResultSet lengthRs = mock(ResultSet.class);

        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Updated Playlist", true);

        PlaylistDTO playlist = new PlaylistDTO(1, "Updated Playlist", true);

        when(databaseConnector.connect()).thenReturn(connection);

        // UPDATE mock
        when(connection.prepareStatement(startsWith("UPDATE"))).thenReturn(updateStatement);

        // user query mock
        when(connection.prepareStatement(startsWith("SELECT id FROM users"))).thenReturn(userStatement);
        when(userStatement.executeQuery()).thenReturn(userRs);
        when(userRs.next()).thenReturn(true);
        when(userRs.getInt("id")).thenReturn(1);

        // playlists query mock
        when(connection.prepareStatement("SELECT * FROM playlist")).thenReturn(playlistStmt);
        when(playlistStmt.executeQuery()).thenReturn(playlistRs);
        when(playlistRs.next()).thenReturn(true, false);
        when(playlistMapper.mapToDTO(playlistRs)).thenReturn(playlistDTO);
        when(playlistRs.getInt("user_id")).thenReturn(1);

        // total length query mock
        when(connection.prepareStatement(startsWith("SELECT COALESCE"))).thenReturn(lengthStmt);
        when(lengthStmt.executeQuery()).thenReturn(lengthRs);
        when(lengthRs.next()).thenReturn(true);
        when(lengthRs.getInt("totalLength")).thenReturn(300);

        // Act
        PlaylistsResponseDTO result = playlistDao.editPlaylist(1, playlist, "test-token");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPlaylists().size());
        assertEquals(300, result.getLength());
        verify(updateStatement).executeUpdate();
    }
}