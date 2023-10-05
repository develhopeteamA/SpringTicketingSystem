package ticketing_system.app.Business.services.TicketServices;

import ticketing_system.app.preesentation.data.TicketData.TicketDTO;

public interface TicketService {
    TicketDTO createTicket(TicketDTO ticketDTO);
    TicketDTO getTicket(Long ticketId);
    TicketDTO updateTicket(Long id, TicketDTO ticketDTO);
    void deleteTicket(Long id);

}
