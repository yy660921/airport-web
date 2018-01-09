package airport.web.data.bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Pattern;

import airport.web.restful.service.Constant;

/**
 * Created by Machenike on 2017/12/20.
 * 定义新闻内容类
 */
public class News {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private long Total;

    private ObjectNode Count;
    private static int CountDays = 365;

    public News(){
        Count = new ObjectMapper().createObjectNode();
        GregorianCalendar gc=new GregorianCalendar();
        try {
            gc.setTime(dateFormat.parse(dateFormat.format(new Date())));
            gc.add(5,-CountDays);
        }catch (Exception e){

        }
        for(int i=0;i<CountDays;i++){
            Count.put(dateFormat.format(gc.getTime()),0);
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
            if (Count.has(dateFormat.format(d))) {
                Count.put(dateFormat.format(d), (Count.get(dateFormat.format(d)).asLong() + 1));
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
        return Count.get(dateFormat.format(Constant.LastDay)).asInt();
    }

    public int getDays(){
        GregorianCalendar gc=new GregorianCalendar();
        int Now = 0;
        int Max = 1;
        try {
            gc.setTime(dateFormat.parse(dateFormat.format(Constant.LastDay)));
            gc.add(5,-20);
        }catch (Exception e){

        }
        for(int i=0;i<20;i++){
            if(Max<Count.get(dateFormat.format(gc.getTime())).asInt()){
                Max = Count.get(dateFormat.format(gc.getTime())).asInt();
            }
            if(i==19){
                Now = Count.get(dateFormat.format(gc.getTime())).asInt();
            }
            gc.add(5,1);
        }
        return (int)(Now/(Max*1.2)*100);
    }
}
