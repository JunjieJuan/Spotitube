package nl.han.aim.oose.dea.datasource.dao;

import nl.han.aim.oose.dea.datasource.databaseconnection.IDatabaseConnector;
import nl.han.aim.oose.dea.datasource.mapper.TrackMapper;
import nl.han.aim.oose.dea.service.dto.TrackDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackDaoTest {

    @Mock
    private IDatabaseConnector databaseConnector;

    @Mock
    private TrackMapper trackMapper;

    @InjectMocks
    private TrackDao trackDao;

    @Test
    void getTracksForPlaylist_shouldReturnTracks() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        TrackDTO track = new TrackDTO();

        when(databaseConnector.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, false);
        when(trackMapper.mapToDTO(rs)).thenReturn(track);

        // Act
        ArrayList<TrackDTO> result = trackDao.getTracksForPlaylist(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getTracksForPlaylist_shouldReturnEmpty_whenNoTracks() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(databaseConnector.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        // Act
        ArrayList<TrackDTO> result = trackDao.getTracksForPlaylist(1);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getAvailableTracks_shouldReturnTracks() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        TrackDTO track = new TrackDTO();

        when(databaseConnector.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, false);
        when(trackMapper.mapToDTO(rs)).thenReturn(track);

        // Act
        ArrayList<TrackDTO> result = trackDao.getAvailableTracks(1);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void getTracksForPlaylist_shouldReturnEmpty_whenSQLExceptionOccurs() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);

        when(databaseConnector.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException());

        // Act
        ArrayList<TrackDTO> result = trackDao.getTracksForPlaylist(1);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void addTrackToPlaylist_shouldCallInsertAndReturnTracks() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        PreparedStatement insertStmt = mock(PreparedStatement.class);
        PreparedStatement selectStmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        TrackDTO track = new TrackDTO();

        when(databaseConnector.connect()).thenReturn(connection);

        when(connection.prepareStatement(startsWith("INSERT")))
                .thenReturn(insertStmt);
        when(connection.prepareStatement(startsWith("SELECT")))
                .thenReturn(selectStmt);

        when(selectStmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // Act
        ArrayList<TrackDTO> result = trackDao.addTrackToPlaylist(1, track);

        // Assert
        verify(insertStmt).executeUpdate();
        assertNotNull(result);
    }

    @Test
    void removeTrackFromPlaylist_shouldCallDeleteAndReturnTracks() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        PreparedStatement deleteStmt = mock(PreparedStatement.class);
        PreparedStatement selectStmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(databaseConnector.connect()).thenReturn(connection);

        when(connection.prepareStatement(startsWith("DELETE")))
                .thenReturn(deleteStmt);
        when(connection.prepareStatement(startsWith("SELECT")))
                .thenReturn(selectStmt);

        when(selectStmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // Act
        ArrayList<TrackDTO> result = trackDao.removeTrackFromPlaylist(1, 1);

        // Assert
        verify(deleteStmt).executeUpdate();
        assertNotNull(result);
    }
}