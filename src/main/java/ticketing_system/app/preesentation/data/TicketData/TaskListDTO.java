package ticketing_system.app.preesentation.data.TicketData;

import lombok.Data;

import java.util.List;

@Data
public class TaskListDTO {
    private List<TaskDTO> tasks;
}
