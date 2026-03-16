package nl.han.aim.oose.dea.datasource.databaseconnection;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private Logger logger = Logger.getLogger(getClass().getName());
    private Properties properties;

    public DatabaseProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't access property file database.properties", e);
        }
    }

    public String connectionString()
    {
        return properties.getProperty("connectionString");
    }

//    public Connection maakVerbinding () throws SQLException, ClassNotFoundException {
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        return DriverManager.getConnection("jdbc:sqlserver://LAPTOP-60PARR74:1433;databaseName=spotitube;user=junjie;password=junjiejuan;encrypt=true;trustServerCertifcate=true");
//    }
//
//    public boolean doesUsernameExist(String gebruikersnaam) throws SQLException, ClassNotFoundException {
//
//        Connection connection = maakVerbinding();
//        PreparedStatement statement = connection.prepareStatement("SELECT gebruikersnaam FROM gebruiker WHERE gebruikersnaam=?");
//        statement.setString(1, gebruikersnaam);
//        ResultSet result = statement.executeQuery();
//
//
//        String resultaat = null;
//
//        while(result.next()) {
//            resultaat = result.getString("gebruikersnaam");
//        }
//        connection.close();
//
//        if(resultaat != null && resultaat.equals(gebruikersnaam)) {
//            return true;
//        }
//        else return false;
//
//    }
}
