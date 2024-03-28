package faang.school.accountservice.service.owner;

import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    void testSaveOwner() {
        when(ownerRepository.save(any(Owner.class))).thenReturn(any(Owner.class));

        ownerService.saveOwner(OwnerType.USER);

        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    void testFindNonExistingOwnerById() {

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Owner> foundOwner = ownerService.findById(anyLong());

        assertEquals(Optional.empty(), foundOwner);
    }

}