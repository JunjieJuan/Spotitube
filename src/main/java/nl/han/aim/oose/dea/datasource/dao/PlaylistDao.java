package nl.han.aim.oose.dea.datasource.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.han.aim.oose.dea.datasource.databaseconnection.DatabaseProperties;
import nl.han.aim.oose.dea.service.dto.PlaylistDTO;
import nl.han.aim.oose.dea.service.dto.PlaylistsResponseDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class PlaylistDao {
    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;

    public PlaylistDao() {}

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public PlaylistsResponseDTO getAllPlaylists(String token) {
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        int totalLength = 0;

        try {
            Class.forName(databaseProperties.getDriver());
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());

            // Haal de user_id op via het token
            PreparedStatement userStatement = connection.prepareStatement(
                    "SELECT id FROM users WHERE token = ?"
            );
            userStatement.setString(1, token);
            ResultSet userResult = userStatement.executeQuery();

            if (userResult.next()) {
                int userId = userResult.getInt("id");

                // Haal alle playlists op
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM playlist"
                );
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int playlistId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    boolean owner = resultSet.getInt("user_id") == userId;

                    playlists.add(new PlaylistDTO(playlistId, name, owner));
                }

                // Bereken de totale lengte van alle tracks
                PreparedStatement lengthStatement = connection.prepareStatement(
                        "SELECT SUM(t.duration) as totalLength " +
                                "FROM track t " +
                                "JOIN playlist_track pt ON t.id = pt.track_id"
                );
                ResultSet lengthResult = lengthStatement.executeQuery();
                if (lengthResult.next()) {
                    totalLength = lengthResult.getInt("totalLength");
                }

                statement.close();
                lengthStatement.close();
            }

            userStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }

        return new PlaylistsResponseDTO(playlists, totalLength);
    }

    public PlaylistsResponseDTO addPlaylist(PlaylistDTO playlist, String token) {
        try {
            Class.forName(databaseProperties.getDriver());
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());

            // Haal user_id op via token
            PreparedStatement userStatement = connection.prepareStatement(
                    "SELECT id FROM users WHERE token = ?"
            );
            userStatement.setString(1, token);
            ResultSet userResult = userStatement.executeQuery();

            if (userResult.next()) {
                int userId = userResult.getInt("id");

                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO playlist (name, user_id) VALUES (?, ?)"
                );
                statement.setString(1, playlist.getName());
                statement.setInt(2, userId);
                statement.executeUpdate();
                statement.close();
            }

            userStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getAllPlaylists(token);
    }

    public PlaylistsResponseDTO deletePlaylist(int id, String token) {
        try {
            Class.forName(databaseProperties.getDriver());
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM playlist WHERE id = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getAllPlaylists(token);
    }

    public PlaylistsResponseDTO editPlaylist(int id, PlaylistDTO playlist, String token) {
        try {
            Class.forName(databaseProperties.getDriver());
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE playlist SET name = ? WHERE id = ?"
            );
            statement.setString(1, playlist.getName());
            statement.setInt(2, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return getAllPlaylists(token);
    }
}