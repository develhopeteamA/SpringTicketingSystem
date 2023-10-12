package ticketing_system.app.preesentation.controler.TicketControllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ticketing_system.app.Business.implementation.TicketImplementation.TicketServiceImplementation;
import ticketing_system.app.percistance.Entities.TicketEntities.Ticket;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketRepository;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static ticketing_system.app.percistance.Enums.TicketPriorityLevel.*;
import static ticketing_system.app.percistance.Enums.TicketStatus.*;


@ExtendWith(MockitoExtension.class)
class TicketControllerTest {
    private TicketController ticketController;
    private TicketServiceImplementation ticketServiceImplementation;
    private ModelMapper modelMapper;
    private TicketRepository ticketRepository;
    @BeforeEach
    void setUp() {
        this.ticketServiceImplementation = new TicketServiceImplementation(new ModelMapper(), ticketRepository);
        this.ticketController = new TicketController(this.ticketServiceImplementation);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createTicket() {
        TicketDTO ticket = new TicketDTO("Cart is not working",
                "User cannot add item to the cart", OPEN, HIGH,
                "2023-12-07 16:13:31");

        ticketController.createTicket(ticket);
    }

    @Test
    void getTicket() {

    }

    @Test
    void updateTicket() {
    }

    @Test
    void deleteTicket() {
    }
}