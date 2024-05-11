package faang.school.accountservice.entity;

import faang.school.accountservice.model.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private long id;
    @OneToMany(mappedBy = "user")
    private List<Account> accounts;
}
