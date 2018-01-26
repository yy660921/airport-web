package airport.web.restful.controller.daily.CustomsController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import airport.web.restful.service.Constant;
import airport.web.restful.service.News.NewsOutputService;
import airport.web.restful.service.News.NewsService;

import static airport.web.restful.service.sql.Query.getNewsDetail;
import static airport.web.restful.service.sql.Query.getWeeklyCountNum;

/**
 * Created by Machenike on 2017/12/20.
 * 新闻内容接口
 * /api/getTotalNews接口下载haiguan_results.json新闻文件
 * /api/getNews接口从所有新闻中随机返回10条按时间递减排序的数据
 * /api/getCount接口获得新闻时间范围统计
 */
@Controller
public class NewsController {
    static{
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Constant.CityList = new HashSet<>();
        executor.scheduleAtFixedRate(
            new NewsService(),
            0,
            1,
            TimeUnit.MINUTES);
        executor.scheduleAtFixedRate(
            new NewsOutputService(),
            0,
            1,
            TimeUnit.HOURS);
    }
    private final static Logger LOG = LoggerFactory.getLogger(NewsController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getNews",
    })

    public LinkedList<JsonNode> getNews() {
        return getNewsDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,
        produces = {"application/json;charset=UTF-8"}, value = {
        "/api/getTotalNews",
    })

    public ResponseEntity<byte[]> getTotalNews(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "haiguan_results.json");
        String Result = "";
        try{
            File file = new File("./haiguan_results.json");
            Long filelength = file.length();
            byte[] filecontent = new byte[filelength.intValue()];
            FileInputStream in = new FileInputStream(file);
            if(in.read(filecontent)==0){
                LOG.debug("Empty File.");
            }
            in.close();
            Result = new String(filecontent, "utf8");
        }catch (Exception e){
            System.out.println("File Read Error");
        }
        return new ResponseEntity<>(Result.getBytes(), headers, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getCount",
    })

    public JsonNode getCount() {
        ObjectNode Count = new ObjectMapper().createObjectNode();
        try {
            ArrayNode Date = new ObjectMapper().createArrayNode();
            ArrayNode count = new ObjectMapper().createArrayNode();
            Iterator<String> dates = Constant.yuqing_Count.fieldNames();
            while(dates.hasNext()){
                String date = dates.next();
                Date.add(date.replaceAll("\\d{4}-",""));
                count.add(Constant.yuqing_Count.get(date));
            }
            Count.replace("Date",Date);
            Count.replace("count",count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Count;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getWeeklyCount",
    })

    public JsonNode getWeeklyCount() {
        return getWeeklyCountNum();
    }
}
