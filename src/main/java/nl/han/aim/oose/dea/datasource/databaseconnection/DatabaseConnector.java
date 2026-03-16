package nl.han.aim.oose.dea.datasource.databaseconnection;

import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
public class DatabaseConnector implements IDatabaseConnector {
    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;



    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Override
    public Connection connect() {
        try {
            return DriverManager.getConnection(databaseProperties.connectionString());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Cant connect to the database " + e);
        }
        return null;
    }
}
