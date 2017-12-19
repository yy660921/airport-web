package airport.web.restful.service.sql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

/**
 * Created by Machenike on 2017/12/18.
 */

public class Query {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /*
     * @description: 查询从from到to时间段内的信息
     * @param from: 起始日期
     * @param to:   截止日期
     */
    public static JsonNode getRiskTouristsAndSeizureNumber(Date from, Date to){
        String sql = "";
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
        String sql = "SELECT createDate,warningTourist_departure,city_dep.lon as deplon,city_dep.lat as deplat,warningTourist_destination,city_dest.lon as destlon,city_dest.lat as destlat,count(*) as weight\n"
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
        String sql = "SELECT createDate,warningTourist_departure,city_dep.lon as deplon,city_dep.lat as deplat,warningTourist_destination,city_dest.lon as destlon,city_dest.lat as destlat,count(*) as weight\n"
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
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TourTrips TourTrip = new TourTrips();
                TourTrip.setDeparture(rs.getString("warningTourist_departure"));
                TourTrip.setDepartureLong(rs.getDouble("deplon"));
                TourTrip.setDepartureLati(rs.getDouble("deplat"));
                TourTrip.setDestination(rs.getString("warningTourist_destination"));
                TourTrip.setDestinationLong(rs.getDouble("destlon"));
                TourTrip.setDestinationLati(rs.getDouble("destlat"));
                TourTrip.setCreateDate(rs.getDate("createDate"));
                TourTrip.setWeight(rs.getInt("weight"));
                if(TourTrip.getWeight()!=0) {
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
                CT.setWarningTourist_place(rs.getString("warningTourist_place"));
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
}
