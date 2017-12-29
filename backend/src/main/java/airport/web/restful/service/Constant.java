package airport.web.restful.service;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import airport.web.data.bean.News;

/**
 * Created by Machenike on 2017/12/20.
 * 保存程序运行过程中的重要变量
 */
public class Constant {

    public static void initial(){
        Media = new HashMap<>();
        Gzh = new HashMap<>();
        LastDay = new Date(0);
    }

    public static String IP;
    public static String Port;
    public static String Database;
    public static String Username;
    public static String Password;

    public static HashSet<String > CityList;

    public static News Baidu;
    public static News Weixin;
    public static ArrayNode news;

    public static HashMap<String, Integer> Media;
    public static HashMap<String, Integer> Gzh;
    public static List<Map.Entry<String,Integer>> MediaList;
    public static List<Map.Entry<String,Integer>> GzhList;

    public static Date LastDay;

}
