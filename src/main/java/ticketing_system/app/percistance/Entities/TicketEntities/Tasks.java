package ticketing_system.app.percistance.Entities.TicketEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import ticketing_system.app.preesentation.data.TicketData.DTOType;

/**
 * class to represent a task.
 * @author kamar baraka.*/

@Entity
@Data
public class Tasks{

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private long taskId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean complete = false;
}