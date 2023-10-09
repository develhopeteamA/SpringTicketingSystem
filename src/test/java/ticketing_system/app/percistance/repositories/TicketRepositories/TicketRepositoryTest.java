package ticketing_system.app.percistance.repositories.TicketRepositories;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ticketing_system.app.Business.implementation.TicketImplementation.TicketServiceImplementation;
import ticketing_system.app.percistance.Entities.TicketEntities.Ticket;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketRepositoryTest {

    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void testGetTicketByTicketId() {
        /*Given*/
        Long ticketId = 1L;
        Ticket expectedTicket = new Ticket();

        /*When and Then*/
        when(ticketRepository.getTicketByTicketId(ticketId)).thenReturn(expectedTicket);

        Ticket actualTicket = ticketRepository.getTicketByTicketId(ticketId);

        verify(ticketRepository, times(1)).getTicketByTicketId(ticketId);

        BDDAssertions.assertThat(actualTicket).isEqualTo(expectedTicket);
    }

    // Add more tests for your repository methods as needed
}