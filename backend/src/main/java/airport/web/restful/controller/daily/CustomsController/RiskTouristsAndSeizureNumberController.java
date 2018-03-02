package airport.web.restful.controller.daily.CustomsController;


import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

import static airport.web.restful.service.sql.Query.getRiskTouristsAndSeizureNumber;

/**
 * Created by Machenike on 2017/12/18.
 * 提供总体态势统计页面左上角风险走势数据
 */

@Controller
public class RiskTouristsAndSeizureNumberController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {"/api/hello"})
    public String hello() {
        return "hello!";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getRTAndSN",
    })

    public JsonNode getRTAndSN(
        @RequestParam(value = "from", defaultValue = "0") long from,
        @RequestParam(value = "to", defaultValue = "0") long to) {
        JsonNode Result = getRiskTouristsAndSeizureNumber(new Date(from * 1000), new Date(to * 1000));
        if(Result.get("createDate").size() == 0){
            return getRiskTouristsAndSeizureNumber();
        }
        else {
            return Result;
        }
    }
}
