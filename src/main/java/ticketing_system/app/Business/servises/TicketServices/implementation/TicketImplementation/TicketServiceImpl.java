package ticketing_system.app.Business.servises.TicketServices.implementation.TicketImplementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ticketing_system.app.Business.implementation.userServiceImplementations.UserImpematation;
import ticketing_system.app.Business.servises.TicketServices.TaskService;
import ticketing_system.app.Business.servises.TicketServices.TicketService;
import ticketing_system.app.Business.servises.TicketServices.utilities.TicketMapper;
import ticketing_system.app.exceptions.TaskExceptionService;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.Enums.Tags;
import ticketing_system.app.percistance.Enums.TicketPriority;
import ticketing_system.app.percistance.Enums.TicketStatus;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketsRepository;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;
import ticketing_system.app.preesentation.controler.TicketControllers.TicketController;
import ticketing_system.app.preesentation.controler.userControllers.UserController;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketAgentDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketNormalDTO;
;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * the ticket service implementation.
 * @author kamar baraka.*/

@Log4j2
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TaskExceptionService taskException;
    private final TaskService taskService;
    private final TicketsRepository ticketsRepository;
    private final TicketMapper ticketMapper;

    @Autowired
    private UserRepository userRepository;


    public List<Tickets> getOpenTickets(){

        /*get all open tickets*/
        return ticketsRepository.findAll(
                Sort.by(Sort.Direction.DESC, "ticket_priority")
        ).stream().filter(tickets -> tickets.getStatus() == TicketStatus.OPEN).toList();
    }
    public List<Tickets> getClosedTickets(){

        /*get closed tickets*/
        return ticketsRepository.findAllByStatus(
                TicketStatus.CLOSED);
    }
    public List<Tickets> getResolvedTickets(){

        /*get resolved tickets*/
        return ticketsRepository.findAllByStatus(
                TicketStatus.RESOLVED
        );
    }
    public List<Tickets> getInProgressTickets(){

        /*get the in-progress tickets*/
        return ticketsRepository.findAllByStatus(
                TicketStatus.IN_PROGRESS
        );
    }

    @Override
    public Tickets createTicket(@Validated TicketDTO ticketDTO) {

        /*map the ticket to DTO*/
        Tickets ticket = ticketMapper.mapToTicket(ticketDTO);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate deadline = LocalDate.parse(ticketDTO.deadline(), formatter);
        ticket.setDeadline(deadline);

        ticket.setTag(Tags.valueOf(ticketDTO.tag()));

        ticket.setTicketName(ticketDTO.ticketName());

        ticket.setPriority(TicketPriority.valueOf(ticketDTO.priority()));

        ticket.setDescription(ticketDTO.description());
        /*persist the ticket*/
        return ticketsRepository.save(ticket);
    }

    @Override
    public List<Tickets> getAllTickets() {

        /*get all tickets*/
        return ticketsRepository.findAll(
                Sort.by(Sort.Direction.DESC, "priority")
        );

    }

    @Override
    public Tickets getTicketByTicketId(final long ticketId) {

        /*get a ticket by ID*/
        return ticketsRepository.findById(ticketId).orElseThrow(taskException::ticketNotFound);

    }

    @Override
    public List<Tickets> getTicketByName(final String ticketName) {

        /*get ticket by name*/
        return ticketsRepository.findTicketsByTicketName(ticketName);
    }

    @Override
    public List<Tickets> getTicketsByTag(final String tag) {

        /*find tickets by the tag*/
        return ticketsRepository.findAllByTag(Tags.valueOf(tag));
    }

    @Override
    public TicketNormalDTO updateTicketById(final long ticketId, @Validated final TicketDTO ticketDTO) {

        /*check if the ticket exists*/
        ticketsRepository.findById(ticketId).ifPresentOrElse(
                ticket -> {
                    Tickets ticketToSave = ticketMapper.mapToTicket(ticketDTO);
                    ticketToSave.setTicketId(ticket.getTicketId());
                    ticketsRepository.save(ticketToSave);
                },
                taskException::ticketNotFound
        );

        return ticketMapper.mapToNormalDTO(getTicketByTicketId(ticketId));
    }

    @Override
    public void deleteTicketById(final long ticketId) {

        /*check if exists and delete ticket with the id*/
        ticketsRepository.findById(ticketId).ifPresentOrElse(
                ticket -> ticketsRepository.deleteById(ticketId),
                taskException::ticketNotFound
        );
    }

    @Override
    public void addTaskToTicket(final long ticketId,@Validated final TaskDTO taskDTO) {

        log.warn("service entry {}", taskDTO);

        /*check if ticket exists*/
        ticketsRepository.findById(ticketId).ifPresentOrElse(
                ticket -> {
                    /*create the task*/
                    Tasks task = taskService.createTaskGetTask(taskDTO);
                    /*add the task to the ticket*/
                    ticket.getTasks().add(task);
                    /*update the ticket*/
                    ticketsRepository.save(ticket);
                },
                taskException::ticketNotFound
        );

        log.warn("service pass {}", taskDTO);

    }

    @Override
    public void deleteTaskFromTicket(final long ticketId,final long taskId) {

        /*check if ticket exists*/
        ticketsRepository.findById(ticketId).ifPresentOrElse(
                ticket -> taskService.deleteTaskById(taskId),
                taskException::ticketNotFound
        );
    }

    @Override
    public void completeTaskOfTicket(final long ticketId,final long taskId) {

        /*check if the ticket exists*/
        ticketsRepository.findById(ticketId).ifPresentOrElse(
                ticket -> {
                    /*get the ticket with the id and update*/
                    List<Tasks> updatedTasks = ticket.getTasks().stream().filter(task -> task.getTaskId() == taskId).peek(
                            task -> task.setComplete(true)
                    ).toList();
                    /*update the task on the ticket*/
                    ticket.getTasks().addAll(updatedTasks);
                    ticketsRepository.save(ticket);
                },
                taskException::ticketNotFound
        );
    }

    @Override
    public TicketAgentDTO assignTicketToAgent(Long ticketId, Long userId) {
         AtomicReference<Tickets> ticketToSave = new AtomicReference<>(new Tickets());

        /*check if the ticket exists*/
        ticketsRepository.findById(ticketId).ifPresentOrElse(
                ticket -> {

//                    Users agentToBeAssigned = userRepository.findById(userId).get();
                    userRepository.findById(userId).ifPresentOrElse(
                            users -> {
                                //check if user role == agent/admin
                                String agentRoleName = users.getRole().getRoleName().toLowerCase();
                                log.info("Agent Role: " + agentRoleName);
                                if (users != null && agentRoleName.equals("agent")) {
                                    ticket.setAgentAssigned(users);
                                    ticketsRepository.save(ticket);

                                }
                            },
                            () -> {
                                throw new RuntimeException("This user cannot be assigned a ticket. " +
                                        "Check if the user exists and has the correct role");
                            }
                    );

                },
                taskException::ticketNotFound
        );

        return ticketMapper.mapToAgentDTO(getTicketByTicketId(ticketId));
    }

    @Override
    public TicketNormalDTO updateTicketStatus(Long ticketId, TicketStatus ticketStatus) {
        /*check if the ticket exists*/
        ticketsRepository.findById(ticketId).ifPresentOrElse(
                ticket -> {
                    ticket.setStatus(ticketStatus);
                    ticketsRepository.save(ticket);
                },
                taskException::ticketNotFound
        );

        return ticketMapper.mapToNormalDTO(getTicketByTicketId(ticketId));
    }

    @Override
    public TicketNormalDTO updateTicketPriorityLevel(Long ticketId, TicketPriority ticketPriority) {
        /*check if the ticket exists*/
        ticketsRepository.findById(ticketId).ifPresentOrElse(
                ticket -> {
                    ticket.setPriority(ticketPriority);
                    ticketsRepository.save(ticket);
                },
                taskException::ticketNotFound
        );

        return ticketMapper.mapToNormalDTO(getTicketByTicketId(ticketId));
    }
}