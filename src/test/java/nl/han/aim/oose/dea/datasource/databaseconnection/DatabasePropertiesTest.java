package nl.han.aim.oose.dea.datasource.databaseconnection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabasePropertiesTest {

    @Test
    void connectionString_shouldNotBeNull() {
        DatabaseProperties props = new DatabaseProperties();

        String result = props.connectionString();

        assertNotNull(result);
    }

    @Test
    void getDriver_shouldNotBeNull() {
        DatabaseProperties props = new DatabaseProperties();

        String result = props.getDriver();

        assertNotNull(result);
    }
}