package airport.web.restful.controller.daily.CustomsController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;

import airport.web.data.bean.CustomsTouristMessage;

import static airport.web.restful.service.sql.Query.getTouristMessage;

/**
 * Created by Machenike on 2017/12/19.
 * 提供一组高风险旅客数据
 */
@Controller
public class TouristController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getTourist",
    })

    public LinkedList<CustomsTouristMessage> getTourist(
        @RequestParam(value = "date", defaultValue = "1509811200") long date) {
        LinkedList Result = getTouristMessage(new Date(date));
        if(Result.size() == 0){
            return getTouristMessage();
        }
        else {
            return Result;
        }
    }
}
