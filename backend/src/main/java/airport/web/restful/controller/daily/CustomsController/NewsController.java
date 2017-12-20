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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import airport.web.restful.service.Constant;
import airport.web.restful.service.News.NewsService;

import static airport.web.restful.service.News.NewsService.getNewestNews;

/**
 * Created by Machenike on 2017/12/20.
 * 新闻内容接口
 * /api/ScanNews接口从本地文件夹中搜索新闻数据
 * /api/getNews接口从所有新闻中随机返回18~26条新闻数据
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

    private final static Logger LOG = LoggerFactory.getLogger(NewsController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getNews",
    })

    public LinkedList<JsonNode> getNews() {
        LinkedList<JsonNode> news = new LinkedList<>();
        try {
            news.addAll(Constant.Baidu.getNews());
            news.addAll(Constant.Weixin.getNews());
        }catch (Exception e){
            LOG.debug("Have to Run ScanNews First");
            e.printStackTrace();
        }
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
            JsonNode Weixin = Constant.Baidu.getCount();
            Iterator<String> fieldNames = Baidu.fieldNames();
            ArrayNode Date = new ObjectMapper().createArrayNode();
            ArrayNode count = new ObjectMapper().createArrayNode();
            while (fieldNames.hasNext()){
                String fieldName = fieldNames.next();
                Date.add(fieldName.replaceAll("\\d{4}-",""));
                count.add(Baidu.get(fieldName).asLong() + Weixin.get(fieldName).asLong());
            }
            Count.replace("Date",Date);
            Count.replace("count",count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Count;
    }
}
