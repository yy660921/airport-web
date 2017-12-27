package airport.web.restful.service.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import airport.web.restful.service.Constant;

/**
 * Created by Machenike on 2017/12/15.
 * 数据库连接工具类
 */

public class MySQL {
    private static String url;
    private static String password;
    private static String username;

    static {
        Properties properties = System.getProperties();
        try {
            Constant.IP = properties.getProperty("MySQL.IP");
            Constant.Port = properties.getProperty("MySQL.Port");
            Constant.Database = properties.getProperty("MySQL.Database");
            Constant.Username = properties.getProperty("MySQL.Username");
            Constant.Password = properties.getProperty("MySQL.Password");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        InputStream stream = null;
        properties = null;
        try {
            stream = MySQL.class.getResourceAsStream("/application.properties");
            properties = new Properties();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            properties.load(stream);
            String driver = properties.getProperty("jdbc.driver");
            System.out.println(Constant.IP);
            if(Constant.IP==null) {
                url = properties.getProperty("jdbc.url");
                username = properties.getProperty("jdbc.username");
                password = properties.getProperty("jdbc.password");
            }
            else{
                url = "jdbc:mysql://"+Constant.IP+":"+Constant.Port+"/"+Constant.Database+"?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true";
                username = Constant.Username;
                password = Constant.Password;
            }
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
