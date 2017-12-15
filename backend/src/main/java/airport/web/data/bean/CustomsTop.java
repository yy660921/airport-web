package airport.web.data.bean;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Machenike on 2017/12/15.
 */

public class CustomsTop extends BaseInfomation{

    private String warningTourist_departure;
    private String warningTourist_destination;
    private String warningTourist_nameList;
    private String DeviceCountDist;

    public CustomsTop(){
        super();
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

    public String getWarningTourist_nameList() {
        return warningTourist_nameList;
    }

    public void setWarningTourist_nameList(String warningTourist_nameList) {
        this.warningTourist_nameList = warningTourist_nameList;
    }

    public String getDeviceCountDist() {
        return DeviceCountDist;
    }

    public void setDeviceCountDist(String deviceCountDist) {
        DeviceCountDist = deviceCountDist;
    }
}
