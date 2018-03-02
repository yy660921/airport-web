package airport.web.restful.controller.daily.CustomsController;


import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;

import airport.web.data.bean.TourTrips;

import static airport.web.restful.service.sql.Query.getAirwayTrip;
import static airport.web.restful.service.sql.Query.getContrabandGoodsDetail;
import static airport.web.restful.service.sql.Query.getDepartureData;
import static airport.web.restful.service.sql.Query.getDeviceCountDist;
import static airport.web.restful.service.sql.Query.getFlightWarningDetail;
import static airport.web.restful.service.sql.Query.getHotGoodsDetail;
import static airport.web.restful.service.sql.Query.getOtherMessageDetail;
import static airport.web.restful.service.sql.Query.getOwnMessageDetail;
import static airport.web.restful.service.sql.Query.getRiskLabelDetail;
import static airport.web.restful.service.sql.Query.getRiskTouristsAndSeizureNumber;
import static airport.web.restful.service.sql.Query.getSexRatioDetail;
import static airport.web.restful.service.sql.Query.getTop10TouristsAndRiskIndex;
import static airport.web.restful.service.sql.Query.getTouristCountryDetail;
import static airport.web.restful.service.sql.Query.getYearsAircraftDetail;
import static airport.web.restful.service.sql.Query.getYearsPeopleDetail;

/**
 * Created by Machenike on 2017/12/18.
 * 提供总体态势统计页面相关接口
 * /api/getRTAndSN：         风险走势接口
 * /api/getAirway：          航线数据接口
 * /api/getDeparture：       高风险旅客出发地统计数据接口
 * /api/getDCD：             设备总查验次数时段分布数据接口
 * /api/getTAndRI：          风险旅客TOP10风险旅客姓名及风险值数据接口
 * /api/getTouristCountry：  高风险旅客来源国家接口
 * /api/getSexRatio：        旅客风险构成-性别比例接口
 * /api/getRiskLabel：       旅客风险构成-风险标签接口
 * /api/getOtherMessage：    外部情报风险来源接口
 * /api/getOwnMessage：      内部情报风险来源接口
 * /api/getFlightWarning：   航线风险要素构成接口
 * /api/getHotGoods：        查获重点热门物品构成接口
 * /api/getContrabandGoods： 查获禁限物品构成接口
 * /api/getYearsAircraft：   年度监管航空器构成接口
 * /api/getYearsPeople：     年度进出境人员构成接口
 */

@Controller
public class CountChartsController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {"/api/hello"})
    public String hello() {
        return "hello!";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getRTAndSN",
    })

    public JsonNode getRTAndSN(
        @RequestParam(value = "from", defaultValue = "0") long from,
        @RequestParam(value = "to", defaultValue = "0") long to) {
        JsonNode Result = getRiskTouristsAndSeizureNumber(new Date(from * 1000), new Date(to * 1000));
        if(Result.get("createDate").size() == 0){
            return getRiskTouristsAndSeizureNumber();
        }
        else {
            return Result;
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getAirway",
    })

    public LinkedList<TourTrips> getAirway() {
        return getAirwayTrip();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getDeparture",
    })

    public JsonNode getDeparture() {
        return getDepartureData();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getDCD",
    })

    public JsonNode getDCD() {
        return getDeviceCountDist();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getTAndRI",
    })
    public JsonNode getTAndRI() {
        return getTop10TouristsAndRiskIndex();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getTouristCountry",
    })

    public JsonNode getTouristCountry() {
        return getTouristCountryDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getSexRatio",
    })

    public JsonNode getSexRatio() {
        return getSexRatioDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getRiskLabel",
    })

    public JsonNode getRiskLabel() {
        return getRiskLabelDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getOtherMessage",
    })

    public JsonNode getOtherMessage() {
        return getOtherMessageDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getOwnMessage",
    })

    public JsonNode getOwnMessage() {
        return getOwnMessageDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getFlightWarning",
    })

    public JsonNode getFlightWarning() {
        return getFlightWarningDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getHotGoods",
    })

    public JsonNode getHotGoods() {
        return getHotGoodsDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getContrabandGoods",
    })

    public JsonNode getContrabandGoods() {
        return getContrabandGoodsDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getYearsAircraft",
    })

    public JsonNode getYearsAircraft() {
        return getYearsAircraftDetail();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {
        "/api/getYearsPeople",
    })

    public JsonNode getYearsPeople() {
        return getYearsPeopleDetail();
    }
}
