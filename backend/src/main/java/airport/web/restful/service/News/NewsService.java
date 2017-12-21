package airport.web.restful.service.News;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import airport.web.data.bean.News;
import airport.web.restful.service.Constant;

import static airport.web.restful.service.Constant.initial;

/**
 * Created by Machenike on 2017/12/20.
 * 读取新闻Json文件
 */
public class NewsService implements Runnable{

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Pattern DataPattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
    private static Pattern TimePattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}");

    private static ArrayNode ReadFile(String FilePath){
        boolean CountTotal = true;
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode NewsList = objectMapper.createArrayNode();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(FilePath), "UTF-8"));
            String line;
            while((line=in.readLine())!=null){
                try {
                    JsonNode News = objectMapper.readTree(line.replace("key_words","keyword"));
                    if(CountTotal){
                        if (News.has("gzh")) {
                            Constant.Gzh.add(News.get("gzh").asText());
                            if (News.get("gzh").asText().equals("铜仁微生活")) {
                                Constant.TongrenWeishenghuo += 1;
                            } else if (News.get("gzh").asText().equals("铜仁公安")) {
                                Constant.TongrenGongan += 1;
                            }
                        } else if (News.has("source")) {
                            Constant.Media.add(News.get("source").asText());
                            if (News.get("source").asText().equals("新华网")) {
                                Constant.Xinhua += 1;
                            } else if (News.get("source").asText().equals("中新网")) {
                                Constant.Zhongxin += 1;
                            }
                        }
                        if (News.has("date")) {
                            Date d = new Date(0);
                            if (DataPattern.matcher(News.get("date").toString().replace("\"", ""))
                                .find()) {
                                d = dateFormat.parse(News.get("date").toString().replace("\"", ""));
                            } else if (TimePattern
                                .matcher(News.get("date").toString().replace("\"", "")).find()) {
                                d = timeFormat.parse(News.get("date").toString().replace("\"", ""));
                            }
                            if (d.after(Constant.LastDay) && d.before(new Date())) {
                                Constant.LastDay = d;
                            }
                        }
                    }
                    else {
                        if (News.has("date")) {
                            Date d = new Date(0);
                            if (DataPattern.matcher(News.get("date").toString().replace("\"", ""))
                                .find()) {
                                d = dateFormat.parse(News.get("date").toString().replace("\"", ""));
                            } else if (TimePattern
                                .matcher(News.get("date").toString().replace("\"", "")).find()) {
                                d = timeFormat.parse(News.get("date").toString().replace("\"", ""));
                            }
                            if (d.after(Constant.LastDay) && d.before(new Date())) {
                                Constant.initial();
                                Constant.LastDay = d;
                            }
                            if (d.equals(Constant.LastDay)) {
                                if (News.has("gzh")) {
                                    Constant.Gzh.add(News.get("gzh").asText());
                                    if (News.get("gzh").asText().equals("铜仁微生活")) {
                                        Constant.TongrenWeishenghuo += 1;
                                    } else if (News.get("gzh").asText().equals("铜仁公安")) {
                                        Constant.TongrenGongan += 1;
                                    }
                                } else if (News.has("source")) {
                                    Constant.Media.add(News.get("source").asText());
                                    if (News.get("source").asText().equals("新华网")) {
                                        Constant.Xinhua += 1;
                                    } else if (News.get("source").asText().equals("中新网")) {
                                        Constant.Zhongxin += 1;
                                    }
                                }
                            }
                        }
                    }
                    ObjectNode AcceptNews = objectMapper.createObjectNode();
                    if(News.has("content")){
                        AcceptNews.put("content", News.get("content").asText());
                    }
                    if(News.has("title")){
                        AcceptNews.put("title", News.get("title").asText());
                    }
                    if(News.has("keyword")){
                        AcceptNews.put("keyword", News.get("keyword").asText());
                    }
                    if(News.has("date")){
                        AcceptNews.put("date", News.get("date").asText());
                    }
                    if(AcceptNews.fieldNames().hasNext()){
                        NewsList.add(AcceptNews);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            }catch (Exception e){

            }
        }
        return NewsList;
    }

    private static void ParseNews(File DirectoryName){
        try {
            for (File SubDictory : DirectoryName.listFiles()) {
                if (SubDictory.getName().equals("baidu")) {
                    Constant.Baidu = new News();
                    if(SubDictory.isDirectory()&&SubDictory.getName().contains("baidu")) {
                        for (File FileName : SubDictory.listFiles()) {
                            if (FileName.getName().contains(".json")) {
                                Constant.Baidu.InsertNews(ReadFile(FileName.getCanonicalFile().getPath()));
                            }
                        }
                    }
                } else if (SubDictory.getName().equals("wx")) {
                    Constant.Weixin = new News();
                    if(SubDictory.isDirectory()&&SubDictory.getName().contains("wx")) {
                        for (File FileName : SubDictory.listFiles()) {
                            if (FileName.getName().contains(".json")) {
                                Constant.Weixin.InsertNews(ReadFile(FileName.getCanonicalFile().getPath()));
                            }
                        }
                    }
                }
                else if(SubDictory.getName().contains(".json")){
                    Constant.news = ReadFile(SubDictory.getCanonicalFile().getPath());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getNewestNews(){
        Date Newest = new Date(0);
        File newest = null;
        try {
            System.out.println(new File("./yuqing").getCanonicalFile());
            File directory = new File("./yuqing");
            for(File subDirectory:directory.listFiles()) {
                System.out.println(subDirectory);
                if (subDirectory.isDirectory() && DataPattern.matcher(subDirectory.getName())
                    .find()) {
                    if (dateFormat.parse(subDirectory.getName()).after(Newest)) {
                        Newest = dateFormat.parse(subDirectory.getName());
                        newest = subDirectory;
                    }
                }
            }
        }catch (Exception e){
            System.out.println("路径错误");
            e.printStackTrace();
        }
        System.out.println(newest);
        ParseNews(newest);
    }

    public void run(){
        initial();
        getNewestNews();
    }

    public static void main(String args[]) throws Exception{
        getNewestNews();
    }
}
