package ticketing_system.app.Business.implementation.TicketImplementation;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticketing_system.app.Business.services.TicketServices.TicketService;
import ticketing_system.app.percistance.Entities.TicketEntities.Ticket;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketRepository;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;

@Service
@AllArgsConstructor
public class TicketServiceImplementation implements TicketService {
    private ModelMapper modelMapper;
    private TicketRepository ticketRepository;

    private TicketDTO convertToDto(Ticket ticket){

        return modelMapper.map(ticket, TicketDTO.class);
    }

    private Ticket convertToTicket(TicketDTO ticketDTO){

        return modelMapper.map(ticketDTO, Ticket.class);
    }
    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = convertToTicket(ticketDTO);
        Ticket savedTicket = this.ticketRepository.save(ticket);
        return convertToDto(savedTicket);
    }

    @Override
    public TicketDTO getTicket(Long ticketId) {
        Ticket ticket = ticketRepository.getTicketByTicketId(ticketId);
        return convertToDto(ticket);
    }
    @Override
    public TicketDTO updateTicket(Long id, TicketDTO ticketDTO) {
        Ticket ticket = convertToTicket(ticketDTO);
        if (getTicket(id) != null){
            Ticket updatedTicket = ticketRepository.save(ticket);
            return  convertToDto(updatedTicket);
        } else {
            return null;
        }
    }
    @Transactional
    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
