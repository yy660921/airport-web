package airport.web.data.bean;

import java.util.Date;

/**
 * Created by Machenike on 2017/12/15.
 */

public class CustomsTouristMessage {

    private int id;
    private Date createTime;
    private String warningTourist_name;
    private String warningTourist_country;
    private String warningTourist_sex;
    private double warningTourist_riskIndex;
    private String warningTourist_passport;
    private String warningTourist_IDtype;
    private Date warningTourist_birthday;
    private String warningTourist_departure;
    private String warningTourist_destination;
    private String warningTourist_flight_number;
    private String warningTourist_flight_type;
    private Date warningTourist_time;
    private String warningTourist_historyTime;
    private String warningTourist_place;
    private String fellowTourist_list;
    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWarningTourist_name() {
        return warningTourist_name;
    }

    public void setWarningTourist_name(String warningTourist_name) {
        this.warningTourist_name = warningTourist_name;
    }

    public String getWarningTourist_country() {
        return warningTourist_country;
    }

    public void setWarningTourist_country(String warningTourist_country) {
        this.warningTourist_country = warningTourist_country;
    }

    public String getWarningTourist_sex() {
        return warningTourist_sex;
    }

    public void setWarningTourist_sex(String warningTourist_sex) {
        this.warningTourist_sex = warningTourist_sex;
    }

    public double getWarningTourist_riskIndex() {
        return warningTourist_riskIndex;
    }

    public void setWarningTourist_riskIndex(String warningTourist_riskIndex) {
        this.warningTourist_riskIndex = Double.parseDouble(warningTourist_riskIndex);
    }

    public String getWarningTourist_passport() {
        return warningTourist_passport;
    }

    public void setWarningTourist_passport(String warningTourist_passport) {
        this.warningTourist_passport = warningTourist_passport;
    }

    public String getWarningTourist_IDtype() {
        return warningTourist_IDtype;
    }

    public void setWarningTourist_IDtype(String warningTourist_IDtype) {
        this.warningTourist_IDtype = warningTourist_IDtype;
    }

    public Date getWarningTourist_birthday() {
        return warningTourist_birthday;
    }

    public void setWarningTourist_birthday(Date warningTourist_birthday) {
        this.warningTourist_birthday = warningTourist_birthday;
    }

    public String getWarningTourist_departure() {
        return warningTourist_departure;
    }

    public void setWarningTourist_departure(String warningTourist_departure) {
        this.warningTourist_departure = warningTourist_departure;
    }

    public String getWarningTourist_destination() {
        return warningTourist_destination;
    }

    public void setWarningTourist_destination(String warningTourist_destination) {
        this.warningTourist_destination = warningTourist_destination;
    }

    public String getWarningTourist_flight_type() {
        return warningTourist_flight_type;
    }

    public void setWarningTourist_flight_type(String warningTourist_flight_type) {
        this.warningTourist_flight_type = warningTourist_flight_type;
    }

    public String getWarningTourist_flight_number() {
        return warningTourist_flight_number;
    }

    public void setWarningTourist_flight_number(String warningTourist_flight_number) {
        this.warningTourist_flight_number = warningTourist_flight_number;
    }

    public Date getWarningTourist_time() {
        return warningTourist_time;
    }

    public void setWarningTourist_time(Date warningTourist_time) {
        this.warningTourist_time = warningTourist_time;
    }

    public String getWarningTourist_historyTime() {
        return warningTourist_historyTime;
    }

    public void setWarningTourist_historyTime(String warningTourist_historyTime) {
        this.warningTourist_historyTime = warningTourist_historyTime;
    }

    public String getWarningTourist_place() {
        return warningTourist_place;
    }

    public void setWarningTourist_place(String warningTourist_place) {
        this.warningTourist_place = warningTourist_place;
    }

    public String getFellowTourist_list() {
        return fellowTourist_list;
    }

    public void setFellowTourist_list(String fellowTourist_list) {
        this.fellowTourist_list = fellowTourist_list;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}