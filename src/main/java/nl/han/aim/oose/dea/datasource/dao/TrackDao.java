package nl.han.aim.oose.dea.datasource.dao;

import nl.han.aim.oose.dea.datasource.databaseconnection.DatabaseProperties;
import nl.han.aim.oose.dea.service.dto.TrackDTO;

import java.util.logging.Logger;

public class TrackDao {

    private Logger logger = Logger.getLogger(getClass().getName());

    private DatabaseProperties databaseProperties;

    public TrackDao(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public TrackDTO findTrack() {

        return null;
    }

}
