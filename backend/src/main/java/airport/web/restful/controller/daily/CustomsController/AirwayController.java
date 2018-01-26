package airport.web.restful.controller.daily.CustomsController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;

import airport.web.data.bean.TourTrips;

import static airport.web.restful.service.sql.Query.getAirwayTrip;

/**
 * Created by Machenike on 2017/12/19.
 * 提供总体态势统计页面中部航线数据
 */
@Controller
public class AirwayController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getAirway",
    })

    public LinkedList<TourTrips> getAirway() {
            return getAirwayTrip();
    }
}
