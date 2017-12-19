package airport.web.data.bean;

import java.util.Date;

/**
 * Created by Machenike on 2017/12/18.
 */
public class TourTrips {
    private String Departure;
    private double DepartureLati;
    private double DepartureLong;
    private String Destination;
    private double DestinationLati;
    private double DestinationLong;
    private Date createDate;
    private int weight;

    public TourTrips(){

    }

    public String getDeparture() {
        return Departure;
    }

    public void setDeparture(String departure) {
        Departure = departure;
    }

    public double getDepartureLati() {
        return DepartureLati;
    }

    public void setDepartureLati(double departureLati) {
        DepartureLati = departureLati;
    }

    public double getDepartureLong() {
        return DepartureLong;
    }

    public void setDepartureLong(double departureLong) {
        DepartureLong = departureLong;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public double getDestinationLati() {
        return DestinationLati;
    }

    public void setDestinationLati(double destinationLati) {
        DestinationLati = destinationLati;
    }

    public double getDestinationLong() {
        return DestinationLong;
    }

    public void setDestinationLong(double destinationLong) {
        DestinationLong = destinationLong;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
