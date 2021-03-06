package airport.web.restful.service.News;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import airport.web.restful.service.Constant;
import airport.web.restful.service.sql.MySQL;

import static airport.web.restful.service.Constant.initial;

/**
 * Created by Machenike on 2017/12/20.
 * 读取新闻Json文件
 */
@Service
public class NewsService implements Runnable{

    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private final static Logger LOG = LoggerFactory.getLogger(NewsService.class);

    private static void UpdateCity(){
        FileWriter writer = null;
        try {
            File f = new File("./WrongCity.txt");
            if(!f.createNewFile()){
                LOG.debug("Create WrongCity.txt Fail");
            }
            writer = new FileWriter("./WrongCity.txt", true);
            if(Constant.CityList == null){
                Constant.CityList = new HashSet<>();
            }
            for (String city : Constant.CityList) {
                writer.write(city + "\n");
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(writer!=null) {
                    writer.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void UpDateNews(){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        String sql = "";
        try{
            conn = MySQL.getConnection();
            sql = "SELECT COUNT(source) AS count,source FROM customs_news WHERE category = \"多媒体\" GROUP BY source ORDER BY COUNT(source) DESC LIMIT 2;";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                Constant.yuqing_mediatop1.put(rs.getString("source"),rs.getInt("count"));
            }
            if(rs.next()){
                Constant.yuqing_mediatop2.put(rs.getString("source"),rs.getInt("count"));
            }
            sql = "SELECT COUNT(source) AS count,source FROM customs_news WHERE category = \"公众号\" GROUP BY source ORDER BY COUNT(source) DESC LIMIT 2;";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                Constant.yuqing_gzhtop1.put(rs.getString("source"),rs.getInt("count"));
            }
            if(rs.next()){
                Constant.yuqing_gzhtop2.put(rs.getString("source"),rs.getInt("count"));
            }
            sql = "SELECT DATE_FORMAT(MAX(FROM_UNIXTIME(time)),\"%Y-%m-%d\") AS date FROM customs_news";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                GregorianCalendar gc=new GregorianCalendar();
                try {
                    gc.setTime(dateFormat.get().parse(rs.getString("date")));
                    gc.add(Calendar.DAY_OF_MONTH, -30);
                }catch (Exception e){
                    LOG.debug("Date Format Error from customs_news :" + rs.getString("date"));
                }
                for(int i=0;i<30;i++){
                    Constant.yuqing_Count.put(dateFormat.get().format(gc.getTime()), 0);
                    gc.add(Calendar.DAY_OF_MONTH,1);
                }
            }
            sql = "SELECT DATE_FORMAT(FROM_UNIXTIME(time),\"%Y-%m-%d\") AS date, COUNT(*) AS count FROM customs_news WHERE DATEDIFF((SELECT DATE_FORMAT(MAX(FROM_UNIXTIME(time)),\"%Y-%m-%d\") FROM customs_news),FROM_UNIXTIME(time)) < 30 GROUP BY DATE_FORMAT(FROM_UNIXTIME(time),\"%Y-%m-%d\") ORDER BY DATE_FORMAT(FROM_UNIXTIME(time),\"%Y-%m-%d\") DESC;";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int Now = 0;
            int Max = 1;
            int Start = 0;
            while(rs.next()){
                Constant.yuqing_Count.put(rs.getString("date"), rs.getInt("count"));
                Now = rs.getInt("count");
                if(Now > Max){
                    Start += 1;
                    if(Start > 10){
                        Max = Now;
                    }
                }
            }
            Constant.yuqing_index = (int)(Now/(Max*1.2)*100);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                if(conn!=null) {
                    conn.close();
                }
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
        }
    }

    public void run(){
        initial();
        UpDateNews();
        UpdateCity();
    }

    public static void main(String args[]) throws Exception{

    }
}
