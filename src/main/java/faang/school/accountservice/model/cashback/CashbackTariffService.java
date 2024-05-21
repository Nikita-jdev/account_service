package faang.school.accountservice.model.cashback;

import faang.school.accountservice.repository.CashbackOperationMappingRepository;
import faang.school.accountservice.repository.CashbackTariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashbackTariffService {

    private final CashbackTariffRepository cashbackTariffRepository;
    private final CashbackOperationMappingRepository operationMappingRepository;

    public CashbackTariff createTariff(CashbackTariff cashbackTariff) {
        return cashbackTariffRepository.save(cashbackTariff);
    }

    public CashbackTariff getTariff(Long id) {
        return cashbackTariffRepository.findById(id).orElseThrow(() -> new RuntimeException("Tariff not found"));
    }

    public CashbackOperationMapping addOperationMapping(Long tariffId, CashbackOperationMapping operationMapping) {
        CashbackTariff tariff = getTariff(tariffId);
        operationMapping.setCashbackTariff(tariff);
        return operationMappingRepository.save(operationMapping);
    }

}
