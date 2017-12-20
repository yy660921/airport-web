package airport.web.restful.controller.daily.CustomsController;


import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;

import airport.web.data.bean.BaseInfomation;

import static airport.web.restful.service.sql.Query.getRiskTouristsAndSeizureNumber;

/**
 * Created by Machenike on 2017/12/18.
 * 提供总体态势统计页面左上角风险走势数据
 */

@Controller
public class RiskTouristsAndSeizureNumberController {
    private final static Logger LOG = LoggerFactory.getLogger(RiskTouristsAndSeizureNumberController.class);

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
        @RequestParam(value = "from", defaultValue = "1509811200") long from,
        @RequestParam(value = "to", defaultValue = "1512403200") long to) {
        JsonNode Result = getRiskTouristsAndSeizureNumber(new Date(from), new Date(to));
        if(Result.get("createDate").size() == 0){
            return getRiskTouristsAndSeizureNumber();
        }
        else {
            return Result;
        }
    }
}
