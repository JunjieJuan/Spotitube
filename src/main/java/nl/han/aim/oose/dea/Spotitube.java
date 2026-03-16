package nl.han.aim.oose.dea;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import nl.han.aim.oose.dea.datasource.databaseconnection.DatabaseProperties;

@ApplicationPath("/")
public class Spotitube extends Application {
    public static void main(String[] args) {
        DatabaseProperties database = new DatabaseProperties();
    }
}
