package faang.school.accountservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OwnerType {
    USER("user"),
    PROJECT("project");

    private final String ownerType;
}