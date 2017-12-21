package airport.web.restful.service;


import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import airport.web.data.bean.News;
import airport.web.restful.service.News.NewsService;

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
    }

    public static News Baidu;
    public static News Weixin;

    public static HashSet<String> Media;
    public static HashSet<String> Gzh;

    public static long Xinhua;
    public static long Zhongxin;

    public static long TongrenGongan;
    public static long TongrenWeishenghuo;

}
