package airport.web.restful.controller.daily.CustomsController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;

import static airport.web.restful.service.sql.Query.DeviceCountDist;

/**
 * Created by Machenike on 2017/12/18.
 */
@Controller
public class DeviceCountDistController {
    private final static Logger LOG = LoggerFactory.getLogger(DeviceCountDistController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getDCD",
    })

    public HashMap<String, String> getDCD(
        @RequestParam(value = "date", defaultValue = "1509811200") long date) {
        HashMap Result = DeviceCountDist(new Date(date));
        if(Result.size() == 0){
            return DeviceCountDist();
        }
        else {
            return Result;
        }
    }
}
