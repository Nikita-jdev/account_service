package faang.school.accountservice.service.owner;

import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    @Transactional
    public Owner saveOwner(OwnerType ownerType) {
        log.info("Сохраняем владельца с типом: {}", ownerType);
        return ownerRepository.save(Owner.builder()
                .ownerType(ownerType)
                .build());
    }

    @Override
    public Optional<Owner> findById(Long id) {
        log.info("Ищем владельца с ID: {}", id);
        return ownerRepository.findById(id);
    }

}