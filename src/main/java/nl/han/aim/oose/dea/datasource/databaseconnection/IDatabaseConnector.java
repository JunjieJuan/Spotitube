package nl.han.aim.oose.dea.datasource.databaseconnection;

import java.sql.Connection;

public interface IDatabaseConnector {
    Connection connect();
}

