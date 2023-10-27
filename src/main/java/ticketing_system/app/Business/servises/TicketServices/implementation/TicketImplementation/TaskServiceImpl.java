package ticketing_system.app.Business.servises.TicketServices.implementation.TicketImplementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ticketing_system.app.Business.servises.TicketServices.TaskService;
import ticketing_system.app.Business.servises.TicketServices.utilities.TaskMapper;
import ticketing_system.app.Business.servises.TicketServices.utilities.impl.TicketMapperImpl;
import ticketing_system.app.exceptions.TaskNotFoundException;
import ticketing_system.app.exceptions.TicketNotFoundException;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.repositories.TicketRepositories.TasksRepository;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketsRepository;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TaskPresentationDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketFieldsDTO;

/**
 * the task service implementation.
 * @author kamar baraka.*/

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TasksRepository tasksRepository;
    private final TaskMapper taskMapper;
    private final ModelMapper mapper;

    @Autowired
    private TicketMapperImpl ticketMapper;

    @Autowired
    TicketServiceImpl ticketService;

    @Autowired
    TicketsRepository ticketsRepository;

    /**
     * create a task.
     * @param taskDTO a {@link TaskDTO} object
     * @return {@link TaskDTO}*/
    @Override
    public Tasks createTask(@Validated final TaskDTO taskDTO) {

        log.info("local service entry {}", taskDTO);
        /*convert to a Task*/
        Tasks task = mapper.map(taskDTO, Tasks.class);
        task.setDescription(taskDTO.getDescription());
        log.info("task {}", task);
        /*persist*/
        return tasksRepository.save(task);
    }

    @Override
    public TicketFieldsDTO addTaskToTicket(final long ticketId, @Validated final TaskDTO taskDTO) {

        log.warn("service entry {}", taskDTO);

        /*check if ticket exists*/
        ticketsRepository.findById(ticketId).ifPresentOrElse(
                ticket -> {
                    /*create the task*/
                    Tasks task = createTask(taskDTO);
                    /*add the task to the ticket*/
                    ticket.getTasks().add(task);
                    /*update the ticket*/
                    ticketsRepository.save(ticket);
                },() ->{
                    throw new TicketNotFoundException("Ticket Not  Found");
                }

        );

        log.warn("service pass {}", taskDTO);

        return ticketMapper.mapToAllParamsDTO(ticketService.getTicketByTicketId(ticketId));

    }

    @Override
    public TaskDTO updateTaskById(final long taskId,  TaskDTO taskDTO) {

        /*check if the task exists*/
        Tasks savedTask = tasksRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("no such task"));
        log.warn("Found Task");
        /*convert DTO to task*/
        log.warn("Get TaskDTO:" + taskDTO);
        Tasks task = taskMapper.mapToTask(taskDTO);
        log.warn("Mapped Task");
        task.setTaskId(savedTask.getTaskId());
        log.warn("Get Task ID:" + task);
        log.warn("Get Task ID:" + savedTask.getDescription());
        /*update the task*/
        return taskMapper.mapToDTO(tasksRepository.save(task));
    }

    @Override
    public void deleteTaskById(@Validated final long taskId, long ticketId) {

        /*check if the task exists and delete*/
//        tasksRepository.findById(taskId).ifPresent(tasks -> tasksRepository.deleteById(tasks.getTaskId()));
        tasksRepository.findById(taskId).ifPresentOrElse(
                task -> {
                    Tickets tickets = ticketService.getTicketByTicketId(ticketId);
                    tickets.getTasks().removeIf(t -> t.getTaskId() == taskId);;
                    log.warn(tickets.getTasks());
                    ticketsRepository.save(tickets);
                },
                () -> {
                    throw new TaskNotFoundException("no such task");
                }
        );

    }

    @Override
    public TaskPresentationDTO completeTaskById(Long taskId) {
        /*check if the task exists*/
        Tasks savedTask = tasksRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("no such task"));
        /*convert DTO to task*/
        savedTask.setComplete(true);
        /*update the task*/
        return taskMapper.mapToPreDTO(tasksRepository.save(savedTask));
    }

    @Override
    public TaskPresentationDTO getTaskById(Long taskId) {

        return taskMapper.mapToPreDTO(
                tasksRepository.findById(taskId).orElseThrow(
                        ()-> new TaskNotFoundException("Task Not Found"))
        );
    }
}
