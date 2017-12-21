package airport.web.restful.service.sql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.print.attribute.standard.MediaSize;

import airport.web.data.bean.BaseInfomation;
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
        String sql = "SELECT tourist_warningEvents,seizure_number,createDate FROM customs_top ORDER BY id DESC LIMIT 10";
        return getRiskTouristsAndSeizureNumberBySQL(sql);
    }

    /*
     * @description: 查询最新的高风险旅客出发地与目的地统计
     */
    public static JsonNode getDepartureData(){
        String sql = "SELECT warningTourist_departure,warningTourist_destination FROM customs_top ORDER BY createDate DESC LIMIT 1";
        return getDepartureAndDestinationBySQL(sql);
    }

    /*
     * @description: 查询最新TOP10的高风险旅客名单及风险值
     */
    public static JsonNode getTop10TouristsAndRiskIndex(){
        String sql = "SELECT warningTourist_nameList FROM customs_top ORDER BY createDate DESC LIMIT 1";
        return getTop10TouristsAndRiskIndexBySQL(sql);
    }

    /*
     * @description: 查询最新的设备24小时运行情况
     */
    public static JsonNode getDeviceCountDist(){
        String sql = "SELECT DeviceCountDist FROM customs_top ORDER BY createDate DESC LIMIT 1";
        return getDeviceCountDistBySQL(sql);
    }

    /*
     * @description: 查询date时间的航线
     * @param date: 查询日期
     */
    public static LinkedList<TourTrips> getAirwayTrip(Date from, Date to){
        String sql = "SELECT warningTourist_departure,city_dep.lon as deplon,city_dep.lat as deplat,warningTourist_destination,city_dest.lon as destlon,city_dest.lat as destlat,1 AS weight\n"
                     + "FROM customs_touristmessage\n"
                     + "INNER JOIN city_longlati AS city_dep\n"
                     + "ON customs_touristmessage.warningTourist_departure = city_dep.cncity\n"
                     + "INNER JOIN city_longlati AS city_dest\n"
                     + "ON customs_touristmessage.warningTourist_destination = city_dest.cncity\n"
                     + "WHERE createDate BETWEEN \"" + dateFormat.format(from) + "\" AND \""+ dateFormat.format(to) + "\"";
        return getAirwayTripBySQL(sql);
    }

    /*
     * @description: 查询最新的航线
     */
    public static LinkedList<TourTrips> getAirwayTrip(){
        String sql = "SELECT warningTourist_departure,city_dep.lon as deplon,city_dep.lat as deplat,warningTourist_destination,city_dest.lon as destlon,city_dest.lat as destlat,count(*) as weight\n"
                     + "FROM customs_touristmessage\n"
                     + "INNER JOIN city_longlati AS city_dep\n"
                     + "ON customs_touristmessage.warningTourist_departure = city_dep.cncity\n"
                     + "INNER JOIN city_longlati AS city_dest\n"
                     + "ON customs_touristmessage.warningTourist_destination = city_dest.cncity\n"
                     + "GROUP BY warningTourist_departure,warningTourist_destination\n"
                     + "ORDER BY createDate DESC LIMIT 10";
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
        }
        while(Places.hasNext()){
            sql += "\",\"" + Places.next();
        }
        return getAirLineBySQL(sql + "\")");
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
        String sql = "SELECT * FROM customs_touristmessage ORDER BY createDate DESC LIMIT 10";
        return getTouristMessageBySQL(sql);
    }

    /*
     * @description: 查询最新的首页信息
     */
    public static JsonNode getFirstPageCount(){
        String sql = "SELECT riskIndex,warningEvents_number,tourist_warningEvents,chinaTourist_warningEvents,overseasTourist_warningEvents,seizure_number,contraband_number,highTax_number,governpeople_number,devicecount_number FROM customs_index ORDER BY createDate DESC LIMIT 1";
        return getFirstPageCountBySQL(sql);
    }

    private static JsonNode getRiskTouristsAndSeizureNumberBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println(sql);
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
        } finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private static JsonNode getDepartureAndDestinationBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
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
                while(fieldNames.hasNext()){
                    String field = fieldNames.next();
                    Departures.add(field);
                    DepartureCount.add(Long.parseLong(Departure.get(field).toString().replace("\"","")));
                }
                ArrayNode Destinations = OM.createArrayNode();
                ArrayNode DestinationCount = OM.createArrayNode();
                JsonNode Destination = OM.readTree(rs.getString("warningTourist_destination"));
                fieldNames = Destination.fieldNames();
                while(fieldNames.hasNext()){
                    String field = fieldNames.next();
                    Destinations.add(field);
                    DestinationCount.add(Long.parseLong(Destination.get(field).toString().replace("\"","")));
                }
                result.replace("Departures",Departures);
                result.replace("DepartureCount",DepartureCount);
                result.replace("Destinations",Destinations);
                result.replace("DestinationCount",DestinationCount);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private static JsonNode getTop10TouristsAndRiskIndexBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
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
            while(Passports.hasNext()){
                String Passport = Passports.next();
                NameList.add(TouristList.get(Passport).get("姓名"));
                RiskIndex.add(Double.parseDouble(TouristList.get(Passport).get("风险值").toString().replace("\"","")));
            }
            result.replace("TouristName", NameList);
            result.replace("TouristRiskIndex", RiskIndex);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private static JsonNode getDeviceCountDistBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
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
        } finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private static LinkedList<TourTrips> getAirwayTripBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedList<TourTrips> result = new LinkedList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if(rs.getInt("weight")!=0) {
                    TourTrips TourTrip = new TourTrips();
                    ObjectNode Departure = objectMapper.createObjectNode();
                    ArrayNode DepartureCoordinate = objectMapper.createArrayNode();
                    ObjectNode Destination = objectMapper.createObjectNode();
                    ArrayNode DestinationCoordinate = objectMapper.createArrayNode();
                    Departure.put("CityName", rs.getString("warningTourist_departure"));
                    DepartureCoordinate.add(rs.getDouble("deplon"));
                    DepartureCoordinate.add(rs.getDouble("deplat"));
                    Departure.replace("Coordinate", DepartureCoordinate);
                    Destination.put("CityName", rs.getString("warningTourist_destination"));
                    DestinationCoordinate.add(rs.getDouble("destlon"));
                    DestinationCoordinate.add(rs.getDouble("destlat"));
                    Destination.replace("Coordinate", DestinationCoordinate);
                    TourTrip.setDeparture(Departure);
                    TourTrip.setDestination(Destination);
                    result.add(TourTrip);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private static LinkedList<CustomsTouristMessage> getTouristMessageBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedList<CustomsTouristMessage> result = new LinkedList<>();
        ObjectMapper objectMapper = new ObjectMapper();
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
                CT.setWarningTourist_flight_number(rs.getString("warningTourist_flight_number"));
                CT.setWarningTourist_flight_type(rs.getString("warningTourist_flight_type"));
                CT.setWarningTourist_time(rs.getDate("warningTourist_time"));
                CT.setWarningTourist_historyTime(rs.getString("warningTourist_historyTime"));
                ArrayNode TravelLine = objectMapper.createArrayNode();
                JsonNode Travel = objectMapper.readTree(rs.getString("warningTourist_place"));
                Iterator<String> Travelline = Travel.fieldNames();
                ObjectNode TravelPlaces = objectMapper.createObjectNode();
                while(Travelline.hasNext()){
                    String field = Travelline.next();
                    JsonNode Trav = Travel.get(field);
                    if(!TravelPlaces.has(Trav.get("出发地").asText())){
                        TravelPlaces.put(Trav.get("出发地").asText(),0);
                    }
                    if(!TravelPlaces.has(Trav.get("目的地").asText())){
                        TravelPlaces.put(Trav.get("目的地").asText(),0);
                    }
                }
                JsonNode PlaceDict = getAirLine(TravelPlaces.fieldNames());
                Travelline = Travel.fieldNames();
                while(Travelline.hasNext()){
                    String field = Travelline.next();
                    JsonNode Trav = Travel.get(field);
                    if(PlaceDict.has(Trav.get("出发地").asText())&&PlaceDict.has(Trav.get("目的地").asText())){
                        ObjectNode Trip = objectMapper.createObjectNode();
                        ObjectNode Departure = objectMapper.createObjectNode();
                        ObjectNode Destination = objectMapper.createObjectNode();
                        Departure.put("CityName",Trav.get("出发地").asText());
                        Departure.replace("Coordinate",PlaceDict.get(Trav.get("出发地").asText()));
                        Destination.put("CityName",Trav.get("目的地").asText());
                        Destination.replace("Coordinate",PlaceDict.get(Trav.get("目的地").asText()));
                        Trip.replace("departure",Departure);
                        Trip.replace("destination",Destination);
                        TravelLine.add(Trip);
                    }
                }
                CT.setWarningTourist_place(TravelLine);
                CT.setFellowTourist_list(rs.getString("fellowTourist_list"));
                CT.setCreateDate(rs.getDate("createDate"));
                result.add(CT);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private static JsonNode getAirLineBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
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
            catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private static JsonNode getFirstPageCountBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
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
                result.put("yuqing_index",(Constant.Baidu.getDays() + Constant.Weixin.getDays()));
                result.put("yuqing_total", (Constant.Baidu.getsize() + Constant.Weixin.getsize()));
                result.put("yuqing_media", Constant.Media.size());
                result.put("yuqing_xinhua", Constant.Xinhua);
                result.put("yuqing_zhongxin", Constant.Zhongxin);
                result.put("yuqing_gzh", Constant.Gzh.size());
                result.put("yuqing_tongrenga", Constant.TongrenGongan);
                result.put("yuqing_tongrenwsh", Constant.TongrenWeishenghuo);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}
