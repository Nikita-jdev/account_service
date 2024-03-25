package faang.school.accountservice.service;

import faang.school.accountservice.model.Balance;

public class ClearingUpdate implements Updater<Balance>{
    @Override
    public boolean isApplicable(Balance type) {
        return false;
    }

    @Override
    public Balance update(Balance toUpdate) {
        return null;
    }
}
