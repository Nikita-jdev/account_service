package faang.school.accountservice.controller;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.request.RequestExecutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/request/executor")
public class RequestExecutorController {
    private final RequestExecutorService requestExecutorService;

    @PostMapping("/account/{requestId}")
    public AccountDto openAccount(@PathVariable UUID requestId, @RequestBody @Valid AccountDto accountDto) {
        return requestExecutorService.executeRequest(requestId, accountDto);
    }
}
