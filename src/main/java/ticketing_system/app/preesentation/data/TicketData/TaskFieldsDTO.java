package ticketing_system.app.preesentation.data.TicketData;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class TaskFieldsDTO implements DTOType{
    private String description;
    private boolean complete;
}
