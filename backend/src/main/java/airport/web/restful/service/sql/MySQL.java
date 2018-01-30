package airport.web.restful.service.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    private final static Logger LOG = LoggerFactory.getLogger(MySQL.class);

    static {
        Properties properties = System.getProperties();
        try {
            Constant.IP = properties.getProperty("MySQL.IP");
            Constant.Port = properties.getProperty("MySQL.Port");
            Constant.Database = properties.getProperty("MySQL.Database");
            Constant.Username = properties.getProperty("MySQL.Username");
            Constant.Password = properties.getProperty("MySQL.Password");
        }catch (Exception e) {
            LOG.debug("Properties Read Error!");
        }
        try{
            String FileName = properties.getProperty("Properties.File");
            properties.load(new FileReader(FileName));
            String driver = properties.getProperty("jdbc.driver");
            if(Constant.IP==null) {
                url = properties.getProperty("jdbc.url");
                username = properties.getProperty("jdbc.username");
                password = properties.getProperty("jdbc.password");
            }
            Class.forName(driver).newInstance();
        }catch (Exception e){
            LOG.debug("Using Default MySQL Setting!");
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
            if(properties!=null && url ==null) {
                properties.load(stream);
                String driver = properties.getProperty("jdbc.driver");
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
            }
        } catch (IOException | InstantiationException | IllegalAccessException
            | ClassNotFoundException | NullPointerException e) {
            LOG.debug("Default Setting Reading Error");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("MySQL Connection Error!!!!!\n");
            e.printStackTrace();
        }
        return conn;
    }
}
