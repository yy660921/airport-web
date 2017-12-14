package airport.web.restful.service.daily;

import org.springframework.stereotype.Service;

@Service
public class DailyService {

    public DailyService() {
    }

    public static void main(String[] args) {
        DailyService daily = new DailyService();
        System.out.println(daily.hello());
    }

    public String hello() {
        return "hello";
    }
}
