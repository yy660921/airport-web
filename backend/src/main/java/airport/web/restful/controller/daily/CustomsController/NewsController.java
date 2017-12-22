package airport.web.restful.controller.daily.CustomsController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import airport.web.restful.service.Constant;
import airport.web.restful.service.News.NewsService;

/**
 * Created by Machenike on 2017/12/20.
 * 新闻内容接口
 * /api/getTotalNews接口返回所有新闻按时间递减排序的数据
 * /api/getNews接口从所有新闻中随机返回10条按时间递减排序的数据
 * /api/getCount接口获得新闻时间范围统计
 */
@Controller
public class NewsController {
    static{
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(
            new NewsService(),
            0,
            1,
            TimeUnit.MINUTES);
    }
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final static Logger LOG = LoggerFactory.getLogger(NewsController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getNews",
    })

    public LinkedList<JsonNode> getNews() {
        LinkedList<JsonNode> news = new LinkedList<>();
        HashSet<Integer> ID = new HashSet<>();
        Random r = new Random();
        while (ID.size()<10){
            ID.add(r.nextInt(Constant.news.size()));
        }
        Iterator<Integer> I = ID.iterator();
        int i=0;
        while(I.hasNext()){
            ObjectNode n = (ObjectNode) Constant.news.get(I.next());
            n.put("ID",i);
            news.add(n);
            i++;
        }
        Collections.sort(news, new Comparator<JsonNode>(){
            public int compare(JsonNode arg0, JsonNode arg1) {
                int result = 0;
                try{
                    if(dateFormat.parse(arg0.get("date").asText()).after(dateFormat.parse(arg1.get("date").asText()))){
                        return -1;
                    }
                    else if(dateFormat.parse(arg0.get("date").asText()).before(dateFormat.parse(arg1.get("date").asText()))){
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }catch (Exception e){

                }
                return result;
            }
        });
        return news;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getTotalNews",
    })

    public LinkedList<JsonNode> getTotalNews() {
        LinkedList<JsonNode> news = new LinkedList<>();
        for(int i=0;i<Constant.news.size();i++){
            ObjectNode n = (ObjectNode) Constant.news.get(i);
            news.add(n);
        }
        Collections.sort(news, new Comparator<JsonNode>(){
            public int compare(JsonNode arg0, JsonNode arg1) {
                int result = 0;
                try{
                    if(dateFormat.parse(arg0.get("date").asText()).after(dateFormat.parse(arg1.get("date").asText()))){
                        return -1;
                    }
                    else if(dateFormat.parse(arg0.get("date").asText()).before(dateFormat.parse(arg1.get("date").asText()))){
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }catch (Exception e){

                }
                return result;
            }
        });
        return news;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getCount",
    })

    public JsonNode getCount() {
        ObjectNode Count = new ObjectMapper().createObjectNode();
        try {
            JsonNode Baidu = Constant.Baidu.getCount();
            JsonNode Weixin = Constant.Weixin.getCount();
            ArrayNode Date = new ObjectMapper().createArrayNode();
            ArrayNode count = new ObjectMapper().createArrayNode();
            GregorianCalendar gc=new GregorianCalendar();
            try {
                gc.setTime(dateFormat.parse(dateFormat.format(Constant.LastDay)));
                gc.add(5,-30);
            }catch (Exception e){

            }
            for(int i=0;i<30;i++){
                Date.add(dateFormat.format(gc.getTime()).replaceAll("\\d{4}-",""));
                count.add(Baidu.get(dateFormat.format(gc.getTime())).asLong() + Weixin.get(dateFormat.format(gc.getTime())).asLong());
                gc.add(5,1);
            }
            Count.replace("Date",Date);
            Count.replace("count",count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Count;
    }
}
