package faang.school.accountservice.service.owner;

import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.OwnerType;

import java.util.Optional;

public interface OwnerService {

    Owner saveOwner(OwnerType ownerType);

    Optional<Owner> findById(Long id);
}
