package airport.web.restful.service.sql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.validation.constraints.Null;

import airport.web.data.bean.CustomsTouristMessage;
import airport.web.data.bean.TourTrips;
import airport.web.restful.service.Constant;

/**
 * Created by Machenike on 2017/12/18.
 * 数据库读取操作类
 */

public class Query {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /*
     * @description: 查询从from到to时间段内的信息
     * @param from: 起始日期
     * @param to:   截止日期
     */
    public static JsonNode getRiskTouristsAndSeizureNumber(Date from, Date to){
        String sql;
        if(from.before(to)) {
            sql =
                "SELECT tourist_warningEvents,seizure_number,createDate FROM customs_top WHERE DATE(createDate) BETWEEN \""
                + dateFormat.format(from) + "\" AND \"" + dateFormat.format(to) + "\"";
        }
        else{
            sql =
                "SELECT tourist_warningEvents,seizure_number,createDate FROM customs_top WHERE DATE(createDate) BETWEEN \""
                + dateFormat.format(to) + "\" AND \"" + dateFormat.format(from) + "\"";
        }
        return getRiskTouristsAndSeizureNumberBySQL(sql);
    }

    /*
     * @description: 查询最新的size条信息
     * @param size: 信息条数
     */
    public static JsonNode getRiskTouristsAndSeizureNumber(){
        String sql = "(SELECT tourist_warningEvents,seizure_number,createDate FROM customs_top ORDER BY createDate DESC LIMIT 10) ORDER BY createDate ASC";
        return getRiskTouristsAndSeizureNumberBySQL(sql);
    }

    /*
     * @description: 查询最新的高风险旅客出发地与目的地统计
     */
    public static JsonNode getDepartureData(){
        String sql = "SELECT warningTourist_departure,warningTourist_destination FROM customs_top WHERE createTime = (SELECT MAX(createTime) FROM customs_top)";
        return getDepartureAndDestinationBySQL(sql);
    }

    /*
     * @description: 查询最新TOP10的高风险旅客名单及风险值
     */
    public static JsonNode getTop10TouristsAndRiskIndex(){
        String sql = "SELECT warningTourist_nameList FROM customs_top WHERE createTime = (SELECT MAX(createTime) FROM customs_top)";
        return getTop10TouristsAndRiskIndexBySQL(sql);
    }

    /*
     * @description: 查询最新的设备24小时运行情况
     */
    public static JsonNode getDeviceCountDist(){
        String sql = "SELECT DeviceCountDist FROM customs_top WHERE createTime = (SELECT MAX(createTime) FROM customs_top)";
        return getDeviceCountDistBySQL(sql);
    }

    /*
     * @description: 查询最新的航线
     */
    public static LinkedList<TourTrips> getAirwayTrip(){
        String sql = "SELECT warningTourist_line FROM customs_top WHERE createTime = (SELECT MAX(createTime) FROM customs_top)";
        return getAirwayTripBySQL(sql);
    }

    /*
     * @description: 查询某条航线
     */
    public static JsonNode getAirLine(Iterator<String> Places){
        String sql = "SELECT cncity,lon,lat FROM city_longlati WHERE cncity in (\"北京\")";
        if(Places.hasNext()) {
            sql =
                "SELECT cncity,lon,lat FROM city_longlati WHERE cncity in (\"" + Places.next();
            while(Places.hasNext()){
                sql += "\",\"" + Places.next();
            }
            sql += "\")";
        }
        return getAirLineBySQL(sql);
    }

    /*
     * @description: 查询date时间的高危旅客信息
     * @param date: 查询日期
     */
    public static LinkedList<CustomsTouristMessage> getTouristMessage(Date date){
        String sql = "SELECT * FROM customs_touristmessage WHERE createDate = \""+ dateFormat.format(date) + "\"";
        return getTouristMessageBySQL(sql);
    }

    /*
     * @description: 查询最新的高危旅客信息
     */
    public static LinkedList<CustomsTouristMessage> getTouristMessage(){
        String sql = "SELECT * FROM customs_touristmessage ORDER BY createTime DESC LIMIT 10";
        return getTouristMessageBySQL(sql);
    }

