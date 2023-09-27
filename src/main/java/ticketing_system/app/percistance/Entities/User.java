package ticketing_system.app.percistance.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    private String username;
    private final LocalDate dateCreated = LocalDate.now();
}
