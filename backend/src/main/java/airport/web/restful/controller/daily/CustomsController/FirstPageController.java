package airport.web.restful.controller.daily.CustomsController;

import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static airport.web.restful.service.sql.Query.getFirstPageCount;
import static airport.web.restful.service.sql.Query.getFirstPageNewsCount;

/**
 * Created by Machenike on 2017/12/20.
 * 首页内容接口
 * /api/getFirstPage接口获取首页统计内容
 */
@Controller
public class FirstPageController {
    private final static Logger LOG = LoggerFactory.getLogger(FirstPageController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getFirstPage",
    })

    public JsonNode getFirstPage() {
        return getFirstPageCount();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getFirstPageNews",
    })

    public JsonNode getFirstPageNews() {
        return getFirstPageNewsCount();
    }
}
