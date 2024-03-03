package faang.school.accountservice.service;

import faang.school.accountservice.dto.FreeAccountNumberDto;
import faang.school.accountservice.mapper.FreeAccountNumberMapper;
import faang.school.accountservice.model.FreeAccountNumber;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {

    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final FreeAccountNumberMapper mapper;

    public FreeAccountNumber getFreeAccountNumbers(long id) {
        return freeAccountNumbersRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("FreeAccountNumber with id = " + id + " is not exists"));
    }

    public FreeAccountNumberDto get(long id) {
        return mapper.toDto(getFreeAccountNumbers(id));
    }

    private void checkFreeAccountNumbers (long id) {
        if (!freeAccountNumbersRepository.existsById(id)) {
            throw new EntityNotFoundException("FreeAccountNumber with id = " + id + " is not exists");
        }
    }
}
