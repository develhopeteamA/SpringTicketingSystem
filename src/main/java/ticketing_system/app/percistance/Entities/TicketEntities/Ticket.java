package ticketing_system.app.percistance.Entities.TicketEntities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ticketing_system.app.percistance.Entities.User;
import ticketing_system.app.percistance.Enums.TicketPriorityLevel;
import ticketing_system.app.percistance.Enums.TicketStatus;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long ticketId;
    private String ticketName;
    private String description;
    private TicketStatus ticketStatus; //statusID from table or use enum
    private String tags;
    private TicketPriorityLevel priorityLevel; //use enum
    @ManyToOne
    private User agentAssigned; //userID from User table
    @OneToMany
    private List<Task> subTasks;
    private String attachments;
    private Timestamp deadlineDate;
    private Timestamp createdOn;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp updatedOn;

    public Ticket(){

    }

    public Ticket(String ticketName, String description, TicketStatus ticketStatus, TicketPriorityLevel priorityLevel, Timestamp deadlineDate){
        this.ticketStatus = ticketStatus;
        this.ticketName = ticketName;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.deadlineDate = deadlineDate;
    }

}


