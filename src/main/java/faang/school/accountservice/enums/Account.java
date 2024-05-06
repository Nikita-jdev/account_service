package faang.school.accountservice.enums;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {
    //TODO у меня 3 таблицы и теперь нужно создать 3 сущности, Currency как enum есть уже,
    // нужно просто до оформить все это дело
}
