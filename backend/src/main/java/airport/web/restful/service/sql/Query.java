package airport.web.restful.service.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import airport.web.data.bean.BaseInfomation;

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
    public static LinkedList<BaseInfomation> getRiskTouristsAndSeizureNumber(Date from, Date to){
        String sql = "";
        if(from.before(to)) {
            sql =
                "SELECT id,createTime,tourist_warningEvents,seizure_number,createDate FROM customs_top WHERE DATE(createDate) BETWEEN \""
                + dateFormat.format(from) + "\" AND \"" + dateFormat.format(to) + "\"";
        }
        else{
            sql =
                "SELECT id,createTime,tourist_warningEvents,seizure_number,createDate FROM customs_top WHERE DATE(createDate) BETWEEN \""
                + dateFormat.format(to) + "\" AND \"" + dateFormat.format(from) + "\"";
        }
        return getRiskTouristsAndSeizureNumberBySQL(sql);
    }

    /*
     * @description: 查询最新的size条信息
     * @param size: 信息条数
     */
    public static LinkedList<BaseInfomation> getRiskTouristsAndSeizureNumber(int size){
        String sql = "SELECT id,createTime,tourist_warningEvents,seizure_number,createDate FROM customs_top ORDER BY id DESC LIMIT "+ size;
        return getRiskTouristsAndSeizureNumberBySQL(sql);
    }

    /*
     * @description: 查询date时间的高风险旅客出发地与目的地统计
     * @param date: 查询日期
     */
    public static HashMap<String, String> getDepartureAndDestination(Date date){
        String sql = "SELECT warningTourist_departure,warningTourist_destination,createDate FROM customs_top WHERE createDate = \""+ dateFormat.format(date) + "\" LIMIT 1";
        return getDepartureAndDestinationBySQL(sql);
    }

    /*
     * @description: 查询最新的高风险旅客出发地与目的地统计
     */
    public static HashMap<String, String> getDepartureAndDestination(){
        String sql = "SELECT warningTourist_departure,warningTourist_destination,createDate FROM customs_top ORDER BY createDate DESC LIMIT 1";
        return getDepartureAndDestinationBySQL(sql);
    }

    /*
     * @description: 查询date时间的TOP10高风险旅客名单及风险值
     * @param date: 查询日期
     */
    public static HashMap<String, String> getTop10TouristsAndRiskIndex(Date date){
        String sql = "SELECT warningTourist_nameList,createDate FROM customs_top WHERE createDate = \""+ dateFormat.format(date) + "\" LIMIT 1";
        return getTop10TouristsAndRiskIndexBySQL(sql);
    }

    /*
     * @description: 查询最新TOP10的高风险旅客名单及风险值
     */
    public static HashMap<String, String> getTop10TouristsAndRiskIndex(){
        String sql = "SELECT warningTourist_nameList,createDate FROM customs_top ORDER BY createDate DESC LIMIT 1";
        return getTop10TouristsAndRiskIndexBySQL(sql);
    }

    /*
     * @description: 查询date时间的TOP10高风险旅客名单及风险值
     * @param date: 查询日期
     */
    public static HashMap<String, String> DeviceCountDist(Date date){
        String sql = "SELECT DeviceCountDist,createDate FROM customs_top WHERE createDate = \""+ dateFormat.format(date) + "\" LIMIT 1";
        return DeviceCountDistBySQL(sql);
    }

    /*
     * @description: 查询最新TOP10的高风险旅客名单及风险值
     */
    public static HashMap<String, String> DeviceCountDist(){
        String sql = "SELECT DeviceCountDist,createDate FROM customs_top ORDER BY createDate DESC LIMIT 1";
        return DeviceCountDistBySQL(sql);
    }

    private static LinkedList<BaseInfomation> getRiskTouristsAndSeizureNumberBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedList<BaseInfomation> result = new LinkedList<>();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                BaseInfomation BI = new BaseInfomation();
                BI.setId(rs.getInt("id"));
                BI.setCreateTime(rs.getDate("createTime"));
                BI.setTourist_warningEvents(rs.getInt("tourist_warningEvents"));
                BI.setSeizure_number(rs.getInt("seizure_number"));
                BI.setCreateDate(rs.getDate("createDate"));
                result.add(BI);
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

    private static HashMap<String, String> getDepartureAndDestinationBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, String> result = new HashMap<>();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.put("warningTourist_departure",rs.getString("warningTourist_departure"));
                result.put("warningTourist_destination",rs.getString("warningTourist_destination"));
                result.put("createDate",dateFormat.format(rs.getDate("createDate")));
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

    private static HashMap<String, String> getTop10TouristsAndRiskIndexBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, String> result = new HashMap<>();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.put("warningTourist_nameList",rs.getString("warningTourist_nameList"));
                result.put("createDate",dateFormat.format(rs.getDate("createDate")));
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

    private static HashMap<String, String> DeviceCountDistBySQL(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, String> result = new HashMap<>();
        try{
            conn = MySQL.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.put("DeviceCountDist",rs.getString("DeviceCountDist"));
                result.put("createDate",dateFormat.format(rs.getDate("createDate")));
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
