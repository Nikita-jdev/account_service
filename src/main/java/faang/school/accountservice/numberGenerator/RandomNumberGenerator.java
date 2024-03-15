package faang.school.accountservice.numberGenerator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberGenerator {

    public String generateRandomNumber(int minDigits, int maxDigits) {
        Random random = new Random();
        int length = minDigits + random.nextInt(maxDigits - minDigits + 1);

        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = (i == 0) ? 1 + random.nextInt(9) : random.nextInt(10);
            randomNumber.append(digit);
        }
        return randomNumber.toString();
    }

}