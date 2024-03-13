package faang.school.accountservice.service;

import org.springframework.stereotype.Service;

@Service
public class FreeAccountNumbersService {

    public String getFreeNumber(String type) {
        return "0000000000000000";
    }
}
