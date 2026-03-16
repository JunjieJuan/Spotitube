package nl.han.aim.oose.dea.datasource.dao;

import nl.han.aim.oose.dea.datasource.databaseconnection.DatabaseProperties;
import nl.han.aim.oose.dea.service.dto.login.LoginTokenDTO;
import nl.han.aim.oose.dea.service.dto.UserDTO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDao {
    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;
    private LoginTokenDTO loginTokenDTO;

    public LoginDao(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public LoginTokenDTO checkUser(String Username, String Password) {
        UserDTO user = new UserDTO();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT * from user WHERE Username = ? and Password = ?");
            statement.setString(1, Username);
            statement.setString(2, Password);
            ResultSet resultSet = statement.executeQuery();
            if( resultSet.next()){
                user.setUsername(resultSet.getString("Username"));
                user.setPassword(resultSet.getString("Password"));
            }
            statement.close();
            connection.close();
        } catch (SQLException  | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return loginTokenDTO;
    }


}
