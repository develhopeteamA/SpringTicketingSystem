package ticketing_system.app.preesentation.data.TicketData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class TaskDTO {
    private Long id;
    private String description;
    private boolean complete;

    public TaskDTO(){
    }

    public TaskDTO(String description, boolean complete){

    }
}
