package airport.web.data.bean;


import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by Machenike on 2017/12/18.
 */
public class TourTrips {
    private ObjectNode Departure;
    private ObjectNode Destination;

    public TourTrips(){

    }

    public ObjectNode getDeparture() {
        return Departure;
    }

    public void setDeparture(ObjectNode departure) {
        Departure = departure;
    }

    public ObjectNode getDestination() {
        return Destination;
    }

    public void setDestination(ObjectNode destination) {
        Destination = destination;
    }


}
