package airport.web.restful.controller.daily.CustomsController;

import com.fasterxml.jackson.databind.node.ArrayNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static airport.web.restful.service.sql.Query.getViewPointInfo;

/**
 * Created by Machenike on 2018/1/22.
 * /api/getViewPoint接口获取观点数据
 */
@Controller
public class ViewPointController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getViewPoint",
    })

    public ArrayNode getViewPoint() {
        return getViewPointInfo();
    }
}
