package ticketing_system.app.Business.implementation.TicketImplementation;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public TicketServiceImplementation(TicketRepository ticketRepository, ModelMapper modelMapper){
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }

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
        if (ticket == null){
            return null;
        }
        return convertToDto(ticket);
    }
    @Override
    public TicketDTO updateTicket(Long id, TicketDTO ticketDTO) {
        Ticket ticket = convertToTicket(ticketDTO);
        if (getTicket(id) != null){
            deleteTicket(id);
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
