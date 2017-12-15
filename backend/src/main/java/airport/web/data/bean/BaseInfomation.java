package airport.web.data.bean;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Machenike on 2017/12/15.
 */

public class BaseInfomation {

    private int id;
    private Date createTime;        //数据写入时间(2017-12-05 12:00:00)
    private double riskIndex;       //总体风险指数(64.75)
    private int tourist_warningEvents;
    private int seizure_number;
    private Date createDate;

    public BaseInfomation(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        try {
            this.createTime = DateUtils.parseDate(createTime,"yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setCreateTime(long timeStamp) {
        this.createTime = new Date(timeStamp);
    }

    public double getRiskIndex() {
        return riskIndex;
    }

    public void setRiskIndex(String riskIndex) {
        this.riskIndex = Double.parseDouble(riskIndex);
    }

    public int getTourist_warningEvents() {
        return tourist_warningEvents;
    }

    public void setTourist_warningEvents(int tourist_warningEvents) {
        this.tourist_warningEvents = tourist_warningEvents;
    }

    public int getSeizure_number() {
        return seizure_number;
    }

    public void setSeizure_number(int seizure_number) {
        this.seizure_number = seizure_number;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
