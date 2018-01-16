package airport.web.data.bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import airport.web.restful.service.Constant;

/**
 * Created by Machenike on 2017/12/20.
 * 定义新闻内容类
 */
public class News {

    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private long Total;

    private ObjectNode Count;
    private static int CountDays = 1825;

    public News(){
        Count = new ObjectMapper().createObjectNode();
        GregorianCalendar gc=new GregorianCalendar();
        try {
            gc.setTime(dateFormat.get().parse(dateFormat.get().format(new Date())));
            gc.add(5,-CountDays);
        }catch (Exception e){

        }
        for(int i=0;i<CountDays;i++){
            Count.put(dateFormat.get().format(gc.getTime()), 0);
            gc.add(5,1);
        }
        Total = 0;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total += total;
    }

    public void CountDays(Date d){
        try {
            if (Count.has(dateFormat.get().format(d))) {
                Count.put(dateFormat.get().format(d), (Count.get(dateFormat.get().format(d)).asLong() + 1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public long getsize(){
        return Total;
    }

    public JsonNode getCount(){
        return this.Count;
    }

    public int getLastDay(){
        return Count.get(dateFormat.get().format(Constant.LastDay)).asInt();
    }

    public int getDays(){
        GregorianCalendar gc=new GregorianCalendar();
        int Now = 0;
        int Max = 1;
        try {
            gc.setTime(dateFormat.get().parse(dateFormat.get().format(Constant.LastDay)));
            gc.add(5,-20);
        }catch (Exception e){

        }
        for(int i=0;i<20;i++){
            if(Max < Count.get(dateFormat.get().format(gc.getTime())).asInt()){
                Max = Count.get(dateFormat.get().format(gc.getTime())).asInt();
            }
            if(i==19){
                Now = Count.get(dateFormat.get().format(gc.getTime())).asInt();
            }
            gc.add(5,1);
        }
        return (int)(Now/(Max*1.2)*100);
    }
}
