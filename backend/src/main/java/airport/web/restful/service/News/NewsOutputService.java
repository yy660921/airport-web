package airport.web.restful.service.News;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;

import static airport.web.restful.service.sql.Query.getTotalNewsDetail;

/**
 * Created by Machenike on 2018/1/17.
 * 用于定期将新闻内容输出至文件
 */

public class NewsOutputService implements Runnable{
    private static void getTotalNewsFile(){
        System.out.println("Write Total News in File:haiguan_results.json");
        ArrayNode TotalNews = getTotalNewsDetail();
        try {
            File file = new File("./haiguan_results.json");
            if(!file.createNewFile()){
                System.out.println("Create File Error!");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, TotalNews);
            System.out.println(TotalNews.asText().length());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                System.out.println("File Write Finish");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void run(){
        getTotalNewsFile();
    }

    public static void main(String args[]){
        getTotalNewsFile();
    }
}
