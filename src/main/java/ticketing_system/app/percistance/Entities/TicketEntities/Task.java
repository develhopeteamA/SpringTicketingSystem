package ticketing_system.app.percistance.Entities.TicketEntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String description;
    private boolean complete;
    @ManyToOne
    private Ticket ticket;
}
