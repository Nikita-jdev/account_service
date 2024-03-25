package faang.school.accountservice.service;

public interface Updater<U, T, V> {

    boolean isApplicable(U type);

    T update(T toUpdate);
}
