package ticketing_system.app.percistance.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    private Long ticketID;
    private String ticketName;
    private String description;
    @ManyToOne
    private TicketStatus ticketStatus; //statusID from table
    private String tags;
    private String priorityLevel;
    @ManyToOne
    private User agentAssigned; //userID from User table
    @OneToMany
    private List<Task> subTasks;
    private String attachments;
    private Timestamp deadlineDate;
    private Timestamp createdOn;
    private int createdBy;
    private int updatedBy;
    private Timestamp updatedOn;

    public Ticket(){

    }

    public Ticket(String ticketName, String description, TicketStatus ticketStatus, String priorityLevel, Timestamp deadlineDate){
        this.ticketStatus = ticketStatus;
        this.ticketName = ticketName;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.deadlineDate = deadlineDate;
    }
}


