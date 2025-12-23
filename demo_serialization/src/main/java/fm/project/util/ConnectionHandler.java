package fm.project.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

// NOTE: this file was copied from github repo 2332_cognizant_java_fs

public class ConnectionHandler {
    private static Connection connection;

    static {
        if (connection == null){
            Properties properties = new Properties();

            try (InputStream input = ConnectionHandler.class.getClassLoader().getResourceAsStream("database.properties")){

                if(input == null){
                    throw new Exception("Unable to find database.properties");
                }else{
                    properties.load(input);
                }

                System.out.println("Loaded properties:");

                // Load JDBC Driver
                Class.forName(properties.getProperty("db.driver"));

                connection = DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password")
                );

            }catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();  // print root cause
                throw new RuntimeException("Failed to load database configuration", e);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
}

        }
    }

    public static Connection getConnection() throws RuntimeException {
        if(connection == null){
            throw new RuntimeException("Connection failed to setup correctly");
        }

        return connection;
    }

}
