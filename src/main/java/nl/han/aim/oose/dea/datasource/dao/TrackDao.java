package nl.han.aim.oose.dea.datasource.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.han.aim.oose.dea.datasource.databaseconnection.IDatabaseConnector;
import nl.han.aim.oose.dea.datasource.mapper.TrackMapper;
import nl.han.aim.oose.dea.service.dto.TrackDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class TrackDao {
    private Logger logger = Logger.getLogger(getClass().getName());
    private IDatabaseConnector databaseConnector;

    @Inject
    private TrackMapper trackMapper;

    public TrackDao() {}

    @Inject
    public void setDatabaseConnector(IDatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void setTrackMapper(TrackMapper trackMapper) {
        this.trackMapper = trackMapper;
    }

    public ArrayList<TrackDTO> getTracksForPlaylist(int playlistId) {
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        try (Connection connection = databaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT t.*, pt.offlineAvailable FROM track t " +
                             "JOIN playlist_track pt ON t.id = pt.track_id " +
                             "WHERE pt.playlist_id = ?"
             )) {
            statement.setInt(1, playlistId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tracks.add(trackMapper.mapToDTO(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return tracks;
    }

    public ArrayList<TrackDTO> getAvailableTracks(int playlistId) {
        ArrayList<TrackDTO> tracks = new ArrayList<>();
        try (Connection connection = databaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT t.*, false AS offlineAvailable FROM track t WHERE id NOT IN " +
                             "(SELECT track_id FROM playlist_track WHERE playlist_id = ?)"
             )) {
            statement.setInt(1, playlistId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tracks.add(trackMapper.mapToDTO(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return tracks;
    }

    public ArrayList<TrackDTO> addTrackToPlaylist(int playlistId, TrackDTO track) {
        try (Connection connection = databaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO playlist_track (playlist_id, track_id, offlineAvailable) VALUES (?, ?, ?)"
             )) {
            statement.setInt(1, playlistId);
            statement.setInt(2, track.getId());
            statement.setBoolean(3, track.isOfflineAvailable());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getTracksForPlaylist(playlistId);
    }

    public ArrayList<TrackDTO> removeTrackFromPlaylist(int playlistId, int trackId) {
        try (Connection connection = databaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM playlist_track WHERE playlist_id = ? AND track_id = ?"
             )) {
            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getTracksForPlaylist(playlistId);
    }
}