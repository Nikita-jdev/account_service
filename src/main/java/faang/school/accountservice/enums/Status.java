package faang.school.accountservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ACTIVE("active"),
    FROZEN("frozen"),
    CLOSED("closed"),
    BLOCKED("blocked");

    private final String accountStatus;
}