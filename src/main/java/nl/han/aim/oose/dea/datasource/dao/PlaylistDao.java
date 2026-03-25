package nl.han.aim.oose.dea.datasource.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.han.aim.oose.dea.datasource.databaseconnection.IDatabaseConnector;
import nl.han.aim.oose.dea.datasource.mapper.PlaylistMapper;
import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import nl.han.aim.oose.dea.service.dto.PlaylistsResponseDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class PlaylistDao {
    private Logger logger = Logger.getLogger(getClass().getName());
    private IDatabaseConnector databaseConnector;

    @Inject
    private PlaylistMapper playlistMapper;

    public PlaylistDao() {}

    @Inject
    public void setDatabaseConnector(IDatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public PlaylistsResponseDTO getAllPlaylists(String token) {
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        int totalLength = 0;

        try (Connection connection = databaseConnector.connect()) {
            int userId = getUserIdByToken(connection, token);

            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM playlist"
            )) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    PlaylistDTO playlist = playlistMapper.mapToDTO(rs);
                    playlist.setOwner(rs.getInt("user_id") == userId);
                    playlists.add(playlist);
                }
            }

            try (PreparedStatement lengthStatement = connection.prepareStatement(
                    "SELECT COALESCE(SUM(t.duration), 0) AS totalLength " +
                            "FROM track t " +
                            "JOIN playlist_track pt ON t.id = pt.track_id"
            )) {
                ResultSet rs = lengthStatement.executeQuery();
                if (rs.next()) {
                    totalLength = rs.getInt("totalLength");
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }

        return new PlaylistsResponseDTO(playlists, totalLength);
    }

    public PlaylistsResponseDTO addPlaylist(PlaylistDTO playlist, String token) {
        try (Connection connection = databaseConnector.connect()) {
            int userId = getUserIdByToken(connection, token);

            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO playlist (name, user_id) VALUES (?, ?)"
            )) {
                statement.setString(1, playlist.getName());
                statement.setInt(2, userId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getAllPlaylists(token);
    }

    public PlaylistsResponseDTO deletePlaylist(int id, String token) {
        try (Connection connection = databaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM playlist WHERE id = ?"
             )) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getAllPlaylists(token);
    }

    public PlaylistsResponseDTO editPlaylist(int id, PlaylistDTO playlist, String token) {
        try (Connection connection = databaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE playlist SET name = ? WHERE id = ?"
             )) {
            statement.setString(1, playlist.getName());
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getAllPlaylists(token);
    }

    private int getUserIdByToken(Connection connection, String token) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM users WHERE token = ?"
        )) {
            statement.setString(1, token);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }
}