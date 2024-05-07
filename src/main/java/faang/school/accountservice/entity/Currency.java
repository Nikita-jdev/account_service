package faang.school.accountservice.entity;

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
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO: тут я думаю, может тут country_id передавать, раз у нас есть таблица
    // country в БД? или не париться, передавать просто стрингой название страны?
    // Если запариться с country_id, то тут же нужно просто сущность для country создать
    // и country CountryRepository сделать?
    @Column(name = "country", length = 128, nullable = false)
    private String country;

    @Column(name = "currency", length = 128, nullable = false)
    private String currency;

    @Column(name = "code", length = 3, nullable = false)
    private String code;

    @Column(name = "number", length = 3, nullable = false)
    private String number;
}