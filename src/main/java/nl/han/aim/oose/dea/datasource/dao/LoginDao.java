package nl.han.aim.oose.dea.datasource.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.han.aim.oose.dea.datasource.databaseconnection.DatabaseProperties;
import nl.han.aim.oose.dea.service.dto.login.LoginTokenDTO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class LoginDao {
    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;

    public LoginDao() {}

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public LoginTokenDTO checkUser(String username, String password) {
        try {
            Class.forName(databaseProperties.getDriver());
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                statement.close();
                connection.close();
                return new LoginTokenDTO(foundUsername);
            }

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return null;
    }
}