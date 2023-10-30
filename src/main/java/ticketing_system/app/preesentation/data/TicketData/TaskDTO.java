package ticketing_system.app.preesentation.data.TicketData;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

/**
 * the task DTO.
 * @author kamar baraka.*/
@Data
@Schema(title = "Task DTO",name = "TaskDTO",description = "the schema to create tasks")
public class TaskDTO implements DTOType{
    @SchemaProperty(name = "description")
    String description;
}