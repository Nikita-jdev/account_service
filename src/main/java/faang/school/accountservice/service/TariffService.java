package faang.school.accountservice.service;

import faang.school.accountservice.dto.TariffDto;
import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.entity.Tariff;
import faang.school.accountservice.exception.TariffNotFoundException;
import faang.school.accountservice.mapper.TariffMapper;
import faang.school.accountservice.repository.OwnerRepository;
import faang.school.accountservice.repository.TariffRepository;
import faang.school.accountservice.validator.TariffValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffRepository tariffRepository;
    private final OwnerRepository ownerRepository;
    private final TariffValidator tariffValidator;
    private final TariffMapper tariffMapper;

    @Transactional
    public TariffDto createTariff(TariffDto tariffDto) {
        tariffValidator.validateTariff(tariffDto);
        log.info("Creating new tariff: {}", tariffDto);
        Tariff tariff = tariffMapper.toTariff(tariffDto);

        return tariffMapper.toTariffDto(tariffRepository.save(tariff));
    }

    @Transactional(readOnly = true)
    public TariffDto getTariffById(Long id) {
        log.info("Getting tariff by ID: {}", id);
        Optional<Tariff> tariff = tariffRepository.findById(id);
        return tariff.map(tariffMapper::toTariffDto)
                .orElseThrow(() -> new TariffNotFoundException("Tariff with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<TariffDto> getAllTariffs() {
        log.info("Getting all tariffs");
        List<Tariff> tariffs = tariffRepository.findAll();
        return tariffs.stream()
                .map(tariffMapper::toTariffDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TariffDto updateTariff(Long id, TariffDto tariffDto) {
        log.info("Updating tariff with ID {}: {}", id, tariffDto);
        Tariff existingTariff = tariffRepository.findById(id)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not found"));

        if (existingTariff.getTariffType() != tariffDto.getTariffType()) {
            throw new IllegalArgumentException("Tariff type cannot be changed");
        }

        List<BigDecimal> newRates = new ArrayList<>();
        newRates.add(tariffDto.getRate());
        existingTariff.setRates(newRates);

        Tariff savedTariff = tariffRepository.save(existingTariff);

        return tariffMapper.toTariffDto(savedTariff);
    }


    public void updateTariffRate(Long tariffId, BigDecimal newRate) {
        log.info("Updating rate with tariff ID {}: {}", tariffId, newRate);
        Tariff existingTariff = tariffRepository.findById(tariffId)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not found"));

        existingTariff.getRates().add(newRate);

        tariffRepository.save(existingTariff);
    }

    @Transactional
    public void deleteTariff(Long id) {
        log.info("Deleting tariff with ID: {}", id);

        Tariff tariff = tariffRepository.findById(id).orElse(null);

        if (tariff != null && !isTariffInUse(tariff)) {
            tariffRepository.delete(tariff);
        }
    }

    private boolean isTariffInUse(Tariff tariff) {
        List<Owner> usersWithTariff = ownerRepository.findByTariff(tariff);
        return !usersWithTariff.isEmpty();
    }
}