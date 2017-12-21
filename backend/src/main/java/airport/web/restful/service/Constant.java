package airport.web.restful.service;


import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.Date;
import java.util.HashSet;

import airport.web.data.bean.News;

/**
 * Created by Machenike on 2017/12/20.
 * 保存程序运行过程中的重要变量
 */
public class Constant {

    public static void initial(){
        Media = new HashSet<>();
        Gzh = new HashSet<>();
        Xinhua = 0;
        Zhongxin = 0;
        TongrenGongan = 0;
        TongrenWeishenghuo = 0;
        LastDay = new Date(0);
    }

    public static News Baidu;
    public static News Weixin;
    public static ArrayNode news;

    public static HashSet<String> Media;
    public static HashSet<String> Gzh;

    public static long Xinhua;
    public static long Zhongxin;

    public static long TongrenGongan;
    public static long TongrenWeishenghuo;

    public static Date LastDay;

}
