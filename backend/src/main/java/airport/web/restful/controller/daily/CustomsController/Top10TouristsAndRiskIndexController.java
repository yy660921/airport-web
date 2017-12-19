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
import java.util.HashMap;

import static airport.web.restful.service.sql.Query.getTop10TouristsAndRiskIndex;

/**
 * Created by Machenike on 2017/12/18.
 * 提供总体态势统计页面左下角风险旅客TOP10风险旅客姓名及风险值数据
 */
@Controller
public class Top10TouristsAndRiskIndexController {
    private final static Logger LOG = LoggerFactory.getLogger(Top10TouristsAndRiskIndexController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getTAndRI",
    })

    public JsonNode getTAndRI() {
        return getTop10TouristsAndRiskIndex();
    }
}