    /*
     * @description: 查询最新的首页信息
     */
    public static JsonNode getFirstPageCount(){
        String sql = "SELECT riskIndex,warningEvents_number,tourist_warningEvents,chinaTourist_warningEvents,overseasTourist_warningEvents,seizure_number,contraband_number,highTax_number,governpeople_number,devicecount_number FROM customs_index WHERE createTime = (SELECT MAX(createTime) FROM customs_index)";
        return getFirstPageCountBySQL(sql);
    }

    /*
     * @description: 查询最新的首页新闻统计信息
     */
    public static JsonNode getFirstPageNewsCount(){
        String sql = "SELECT COUNT(*) AS count,SUM(count) AS sum,category AS source FROM (SELECT COUNT(*) AS count,source,category FROM customs_news GROUP BY source,category) a GROUP BY category;";
        return getFirstPageNewsCountBySQL(sql);
    }

    /*
     * @description: 查询随机的10条新闻信息
     */
    public static LinkedList<JsonNode> getNewsDetail(){
        String sql = "SELECT * FROM (SELECT * FROM customs_news WHERE id >= ((SELECT MAX(id) FROM customs_news)-(SELECT MIN(id) FROM customs_news)) * RAND() + (SELECT MIN(id) FROM customs_news)  LIMIT 10) AS a ORDER BY time DESC;";
        return getNewsDetailBySQL(sql);
    }

    /*
     * @description: 查询全部的新闻信息
     */
    public static ArrayNode getTotalNewsDetail(){
        String sql = "SELECT title,content,ID,time FROM customs_news";
        return getTotalNewsDetailBySQL(sql);
    }

