package ticketing_system.app.Business.implementation.TicketImplementation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ticketing_system.app.percistance.Entities.TicketEntities.Ticket;
import ticketing_system.app.percistance.Enums.TicketPriorityLevel;
import ticketing_system.app.percistance.Enums.TicketStatus;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketRepository;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplementationTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TicketServiceImplementation ticketService;


    @Test
    public void testCreateTicket() {
        // Create a sample DTO and entity
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketID(1L);
        ticketDTO.setTicketName("No service");
        Ticket ticketEntity = new Ticket();
        ticketEntity.setTicketId(1L);

        TicketDTO firstTicket = new TicketDTO("No service", "No service on site", TicketStatus.OPEN,
                TicketPriorityLevel.LOW, "2023-10-17 16:13:31");
       Ticket ticket2 = new Ticket("No service", "No service on site", TicketStatus.OPEN,
               TicketPriorityLevel.LOW, Timestamp.valueOf("2023-10-17 16:13:31"));



        // Mock behavior for modelMapper and ticketRepository
        when(modelMapper.map(ticketDTO, Ticket.class)).thenReturn(ticketEntity);
        when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);
        when(modelMapper.map(ticketEntity, TicketDTO.class)).thenReturn(ticketDTO);

        // Mock behavior for modelMapper and ticketRepository
        when(modelMapper.map(firstTicket, Ticket.class)).thenReturn(ticket2);
        when(ticketRepository.save(ticket2)).thenReturn(ticket2);
        when(modelMapper.map(ticket2, TicketDTO.class)).thenReturn(firstTicket);

        // Perform the service method
        TicketDTO createdTicket = ticketService.createTicket(ticketDTO);
        TicketDTO firstTicketCreated = ticketService.createTicket(firstTicket);

        // Verify interactions and assertions
        verify(modelMapper, times(1)).map(ticketDTO, Ticket.class);
        verify(ticketRepository, times(1)).save(ticketEntity);
        assertThat(createdTicket).isEqualTo(ticketDTO);
        assertThat(firstTicketCreated).isEqualTo(firstTicket);


    }

    @Test
    void getTicket() {
        Long ticketId = 1L;
        TicketDTO expectedTicket = new TicketDTO();
        Ticket ticketEntity = new Ticket();
        ticketEntity.setTicketId(1L);

        /*When and Then*/
        when(ticketRepository.getTicketByTicketId(ticketId)).thenReturn(ticketEntity);
        when(modelMapper.map(ticketEntity, TicketDTO.class)).thenReturn(expectedTicket);


        // Perform the service method
        TicketDTO actualTicket = ticketService.getTicket(ticketId);

        // Verify interactions and assertions
        verify(ticketRepository, times(1)).getTicketByTicketId(ticketId);
        verify(modelMapper, times(1)).map(ticketEntity, TicketDTO.class);
        assertThat(actualTicket).isEqualTo(expectedTicket);

    }

    @Test
    void updateTicket() {
        // Create a sample DTO and entity
        Long ticketId = 1L;
        TicketDTO ticketToUpdate = new TicketDTO();

        Ticket ticketEntity = new Ticket();
        ticketEntity.setTicketId(ticketId);

        /*When and Then*/  // Mock behavior for modelMapper and ticketRepository
        when(modelMapper.map(ticketToUpdate, Ticket.class)).thenReturn(ticketEntity);
        when(ticketRepository.getTicketByTicketId(ticketId)).thenReturn(ticketEntity);
        when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);
        when(modelMapper.map(ticketEntity, TicketDTO.class)).thenReturn(ticketToUpdate);


        // Perform the service method
        TicketDTO actualTicket = ticketService.updateTicket(ticketId, ticketToUpdate);

        // Verify interactions and assertions
        verify(modelMapper, times(1)).map(ticketToUpdate, Ticket.class);
        verify(ticketRepository, times(1)).getTicketByTicketId(ticketId);
//        verify(ticketRepository, times(1)).save(ticketEntity);
        assertThat(actualTicket).isEqualTo(ticketToUpdate);
    }

    @Test
    void deleteTicket() {
        Long ticketId = 1L;

        // Perform the service method
       ticketService.deleteTicket(ticketId);

        // Verify interactions and assertions
        verify(ticketRepository, times(1)).deleteById(ticketId);
        assertThat(ticketRepository.getTicketByTicketId(ticketId)).isNull();
    }
}