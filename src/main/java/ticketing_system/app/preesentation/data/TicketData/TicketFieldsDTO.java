package ticketing_system.app.preesentation.data.TicketData;

import lombok.Data;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.Enums.Tags;
import ticketing_system.app.percistance.Enums.TicketPriority;
import ticketing_system.app.percistance.Enums.TicketStatus;

import java.time.LocalDate;
import java.util.List;

@Data
public class TicketFieldsDTO implements DTOType {

    private String ticketName;
    private String description;
    private Tags tag;
    private LocalDate deadline;
    private TicketStatus status;
    private TicketPriority priority;
    private List<Tasks> tasks;
    private Users agentAssigned;
//    private final LocalDateTime createdOn;
//    private LocalDateTime updatedOn;
    public TicketFieldsDTO(){}
}
