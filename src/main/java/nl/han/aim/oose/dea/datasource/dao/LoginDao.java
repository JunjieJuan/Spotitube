package nl.han.aim.oose.dea.datasource.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.han.aim.oose.dea.datasource.databaseconnection.IDatabaseConnector;
import nl.han.aim.oose.dea.datasource.mapper.LoginResponseMapper;
import nl.han.aim.oose.dea.service.dto.LoginTokenDTO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class LoginDao {
    private Logger logger = Logger.getLogger(getClass().getName());
    private IDatabaseConnector databaseConnector;
    @Inject
    LoginResponseMapper loginResponseMapper;

    public LoginDao() {}

    @Inject
    public void setDatabaseConnector(IDatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void setLoginResponseMapper(LoginResponseMapper loginResponseMapper) {
        this.loginResponseMapper = loginResponseMapper;
    }

    public LoginTokenDTO checkUser(String username, String password) {
        try (Connection connection = databaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ? AND password = ?"
             )) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LoginTokenDTO loginTokenDTO = loginResponseMapper.mapToDTO(resultSet);

                try (PreparedStatement updateStatement = connection.prepareStatement(
                        "UPDATE users SET token = ? WHERE username = ?"
                )) {
                    updateStatement.setString(1, loginTokenDTO.getToken());
                    updateStatement.setString(2, loginTokenDTO.getUser());
                    updateStatement.executeUpdate();
                }

                return loginTokenDTO;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database", e);
        }
        return null;
    }
}