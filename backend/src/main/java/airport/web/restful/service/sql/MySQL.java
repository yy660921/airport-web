package airport.web.restful.service.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Machenike on 2017/12/15.
 */

public class MySQL {
    private static String url;
    private static String password;
    private static String username;

    static {
        InputStream stream = MySQL.class.getResourceAsStream("/application.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
            String driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");
            Class.forName(driver).newInstance();
        } catch (IOException | InstantiationException | IllegalAccessException
            | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}