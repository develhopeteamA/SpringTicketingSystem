package ticketing_system.app.Business.services.TicketServices;

import ticketing_system.app.percistance.Enums.TicketPriorityLevel;
import ticketing_system.app.percistance.Enums.TicketStatus;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;

import java.util.List;

public interface TicketService {
    TicketDTO createTicket(TicketDTO ticketDTO);
    TicketDTO getTicket(Long ticketId);
    TicketDTO updateTicket(Long id, TicketDTO ticketDTO);
    void deleteTicket(Long id);
    TicketDTO assignTicket(Long ticketId, String userEmail);

    TicketDTO updateTicketStatus(Long ticketId, TicketStatus ticketStatus);

    TicketDTO updateTicketPriorityLevel(Long ticketId, TicketPriorityLevel ticketPriorityLevel);

    TicketDTO addTaskToTicket(Long ticketId, List<TaskDTO> taskDTOList);
}
