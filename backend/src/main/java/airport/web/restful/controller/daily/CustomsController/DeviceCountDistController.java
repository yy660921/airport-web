package airport.web.restful.controller.daily.CustomsController;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import static airport.web.restful.service.sql.Query.getDeviceCountDist;

/**
 * Created by Machenike on 2017/12/18.
 * 提供总体态势统计页面右下角设备总查验次数时段分布数据
 */
@Controller
public class DeviceCountDistController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getDCD",
    })

    public JsonNode getDCD() {
        return getDeviceCountDist();
    }
}
