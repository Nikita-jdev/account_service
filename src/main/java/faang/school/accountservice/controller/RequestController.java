package faang.school.accountservice.controller;

import faang.school.accountservice.dto.RequestDto;
import faang.school.accountservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class RequestController {
    private final RequestService requestService;

    @GetMapping
    public List<RequestDto> getAllRequests () {
        return requestService.getAll();
    }

    @PostMapping
    public RequestDto createRequest (@RequestBody RequestDto requestDto) {
        return requestService.createRequest(requestDto);
    }

    @PutMapping
    public RequestDto updateRequest (@RequestBody RequestDto requestDto) {
        return requestService.updateRequest(requestDto);
    }
}
