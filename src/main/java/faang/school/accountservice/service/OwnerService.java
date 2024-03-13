package faang.school.accountservice.service;

import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner findById(long id) {
        return ownerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
    }
}
