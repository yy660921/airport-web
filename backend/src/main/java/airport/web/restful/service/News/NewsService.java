package airport.web.restful.service.News;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import airport.web.data.bean.News;
import airport.web.restful.service.Constant;

/**
 * Created by Machenike on 2017/12/20.
 * 读取新闻Json文件
 */
public class NewsService {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static Pattern DataPattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");

    public static ObjectNode ReadFile(String FilePath){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode NewsMap = objectMapper.createObjectNode();

        try {
            BufferedReader in = new BufferedReader(new FileReader(FilePath));
            String line = "";
            int Count = 0;
            ArrayNode NewsList = objectMapper.createArrayNode();
            while((line=in.readLine())!=null){
                try {
                    JsonNode News = objectMapper.readTree(line);
                    Count ++;
                    NewsList.add(News);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            NewsMap.put("TotalCount",Count);
            NewsMap.replace("NewsList",NewsList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return NewsMap;
    }

    public static void ParseNews(File DirectoryName){
        try {
            for (File SubDictory : DirectoryName.listFiles()) {
                News NewsInfo = new News();
                if(SubDictory.isDirectory()&&(SubDictory.getName().contains("wx")||SubDictory.getName().contains("baidu"))) {
                    for (File FileName : SubDictory.listFiles()) {
                        if (FileName.getName().contains("海关") && FileName.getName()
                            .contains("病毒")) {
                            NewsInfo
                                .setCustomsVirus(ReadFile(FileName.getCanonicalFile().getPath()));
                        } else if (FileName.getName().contains("海关") && FileName.getName()
                            .contains("查获")) {
                            NewsInfo
                                .setCustomsSeized(ReadFile(FileName.getCanonicalFile().getPath()));
                        } else if (FileName.getName().contains("海关") && FileName.getName()
                            .contains("毒品")) {
                            NewsInfo
                                .setCustomsDrug(ReadFile(FileName.getCanonicalFile().getPath()));
                        } else if (FileName.getName().contains("海关") && FileName.getName()
                            .contains("疫情")) {
                            NewsInfo.setCustomsEpidemic(
                                ReadFile(FileName.getCanonicalFile().getPath()));
                        } else if (FileName.getName().contains("海关") && FileName.getName()
                            .contains("走私")) {
                            NewsInfo
                                .setCustomsSmuggle(ReadFile(FileName.getCanonicalFile().getPath()));
                        } else if (FileName.getName().contains("铜仁凤凰机场")) {
                            NewsInfo.setTongrenPhoenixAirport(
                                ReadFile(FileName.getCanonicalFile().getPath()));
                        } else if (FileName.getName().contains("铜仁海关")) {
                            NewsInfo
                                .setTongrenCustoms(ReadFile(FileName.getCanonicalFile().getPath()));
                        } else if (FileName.getName().contains("铜仁机场")) {
                            NewsInfo
                                .setTongrenAirport(ReadFile(FileName.getCanonicalFile().getPath()));
                        }
                    }
                    if (SubDictory.getName().equals("baidu")) {
                        NewsInfo.Summary();
                        Constant.Baidu = NewsInfo;
                    } else if (SubDictory.getName().equals("wx")) {
                        NewsInfo.Summary();
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
            for (File directory : new File("../..").getCanonicalFile().listFiles()) {
                System.out.println(directory);
                if (directory.isDirectory() && DataPattern.matcher(directory.getName()).find()) {
                    if (dateFormat.parse(directory.getName()).after(Newest)) {
                        Newest = dateFormat.parse(directory.getName());
                        newest = directory;
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

    public static void main(String args[]) throws Exception{
        getNewestNews();
    }
}
