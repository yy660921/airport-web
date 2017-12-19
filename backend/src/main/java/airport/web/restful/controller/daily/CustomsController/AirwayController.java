package airport.web.restful.controller.daily.CustomsController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;

import airport.web.data.bean.TourTrips;

import static airport.web.restful.service.sql.Query.getAirwayTrip;

/**
 * Created by Machenike on 2017/12/19.
 * 提供总体态势统计页面中部航线数据
 */
@Controller
public class AirwayController {
    private final static Logger LOG = LoggerFactory.getLogger(AirwayController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getAirway",
    })

    public LinkedList<TourTrips> getAirway(
        @RequestParam(value = "from", defaultValue = "1509811200") long from,
        @RequestParam(value = "to", defaultValue = "1509811200") long to) {
        LinkedList Result = getAirwayTrip(new Date(from), new Date(to));
        if(Result.size() == 0){
            return getAirwayTrip();
        }
        else {
            return Result;
        }
    }
}