    private static JsonNode getRiskTouristsAndSeizureNumberBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ArrayNode tourist_warningEvents = objectMapper.createArrayNode();
            ArrayNode seizure_number = objectMapper.createArrayNode();
            ArrayNode createDate = objectMapper.createArrayNode();
            while (rs.next()) {
                tourist_warningEvents.add(rs.getInt("tourist_warningEvents"));
                seizure_number.add(rs.getInt("seizure_number"));
                createDate.add(dateFormat.format(rs.getDate("createDate")).replaceAll("20\\d*-",""));
            }
            result.replace("tourist_warningEvents", tourist_warningEvents);
            result.replace("seizure_number", seizure_number);
            result.replace("createDate", createDate);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static JsonNode getDepartureAndDestinationBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        ObjectMapper OM = new ObjectMapper();
        ObjectNode result = OM.createObjectNode();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                ArrayNode Departures = OM.createArrayNode();
                ArrayNode DepartureCount = OM.createArrayNode();
                JsonNode Departure = OM.readTree(rs.getString("warningTourist_departure"));
                Iterator<String> fieldNames = Departure.fieldNames();
                HashMap<String, Long> Dep = new HashMap<>();
                while(fieldNames.hasNext()){
                    String field = fieldNames.next();
                    Dep.put(field,Long.parseLong(Departure.get(field).toString().replace("\"","")));
                }
                ArrayList<Map.Entry<String, Long>> Depts = new ArrayList<>(Dep.entrySet());
                Collections.sort(Depts, new Comparator<Map.Entry<String,Long>>(){
                    public int compare(Map.Entry<String,Long> arg0, Map.Entry<String,Long> arg1) {
                        return arg0.getValue().compareTo(arg1.getValue());
                    }
                });
                for(Map.Entry<String, Long> Dept:Depts){
                    Departures.add(Dept.getKey());
                    DepartureCount.add(Long.parseLong(Dept.getValue().toString()));
                }
                ArrayNode Destinations = OM.createArrayNode();
                ArrayNode DestinationCount = OM.createArrayNode();
                JsonNode Destination = OM.readTree(rs.getString("warningTourist_destination"));
                fieldNames = Destination.fieldNames();
                HashMap<String, Long> Des = new HashMap<>();
                while(fieldNames.hasNext()){
                    String field = fieldNames.next();
                    Des.put(field,Long.parseLong(Destination.get(field).toString().replace("\"","")));
                }
                ArrayList<Map.Entry<String, Long>> Dests = new ArrayList<>(Des.entrySet());
                Collections.sort(Dests, new Comparator<Map.Entry<String,Long>>(){
                    public int compare(Map.Entry<String,Long> arg0, Map.Entry<String,Long> arg1) {
                        return arg0.getValue().compareTo(arg1.getValue());
                    }
                });
                for(Map.Entry<String, Long> Dest:Dests){
                    Destinations.add(Dest.getKey());
                    DestinationCount.add(Long.parseLong(Dest.getValue().toString()));
                }
                result.replace("Departures",Departures);
                result.replace("DepartureCount",DepartureCount);
                result.replace("Destinations",Destinations);
                result.replace("DestinationCount",DestinationCount);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static JsonNode getTop10TouristsAndRiskIndexBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            JsonNode TouristList = null;
            if (rs.next()) {
                TouristList = objectMapper.readTree(rs.getString("warningTourist_nameList"));
            }
            ArrayNode NameList = objectMapper.createArrayNode();
            ArrayNode RiskIndex = objectMapper.createArrayNode();
            Iterator<String> Passports = TouristList.fieldNames();
            HashMap<String, Double> TouristMap = new HashMap<>();
            while(Passports.hasNext()){
                String Passport = Passports.next();
                TouristMap.put(TouristList.get(Passport).get("姓名").asText(),Double.parseDouble(TouristList.get(Passport).get("风险值").toString().replace("\"","")));
            }
            ArrayList<Map.Entry<String, Double>> Tourists = new ArrayList<>(TouristMap.entrySet());
            Collections.sort(Tourists, new Comparator<Map.Entry<String,Double>>(){
                public int compare(Map.Entry<String,Double> arg0, Map.Entry<String,Double> arg1) {
                    return arg0.getValue().compareTo(arg1.getValue());
                }
            });
            for(Map.Entry<String, Double> Tourist:Tourists){
                NameList.add(Tourist.getKey());
                RiskIndex.add(Tourist.getValue());
            }
            result.replace("TouristName", NameList);
            result.replace("TouristRiskIndex", RiskIndex);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static JsonNode getDeviceCountDistBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                ArrayNode Timeline = objectMapper.createArrayNode();
                ArrayNode Count = objectMapper.createArrayNode();
                JsonNode Device = objectMapper.readTree(rs.getString("DeviceCountDist"));
                Iterator<String> fields= Device.fieldNames();
                while(fields.hasNext()){
                    String field = fields.next();
                    Timeline.add(field);
                    Count.add(Long.parseLong(Device.get(field).toString().replace("\"","")));
                }
                result.replace("TimeLine",Timeline);
                result.replace("Count",Count);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static LinkedList<TourTrips> getAirwayTripBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        LinkedList<TourTrips> Tempresult = new LinkedList<>();
        LinkedList<TourTrips> result = new LinkedList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        HashSet<String> Places = new HashSet<>();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                ArrayNode TravelLine = objectMapper.createArrayNode();
                JsonNode Travel = objectMapper.readTree(rs.getString("warningTourist_line"));
                Iterator<String> Timeline = Travel.fieldNames();
                while (Timeline.hasNext()) {
                    String time = Timeline.next();
                    JsonNode Trav = Travel.get(time);
                    TourTrips Trip = new TourTrips();
                    ObjectNode Departure = objectMapper.createObjectNode();
                    ObjectNode Destination = objectMapper.createObjectNode();
                    Departure.put("CityName", Trav.get("出发地").asText());
                    Destination.put("CityName", Trav.get("目的地").asText());
                    Trip.setDeparture(Departure);
                    Trip.setDestination(Destination);
                    Tempresult.add(Trip);
                    Places.add(Trav.get("出发地").asText());
                    Places.add(Trav.get("目的地").asText());
                }
                JsonNode PlaceDict = getAirLine(Places.iterator());
                for (TourTrips Tourtrip : Tempresult) {
                    ObjectNode Departure = Tourtrip.getDeparture();
                    ObjectNode Destination =  Tourtrip.getDestination();
                    if (PlaceDict.has(Departure.get("CityName").asText()) && PlaceDict
                        .has(Destination.get("CityName").asText())) {
                        Departure.replace("Coordinate",
                                          PlaceDict.get(Departure.get("CityName").asText()));
                        Destination.replace("Coordinate",
                                            PlaceDict
                                                .get(Destination.get("CityName").asText()));
                        Tourtrip.setDeparture(Departure);
                        Tourtrip.setDestination(Destination);
                        result.add(Tourtrip);
                        if(Constant.CityList.contains(Departure.get("CityName").asText())){
                            Constant.CityList.remove(Departure.get("CityName").asText());
                        }
                        if(Constant.CityList.contains(Destination.get("CityName").asText())){
                            Constant.CityList.remove(Destination.get("CityName").asText());
                        }
                    }
                    else{
                        if(!PlaceDict.has(Departure.get("CityName").asText())){
                            Constant.CityList.add(Departure.get("CityName").asText());
                        }
                        if(!PlaceDict.has(Destination.get("CityName").asText())){
                            Constant.CityList.add(Destination.get("CityName").asText());
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static LinkedList<CustomsTouristMessage> getTouristMessageBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        FileWriter writer;
        LinkedList<CustomsTouristMessage> result = new LinkedList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        HashSet<String> Places = new HashSet<>();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CustomsTouristMessage CT = new CustomsTouristMessage();
                CT.setId(rs.getInt("id"));
                CT.setCreateTime(rs.getDate("createTime"));
                CT.setWarningTourist_name(rs.getString("warningTourist_name"));
                CT.setWarningTourist_country(rs.getString("warningTourist_country"));
                CT.setWarningTourist_sex(rs.getString("warningTourist_sex"));
                CT.setWarningTourist_riskIndex(rs.getString("warningTourist_riskIndex"));
                CT.setWarningTourist_passport(rs.getString("warningTourist_passport"));
                CT.setWarningTourist_IDtype(rs.getString("warningTourist_IDtype"));
                CT.setWarningTourist_birthday(rs.getDate("warningTourist_birthday"));
                CT.setWarningTourist_departure(rs.getString("warningTourist_departure"));
                CT.setWarningTourist_destination(rs.getString("warningTourist_destination"));
                CT.setWarningTourist_arrival_number(rs.getInt("warningTourist_arrival_number"));
                CT.setWarningTourist_arrival_risknumber(rs.getInt("warningTourist_arrival_risknumber"));
                ObjectNode categoryList = (ObjectNode) objectMapper.readTree(rs.getString("warningTourist_category"));
                ArrayNode cateList = objectMapper.createArrayNode();
                Iterator<String> categorys = categoryList.fieldNames();
                while(categorys.hasNext()){
                    String category = categorys.next();
                    if(categoryList.get(category).asText().equals("1")){
                        cateList.add(category);
                    }
                }
                CT.setWarningTourist_category(cateList);
                CT.setWarningTourist_flight_number(rs.getString("warningTourist_flight_number"));
                CT.setWarningTourist_flight_type(rs.getString("warningTourist_flight_type"));
                CT.setWarningTourist_time(rs.getDate("warningTourist_time"));
                CT.setWarningTourist_historyTime(rs.getString("warningTourist_historyTime"));
                ArrayNode TravelLine = objectMapper.createArrayNode();
                JsonNode Travel = objectMapper.readTree(rs.getString("warningTourist_place"));
                Iterator<String> Timeline = Travel.fieldNames();
                while(Timeline.hasNext()){
                    String time = Timeline.next();
                    JsonNode Trav = Travel.get(time);
                    ObjectNode Trip = objectMapper.createObjectNode();
                    ObjectNode Departure = objectMapper.createObjectNode();
                    ObjectNode Destination = objectMapper.createObjectNode();
                    Departure.put("CityName",Trav.get("出发地").asText());
                    Destination.put("CityName",Trav.get("目的地").asText());
                    Trip.replace("departure",Departure);
                    Trip.replace("destination",Destination);
                    TravelLine.add(Trip);
                    Places.add(Trav.get("出发地").asText());
                    Places.add(Trav.get("目的地").asText());
                }
                CT.setWarningTourist_place(TravelLine);
                CT.setFellowTourist_list(rs.getString("fellowTourist_list"));
                CT.setCreateDate(rs.getDate("createDate"));
                result.add(CT);
            }
            JsonNode PlaceDict = objectMapper.createObjectNode();
            if(result.size()!=0){
                PlaceDict = getAirLine(Places.iterator());
            }
            for(CustomsTouristMessage Tourist:result){
                JsonNode TravelLines = Tourist.getWarningTourist_place();
                ArrayNode newTravelLines = objectMapper.createArrayNode();
                for(JsonNode TravelLine:TravelLines){
                    ObjectNode Departure = (ObjectNode) TravelLine.get("departure");
                    ObjectNode Destination = (ObjectNode) TravelLine.get("destination");
                    if(PlaceDict.has(Departure.get("CityName").asText())&&PlaceDict.has(Destination.get("CityName").asText())) {
                        Departure.replace("Coordinate",
                                          PlaceDict.get(Departure.get("CityName").asText()));
                        Destination.replace("Coordinate",
                                            PlaceDict.get(Destination.get("CityName").asText()));
                        ObjectNode newTravelLine = objectMapper.createObjectNode();
                        newTravelLine.replace("departure",Departure);
                        newTravelLine.replace("destination",Destination);
                        newTravelLines.add(newTravelLine);
                        if(Constant.CityList.contains(Departure.get("CityName").asText())){
                            Constant.CityList.remove(Departure.get("CityName").asText());
                        }
                        if(Constant.CityList.contains(Destination.get("CityName").asText())){
                            Constant.CityList.remove(Destination.get("CityName").asText());
                        }
                    }
                    else{
                        if(!PlaceDict.has(Departure.get("CityName").asText())){
                            Constant.CityList.add(Departure.get("CityName").asText());
                        }
                        if(!PlaceDict.has(Destination.get("CityName").asText())){
                            Constant.CityList.add(Destination.get("CityName").asText());
                        }
                    }
                }
                Tourist.setWarningTourist_place(newTravelLines);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static JsonNode getAirLineBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                    ArrayNode Coordinate = objectMapper.createArrayNode();
                    Coordinate.add(rs.getDouble("lon"));
                    Coordinate.add(rs.getDouble("lat"));
                    result.replace(rs.getString("cncity"),Coordinate);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static JsonNode getFirstPageCountBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    result.put("riskIndex", Integer.parseInt(rs.getString("riskIndex")));
                }catch (Exception e){
                    result.put("riskIndex", rs.getString("riskIndex"));
                }
                result.put("warningEvents_number", rs.getInt("warningEvents_number"));
                result.put("tourist_warningEvents", rs.getInt("tourist_warningEvents"));
                result.put("chinaTourist_warningEvents", rs.getInt("chinaTourist_warningEvents"));
                result.put("overseasTourist_warningEvents", rs.getInt("overseasTourist_warningEvents"));
                result.put("seizure_number", rs.getInt("seizure_number"));
                result.put("contraband_number", rs.getInt("contraband_number"));
                result.put("highTax_number", rs.getInt("highTax_number"));
                result.put("governpeople_number", rs.getInt("governpeople_number"));
                result.put("devicecount_number", rs.getInt("devicecount_number"));
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static JsonNode getFirstPageNewsCountBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            result.put("yuqing_index", Constant.yuqing_index);
            long yuqing_total = 0;
            while (rs.next()) {
                String source = rs.getString("source");
                if(source.equals("多媒体")){
                    Constant.yuqing_media = rs.getInt("count");
                }
                else if(source.equals("公众号")){
                    Constant.yuqing_gzh = rs.getInt("count");
                }
                yuqing_total += rs.getInt("sum");
            }
            result.put("yuqing_total", yuqing_total);
            result.put("yuqing_media", Constant.yuqing_media);
            result.replace("yuqing_mediatop1", Constant.yuqing_mediatop1);
            result.replace("yuqing_mediatop2", Constant.yuqing_mediatop2);
            result.put("yuqing_gzh", Constant.yuqing_gzh);
            result.replace("yuqing_gzhtop1", Constant.yuqing_gzhtop1);
            result.replace("yuqing_gzhtop2", Constant.yuqing_gzhtop2);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static LinkedList<JsonNode> getNewsDetailBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        ObjectMapper objectMapper = new ObjectMapper();
        LinkedList<JsonNode> result = new LinkedList<>();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int ID = 0;
            while (rs.next()) {
                ObjectNode News = objectMapper.createObjectNode();
                News.put("title",rs.getString("title"));
                News.put("content",rs.getString("content"));
                News.put("ID",ID++);
                News.put("date",dateFormat.format(new Date(rs.getLong("time")*1000)));
                result.add(News);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }

    private static ArrayNode getTotalNewsDetailBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode result = objectMapper.createArrayNode();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int ID = 0;
            while (rs.next()) {
                ObjectNode News = objectMapper.createObjectNode();
                News.put("title",rs.getString("title"));
                News.put("content",rs.getString("content"));
                News.put("ID",ID++);
                News.put("date",dateFormat.format(new Date(rs.getLong("time")*1000)));
                result.add(News);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        } finally {
            try {
                conn.close();
            }
            catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("MySQL Connection Error!!!!!\n");
            }
            return result;
        }
    }
}
