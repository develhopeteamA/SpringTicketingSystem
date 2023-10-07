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
        this.ticketRepository = new TicketRepository() {
            @Override
            public Ticket getTicketByTicketId(Long ticketId) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Ticket> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Ticket> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<Ticket> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Ticket getOne(Long aLong) {
                return null;
            }

            @Override
            public Ticket getById(Long aLong) {
                return null;
            }

            @Override
            public Ticket getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends Ticket> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Ticket> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public <S extends Ticket> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public List<Ticket> findAll() {
                return null;
            }

            @Override
            public List<Ticket> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public <S extends Ticket> S save(S entity) {
                return null;
            }

            @Override
            public Optional<Ticket> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Ticket entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends Ticket> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public List<Ticket> findAll(Sort sort) {
                return null;
            }

            @Override
            public Page<Ticket> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Ticket> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Ticket> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Ticket> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Ticket> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Ticket, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };
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