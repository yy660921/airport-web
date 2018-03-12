package airport.web.restful.controller.daily.CustomsController;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static airport.web.restful.service.sql.Query.getFirstPageCount;
import static airport.web.restful.service.sql.Query.getFirstPageNewsCount;

/**
 * Created by Machenike on 2017/12/20.
 * 首页内容接口
 * /api/getFirstPage接口获取首页统计内容
 * /api/getFirstPageNews接口获取首页新闻统计内容
 */
@Controller
public class FirstPageController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getFirstPage",
    })

    public JsonNode getFirstPage(
        @RequestParam(value = "area", defaultValue = "贵阳") String area
    ) {
        return getFirstPageCount(area);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getFirstPageNews",
    })

    public JsonNode getFirstPageNews() {
        return getFirstPageNewsCount();
    }
}
