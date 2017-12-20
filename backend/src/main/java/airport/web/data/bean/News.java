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

/**
 * Created by Machenike on 2017/12/20.
 * 定义新闻内容类
 */
public class News {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ObjectNode CustomsVirus;             //海关+病毒新闻
    private ObjectNode CustomsSeized;            //海关+查获新闻
    private ObjectNode CustomsDrug;              //海关+毒品新闻
    private ObjectNode CustomsEpidemic;          //海关+疫情新闻
    private ObjectNode CustomsSmuggle;           //海关+走私新闻

    private ObjectNode TongrenPhoenixAirport;    //铜仁凤凰机场新闻
    private ObjectNode TongrenCustoms;           //铜仁海关新闻
    private ObjectNode TongrenAirport;           //铜仁机场新闻

    private long Total;
    private ArrayNode TotalNews;

    private ObjectNode Count;

    public News(){
        TotalNews = new ObjectMapper().createArrayNode();
        Count = new ObjectMapper().createObjectNode();
        GregorianCalendar gc=new GregorianCalendar();
        System.out.println(new Date());
        try {
            gc.setTime(dateFormat.parse(dateFormat.format(new Date())));
            gc.add(5,-15);
        }catch (Exception e){

        }
        for(int i=0;i<15;i++){
            Count.put(dateFormat.format(gc.getTime()),0);
            gc.add(5,1);
        }
        Total = 0;
    }

    public ObjectNode getCustomsVirus() {
        return CustomsVirus;
    }

    public void setCustomsVirus(ObjectNode customsVirus) {
        CustomsVirus = customsVirus;
    }

    public ObjectNode getCustomsSeized() {
        return CustomsSeized;
    }

    public void setCustomsSeized(ObjectNode customsSeized) {
        CustomsSeized = customsSeized;
    }

    public ObjectNode getCustomsDrug() {
        return CustomsDrug;
    }

    public void setCustomsDrug(ObjectNode customsDrug) {
        CustomsDrug = customsDrug;
    }

    public ObjectNode getCustomsEpidemic() {
        return CustomsEpidemic;
    }

    public void setCustomsEpidemic(ObjectNode customsEpidemic) {
        CustomsEpidemic = customsEpidemic;
    }

    public ObjectNode getCustomsSmuggle() {
        return CustomsSmuggle;
    }

    public void setCustomsSmuggle(ObjectNode customsSmuggle) {
        CustomsSmuggle = customsSmuggle;
    }

    public ObjectNode getTongrenPhoenixAirport() {
        return TongrenPhoenixAirport;
    }

    public void setTongrenPhoenixAirport(ObjectNode tongrenPhoenixAirport) {
        TongrenPhoenixAirport = tongrenPhoenixAirport;
    }

    public ObjectNode getTongrenCustoms() {
        return TongrenCustoms;
    }

    public void setTongrenCustoms(ObjectNode tongrenCustoms) {
        TongrenCustoms = tongrenCustoms;
    }

    public ObjectNode getTongrenAirport() {
        return TongrenAirport;
    }

    public void setTongrenAirport(ObjectNode tongrenAirport) {
        TongrenAirport = tongrenAirport;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = total;
    }

    public void Summary(){
        TotalNews = new ObjectMapper().createArrayNode();
        TotalNews.addAll((ArrayNode) CustomsVirus.get("NewsList"))
                 .addAll((ArrayNode) CustomsSeized.get("NewsList"))
                 .addAll((ArrayNode) CustomsDrug.get("NewsList"))
                 .addAll((ArrayNode) CustomsEpidemic.get("NewsList"))
                 .addAll((ArrayNode) CustomsSmuggle.get("NewsList"))
                 .addAll((ArrayNode) TongrenPhoenixAirport.get("NewsList"))
                 .addAll((ArrayNode) TongrenCustoms.get("NewsList"))
                 .addAll((ArrayNode) TongrenAirport.get("NewsList"));
        Total = CustomsVirus.get("TotalCount").asLong()
            +   CustomsSeized.get("TotalCount").asLong()
            +   CustomsDrug.get("TotalCount").asLong()
            +   CustomsEpidemic.get("TotalCount").asLong()
            +   CustomsSmuggle.get("TotalCount").asLong()
            +   TongrenPhoenixAirport.get("TotalCount").asLong()
            +   TongrenCustoms.get("TotalCount").asLong()
            +   TongrenAirport.get("TotalCount").asLong();
        Count();
    }

    public LinkedList<JsonNode> getNews(){
        int num = 9 + new Random().nextInt(4);
        LinkedList<JsonNode> news = new LinkedList<>();
        for(int i=0;i<num;i++){
            news.add(TotalNews.get(new Random().nextInt(TotalNews.size()-1)));
        }
        return news;
    }

    private void Count(){
        JsonNode news;
        for(int i=0;i<TotalNews.size();i++){
            news = TotalNews.get(i);
            if(news.has("date")){
                try {
                    System.out.println(news.get("date").toString());
                    if (Count.has(dateFormat.format(dateFormat.parse(news.get("date").toString())))){
                        Count.put(dateFormat.format(dateFormat.parse(news.get("date").toString())),
                            (Count.get(dateFormat.format(dateFormat.parse(news.get("date").toString()))).asLong() + 1));
                    }
                }catch (Exception e){
                    ;
                }
            }
        }
    }

    public JsonNode getCount(){
        return Count;
    }
}
