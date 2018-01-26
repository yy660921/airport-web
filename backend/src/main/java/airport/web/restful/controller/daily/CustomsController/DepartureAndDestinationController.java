package airport.web.restful.controller.daily.CustomsController;


import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static airport.web.restful.service.sql.Query.getDepartureData;


/**
 * Created by Machenike on 2017/12/18.
 * 提供总体态势统计页面右上角高风险旅客出发地统计数据，默认返回最新数据
 */
@Controller
public class DepartureAndDestinationController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getDeparture",
    })

    public JsonNode getDeparture() {
        return getDepartureData();
    }
}
