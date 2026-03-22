package nl.han.aim.oose.dea.datasource.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.han.aim.oose.dea.datasource.databaseconnection.DatabaseProperties;
import nl.han.aim.oose.dea.service.dto.TrackDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class TrackDao {
    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;

    public TrackDao() {}

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public ArrayList<TrackDTO> getTracksForPlaylist(int playlistId) {
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        try {
            Class.forName(databaseProperties.getDriver());
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT t.* FROM track t " +
                            "JOIN playlist_track pt ON t.id = pt.track_id " +
                            "WHERE pt.playlist_id = ?"
            );
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                tracks.add(mapToTrackDTO(resultSet));
            }

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return tracks;
    }

    public ArrayList<TrackDTO> getAvailableTracks(int playlistId) {
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        try {
            Class.forName(databaseProperties.getDriver());
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM track WHERE id NOT IN " +
                            "(SELECT track_id FROM playlist_track WHERE playlist_id = ?)"
            );
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                tracks.add(mapToTrackDTO(resultSet));
            }

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return tracks;
    }

    public ArrayList<TrackDTO> addTrackToPlaylist(int playlistId, TrackDTO track) {
        try {
            Class.forName(databaseProperties.getDriver());
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO playlist_track (playlist_id, track_id) VALUES (?, ?)"
            );
            statement.setInt(1, playlistId);
            statement.setInt(2, track.getId());
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getTracksForPlaylist(playlistId);
    }

    public ArrayList<TrackDTO> removeTrackFromPlaylist(int playlistId, int trackId) {
        try {
            Class.forName(databaseProperties.getDriver());
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM playlist_track WHERE playlist_id = ? AND track_id = ?"
            );
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getTracksForPlaylist(playlistId);
    }

    private TrackDTO mapToTrackDTO(ResultSet rs) throws SQLException {
        return new TrackDTO(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("performer"),
                rs.getInt("duration"),
                rs.getString("album"),
                rs.getInt("playcount"),
                rs.getString("publicationDate"),
                rs.getString("description"),
                rs.getBoolean("offlineAvailable")
        );
    }
}