package faang.school.accountservice.validator;

import faang.school.accountservice.config.context.UserContext;
import faang.school.accountservice.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestValidator {
    private final UserContext userContext;

    public void validateAccess (RequestDto requestDto) {
        long userId = requestDto.getUserId();

        if (userId != userContext.getUserId()) {
            throw new SecurityException(String.format("User with id = %s has no access", userId));
        }
    }
}
