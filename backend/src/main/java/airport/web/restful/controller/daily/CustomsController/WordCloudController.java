package airport.web.restful.controller.daily.CustomsController;

import com.fasterxml.jackson.databind.node.ArrayNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static airport.web.restful.service.sql.Query.getWordCloud;

/**
 * Created by Machenike on 2018/1/22.
 * /api/getWordCloud接口用于获取最新的词云数据
 */
@Controller
public class WordCloudController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getWordCloud",
    })

    public ArrayNode getViewPoint() {
        return getWordCloud();
    }
}
