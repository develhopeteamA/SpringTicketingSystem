package ticketing_system.app.Business.servises.TicketServices;


import org.springframework.validation.annotation.Validated;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TaskPresentationDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketFieldsDTO;

/**
 * the task service.
 * @author kamar baraka.*/

public interface TaskService {

    Tasks createTask(@Validated TaskDTO taskDTO);

    TicketFieldsDTO addTaskToTicket(long ticketId, TaskDTO taskDTO);

    TaskDTO updateTaskById(long taskId, TaskDTO taskDTO);

    void deleteTaskById(long taskId, long ticketId);

    TaskPresentationDTO completeTaskById(Long taskId);
}