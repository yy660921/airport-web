package airport.web.data.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by Machenike on 2017/12/20.
 * 定义新闻内容类
 */
public class News {

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

    public News(){
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
        TotalNews.addAll((ArrayNode) CustomsVirus.get("NewsList"))
                 .addAll((ArrayNode) CustomsSeized.get("NewsList"))
                 .addAll((ArrayNode) CustomsDrug.get("NewsList"))
                 .addAll((ArrayNode) CustomsEpidemic.get("NewsList"))
                 .addAll((ArrayNode) CustomsSmuggle.get("NewsList"))
                 .addAll((ArrayNode) TongrenPhoenixAirport.get("NewsList"))
                 .addAll((ArrayNode) TongrenCustoms.get("NewsList"))
                 .addAll((ArrayNode) TongrenAirport.get("NewsList"));
    }
}
