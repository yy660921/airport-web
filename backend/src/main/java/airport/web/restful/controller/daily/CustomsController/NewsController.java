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
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import airport.web.restful.service.Constant;
import airport.web.restful.service.News.NewsService;

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
    }
    private static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static Pattern DataPattern = Pattern.compile("^[1-2][0-9][0-9][0-9]-[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9]$");
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
                    if(dateFormat.get().parse(arg0.get("date").asText()).after(dateFormat.get().parse(arg1.get("date").asText()))){
                        return -1;
                    }
                    else if(dateFormat.get().parse(arg0.get("date").asText()).before(dateFormat.get().parse(arg1.get("date").asText()))){
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
    @RequestMapping(method = RequestMethod.GET,
        produces = {"application/json;charset=UTF-8"}, value = {
        "/api/getTotalNews",
    })

    public ResponseEntity<byte[]> getTotalNews(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "haiguan_results.json");
        Date Newest = new Date(0);
        File newest = null;
        String Result = "";
        try {
            File directory = new File("./yuqing");
            for(File subDirectory:directory.listFiles()) {
                if (subDirectory.isDirectory() && DataPattern.matcher(subDirectory.getName())
                    .find()) {
                    if (dateFormat.get().parse(subDirectory.getName()).after(Newest)) {
                        Newest = dateFormat.get().parse(subDirectory.getName());
                        newest = subDirectory;
                    }
                }
            }
        }catch (Exception e){
            System.out.println("路径错误");
            e.printStackTrace();
        }
        try{
            File file = new File(newest.getCanonicalPath() + "/haiguan_results.json");
            Long filelength = file.length();
            byte[] filecontent = new byte[filelength.intValue()];
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            Result = new String(filecontent, "utf8");
        }catch (Exception e){
            System.out.println("File Read Error");
        }
        return new ResponseEntity<byte[]>(Result.getBytes(), headers, HttpStatus.CREATED);
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
                gc.setTime(dateFormat.get().parse(dateFormat.get().format(Constant.LastDay)));
                gc.add(5,-30);
            }catch (Exception e){

            }
            for(int i=0;i<30;i++){
                Date.add(dateFormat.get().format(gc.getTime()).replaceAll("\\d{4}-",""));
                count.add(Baidu.get(dateFormat.get().format(gc.getTime())).asLong() + Weixin.get(dateFormat.get().format(gc.getTime())).asLong());
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
