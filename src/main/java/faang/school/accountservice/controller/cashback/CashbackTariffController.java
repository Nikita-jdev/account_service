package faang.school.accountservice.controller.cashback;

import faang.school.accountservice.model.cashback.CashbackOperationMapping;
import faang.school.accountservice.model.cashback.CashbackTariff;
import faang.school.accountservice.service.cashback.CashbackTariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cashback-tariffs")
public class CashbackTariffController {

    private final CashbackTariffService cashbackTariffService;

    @PostMapping
    public CashbackTariff createTariff(@RequestBody CashbackTariff cashbackTariff) {
        return cashbackTariffService.createTariff(cashbackTariff);
    }

    @GetMapping("/{id}")
    public CashbackTariff getTariff(@PathVariable Long id) {
        return cashbackTariffService.getTariff(id);
    }

    @PostMapping("/{tariffId}/mappings")
    public CashbackOperationMapping addOperationMapping(@PathVariable Long tariffId, @RequestBody CashbackOperationMapping operationMapping) {
        return cashbackTariffService.addOperationMapping(tariffId, operationMapping);
    }
}