package ticketing_system.app.preesentation.controler.TicketControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.implementation.TicketImplementation.TicketServiceImplementation;
import ticketing_system.app.percistance.Enums.TicketPriorityLevel;
import ticketing_system.app.percistance.Enums.TicketStatus;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TaskListDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ticket")
@AllArgsConstructor
@Tag(name = "Ticket API", description = "Perform CRUD operations on ticket")
public class TicketController {
    @Autowired
    private TicketServiceImplementation ticketService;

    @PostMapping
    @Operation(summary = "Create a ticket", method="POST")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(ticketDTO)) ;
    }

    @GetMapping
    @Operation(summary = "Get a ticket by ticket id", method="GET")
    public ResponseEntity<TicketDTO> getTicket(@RequestParam(value = "ticketId") Long ticketId){
        TicketDTO ticketDTO = ticketService.getTicket(ticketId);
        if(ticketDTO != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(ticketDTO);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    @Operation(summary = "Retrieve and Update a ticket using the ticket id", method="PUT")
    public ResponseEntity<TicketDTO> updateTicket(@RequestParam(value = "ticketId") Long ticketId, @RequestBody TicketDTO ticketDTO){
        TicketDTO ticketDTO2 = ticketService.updateTicket(ticketId, ticketDTO);
        if(ticketDTO2 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(ticketDTO2) ;
    }

    @DeleteMapping
    @Operation(summary = "Delete a ticket by ticket id", method="DELETE")
    public void deleteTicket(@RequestParam(value = "ticketId") Long ticketId){
        ticketService.deleteTicket(ticketId);
    }
    @PutMapping(value = "/assign-ticket")
    @Operation(summary = "Retrieve and assign a ticket to an agent", method="PUT")
    public ResponseEntity<TicketDTO> assignTicket(@RequestParam(value = "ticketId") Long ticketId, @RequestParam(value = "userEmail") String userEmail){
        return ResponseEntity.ok(ticketService.assignTicket(ticketId, userEmail)) ;
    }

    @PutMapping(value = "/update-status")
    @Operation(summary = "Retrieve and update ticket status", method="PUT")
    public ResponseEntity<TicketDTO> updateTicketStatus(@RequestParam(value = "ticketId") Long ticketId, @RequestParam(value = "ticketStatus") TicketStatus ticketStatus){
        return ResponseEntity.ok(ticketService.updateTicketStatus(ticketId, ticketStatus)) ;
    }
    @PutMapping(value = "/update-priority-level")
    @Operation(summary = "Retrieve and update ticket priority", method="PUT")
    public ResponseEntity<TicketDTO> updateTicketPriorityLevel(@RequestParam(value = "ticketId") Long ticketId, @RequestParam(value = "ticketPriorityLevel") TicketPriorityLevel ticketPriorityLevel){
        return ResponseEntity.ok(ticketService.updateTicketPriorityLevel(ticketId, ticketPriorityLevel)) ;
    }

    @PutMapping(value = "/add-task")
    @Operation(summary = "Add task to an exising ticket", method="PUT")
    public ResponseEntity<?> addTaskToTicket(@RequestParam(value = "ticketId") Long ticketId, @RequestBody TaskListDTO taskList){
//        ticketService.addTaskToTicket(ticketId, taskList.getTasks())
        return ResponseEntity.ok(ticketService.addTaskToTicket(ticketId, taskList.getTasks())) ;
    }
}
