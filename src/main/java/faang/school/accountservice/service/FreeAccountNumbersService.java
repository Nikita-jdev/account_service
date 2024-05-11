package faang.school.accountservice.service;

import faang.school.accountservice.entity.AccountSeq;
import faang.school.accountservice.entity.AccountType;
import faang.school.accountservice.entity.FreeAccountId;
import faang.school.accountservice.entity.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService{

    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;
    private final FreeAccountNumbersRepository freeAccountRepository;

    private final static long ACCOUNT_PATTERN_DEBIT=4200_0000_0000_0000L;
    private final static long ACCOUNT_PATTERN_DEPO=5236_0000_0000_0000L;
    private final static int MAX_ACCOUNT_LEADING_DIGITS_DEBIT=4200;
    private final static int MAX_ACCOUNT_LEADING_DIGITS_DEPO=5236;

    @Transactional
    public void generateAccountNumber(AccountType accountType, long bucketSize){
        long accountNumber;
        List<FreeAccountNumber> accountNumberList=new ArrayList<>();
        accountNumbersSequenceRepository.incrementCounter(accountType.name(), bucketSize);
        System.out.println("\n\n=========  " + accountType.name());
        AccountSeq period = accountNumbersSequenceRepository.getCounterValues(accountType.name(), bucketSize);//todo minus
        System.out.println("\n\n=========  " + period);

        for(long i=period.getInitialValue(); i<period.getCounter(); i++){
            if(accountType.equals(AccountType.DEBIT)){
                accountNumber=ACCOUNT_PATTERN_DEBIT+i;
                accountNumberList.add(new FreeAccountNumber(new FreeAccountId(accountType, accountNumber)));
            }else{
                accountNumber=ACCOUNT_PATTERN_DEPO+i;
                accountNumberList.add(new FreeAccountNumber(new FreeAccountId(accountType, accountNumber)));
            }
            if(accountNumber/1_0000_0000_0000L!=MAX_ACCOUNT_LEADING_DIGITS_DEBIT){ //todo: depo
                throw new IllegalStateException("Generated account number exceeds pattern limit:");
            }

        }
        freeAccountRepository.saveAll(accountNumberList);
    }

    @Transactional
    public void retrieveAccountNumber(AccountType accountType, Consumer<FreeAccountNumber> accountNumberConsumer){
        Optional<FreeAccountNumber> accountNumber=freeAccountRepository.retrieveFirst(accountType.name());
        if(accountNumber.isPresent()){
            accountNumberConsumer.accept(accountNumber.get());
        }else{
            generateAccountNumber(accountType, 1);
            retrieveAccountNumber(accountType, accountNumberConsumer);

        }

    }
}
