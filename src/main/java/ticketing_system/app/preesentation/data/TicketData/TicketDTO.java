package ticketing_system.app.preesentation.data.TicketData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ticketing_system.app.percistance.Entities.TicketEntities.Task;
import ticketing_system.app.percistance.Entities.User;
import ticketing_system.app.percistance.Enums.TicketPriorityLevel;
import ticketing_system.app.percistance.Enums.TicketStatus;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
@EqualsAndHashCode
public class TicketDTO {
    private Long ticketID;
    private String ticketName;
    private String description;
    private TicketStatus ticketStatus; //statusID from table or enum
    private String tags;
    private TicketPriorityLevel priorityLevel; //use Enum
    private User agentAssigned; //userID from User table
    private List<Task> subTasks;
    private String attachments;
    private Timestamp deadlineDate;
    private Timestamp createdOn;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp updatedOn;

    public TicketDTO(String ticketName, String description, TicketStatus ticketStatus, TicketPriorityLevel priorityLevel, String deadlineDate){
        this.ticketStatus = ticketStatus;
        this.ticketName = ticketName;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.deadlineDate = Timestamp.valueOf(deadlineDate);
    }

    public TicketDTO(){}
}
