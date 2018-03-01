package airport.web.restful.controller.daily.CustomsController;

import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static airport.web.restful.service.sql.Query.getPageJumpDetail;

/**
 * Created by Machenike on 2018/3/1.
 * 页面跳转接口
 * /api/getPageJump
 */
@Controller
public class PageJumpController {

    private final static Logger LOG = LoggerFactory.getLogger(PageJumpController.class);
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getPageJump",
    })

    public JsonNode getPageJump() {
        return getPageJumpDetail();
    }
}
