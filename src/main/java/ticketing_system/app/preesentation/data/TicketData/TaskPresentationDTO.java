package ticketing_system.app.preesentation.data.TicketData;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;
import org.hibernate.annotations.Type;

/**
 * the task presentation DTO.
 * @author kamar baraka.*/

@Schema(title = "Task Representation", name = "TaskPresentationDTO",description = "a representation of a task")
@Data
public class TaskPresentationDTO implements DTOType{
        @SchemaProperty(name = "description")
        String description;
        @SchemaProperty(name = "complete", schema = @Schema(implementation = Boolean.class))
        boolean complete;

        public TaskPresentationDTO(){}
}

