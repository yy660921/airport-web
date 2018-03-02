package airport.web.restful.controller.daily.CustomsController;

import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static airport.web.restful.service.sql.Query.getCountControlDetail;
import static airport.web.restful.service.sql.Query.getPageJumpDetail;
import static airport.web.restful.service.sql.Query.getShowChartsDetail;

/**
 * Created by Machenike on 2018/3/1.
 * 控制接口
 * /api/getPageJump
 * /api/getShowCharts
 * /api/getCountControl
 */
@Controller
public class GetSettingController {

    private final static Logger LOG = LoggerFactory.getLogger(GetSettingController.class);
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getPageJump",
    })

    public JsonNode getPageJump() {
        return getPageJumpDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getShowCharts",
    })

    public JsonNode getShowCharts() {
        return getShowChartsDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getCountControl",
    })

    public String getCountControl() {
        return getCountControlDetail();
    }
}
