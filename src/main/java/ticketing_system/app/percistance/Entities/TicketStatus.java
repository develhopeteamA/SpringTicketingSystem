package ticketing_system.app.percistance.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TicketStatus {
    @Id
    @GeneratedValue
    private Long statusID;
    private String statusName;
}
