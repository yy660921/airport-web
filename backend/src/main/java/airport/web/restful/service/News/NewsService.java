package airport.web.restful.service.News;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    private static Pattern DataPattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");

    private static ArrayNode ReadFile(String FilePath){
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode NewsList = objectMapper.createArrayNode();
        try {
            BufferedReader in = new BufferedReader(new FileReader(FilePath));
            String line;
            while((line=in.readLine())!=null){
                try {
                    JsonNode News = objectMapper.readTree(line);
                    if(News.has("gzh")){
                        Constant.Gzh.add(News.get("gzh").asText());
                        if(News.get("gzh").asText().equals("铜仁微生活")){
                            Constant.TongrenWeishenghuo += 1;
                        }
                        else if(News.get("gzh").asText().equals("铜仁公安")){
                            Constant.TongrenGongan += 1;
                        }
                    }
                    else if(News.has("source")){
                        Constant.Gzh.add(News.get("source").asText());
                        if(News.get("source").asText().equals("新华网")){
                            Constant.Xinhua += 1;
                        }
                        else if(News.get("source").asText().equals("中新网")){
                            Constant.Zhongxin += 1;
                        }
                    }
                    NewsList.add(News);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return NewsList;
    }

    private static void ParseNews(File DirectoryName){
        try {
            for (File SubDictory : DirectoryName.listFiles()) {
                News NewsInfo = new News();
                if(SubDictory.isDirectory()&&(SubDictory.getName().contains("wx")||SubDictory.getName().contains("baidu"))) {
                    for (File FileName : SubDictory.listFiles()) {
                        if (FileName.getName().contains(".json")) {
                            NewsInfo.InsertNews(ReadFile(FileName.getCanonicalFile().getPath()));
                        }
                    }
                    if (SubDictory.getName().equals("baidu")) {
                        Constant.Baidu = NewsInfo;
                    } else if (SubDictory.getName().equals("wx")) {
                        Constant.Weixin = NewsInfo;
                    }
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
