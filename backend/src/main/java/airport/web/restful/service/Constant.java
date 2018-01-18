package airport.web.restful.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by Machenike on 2017/12/20.
 * 保存程序运行过程中的重要变量
 */
public class Constant {

    public static void initial(){
        LastDay = new Date(0);
        ObjectMapper objectMapper = new ObjectMapper();
        yuqing_mediatop1 = objectMapper.createObjectNode();
        yuqing_mediatop2 = objectMapper.createObjectNode();
        yuqing_gzhtop1 = objectMapper.createObjectNode();
        yuqing_gzhtop2 = objectMapper.createObjectNode();
        yuqing_Count = objectMapper.createObjectNode();
    }

    public static String IP;
    public static String Port;
    public static String Database;
    public static String Username;
    public static String Password;

    public static HashSet<String > CityList;

    public static ArrayNode news;

    public static int yuqing_index;
    public static int yuqing_media;
    public static ObjectNode yuqing_mediatop1;
    public static ObjectNode yuqing_mediatop2;
    public static int yuqing_gzh;
    public static ObjectNode yuqing_gzhtop1;
    public static ObjectNode yuqing_gzhtop2;
    public static ObjectNode yuqing_Count;

    public static Date LastDay;

}
