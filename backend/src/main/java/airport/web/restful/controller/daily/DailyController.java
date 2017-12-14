package airport.web.restful.controller.daily;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import airport.web.restful.service.daily.DailyService;

@Controller
public class DailyController {

    private DailyService daily = new DailyService();

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {"/hello"})
    public String hello() {
        return daily.hello();
    }
}