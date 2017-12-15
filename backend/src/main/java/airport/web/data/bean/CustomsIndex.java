package airport.web.data.bean;

/**
 * Created by Machenike on 2017/12/15.
 */

public class CustomsIndex extends BaseInfomation{

    private int governpeople_number;
    private int devicecount_number;
    private int warningEvents_number;
    private int overseasTourist_warningEvents;
    private int chinaTourist_warningEvents;
    private int contraband_number;
    private int highTax_number;
    private int danger_number;

    public CustomsIndex(){
        super();
    }

    public int getGovernpeople_number() {
        return governpeople_number;
    }

    public void setGovernpeople_number(int governpeople_number) {
        this.governpeople_number = governpeople_number;
    }

    public int getDevicecount_number() {
        return devicecount_number;
    }

    public void setDevicecount_number(int devicecount_number) {
        this.devicecount_number = devicecount_number;
    }

    public int getWarningEvents_number() {
        return warningEvents_number;
    }

    public void setWarningEvents_number(int warningEvents_number) {
        this.warningEvents_number = warningEvents_number;
    }

    public int getOverseasTourist_warningEvents() {
        return overseasTourist_warningEvents;
    }

    public void setOverseasTourist_warningEvents(int overseasTourist_warningEvents) {
        this.overseasTourist_warningEvents = overseasTourist_warningEvents;
    }

    public int getChinaTourist_warningEvents() {
        return chinaTourist_warningEvents;
    }

    public void setChinaTourist_warningEvents(int chinaTourist_warningEvents) {
        this.chinaTourist_warningEvents = chinaTourist_warningEvents;
    }

    public int getContraband_number() {
        return contraband_number;
    }

    public void setContraband_number(int contraband_number) {
        this.contraband_number = contraband_number;
    }

    public int getHighTax_number() {
        return highTax_number;
    }

    public void setHighTax_number(int highTax_number) {
        this.highTax_number = highTax_number;
    }

    public int getDanger_number() {
        return danger_number;
    }

    public void setDanger_number(int danger_number) {
        this.danger_number = danger_number;
    }

}
