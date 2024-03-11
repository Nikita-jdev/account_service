package faang.school.accountservice.service;

import org.springframework.stereotype.Component;

@Component
public class FreeAccountNumbersService {

    public String getFreeNumber(String type) {
        return "0000000000000000";
    }
}
