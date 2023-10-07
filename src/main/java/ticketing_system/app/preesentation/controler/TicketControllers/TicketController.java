package ticketing_system.app.preesentation.controler.TicketControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.implementation.TicketImplementation.TicketServiceImplementation;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;

@RestController
@RequestMapping(value = "/api/v1/ticket")
@AllArgsConstructor
@Tag(name = "Ticket API", description = "Perform CRUD operations on ticket")
public class TicketController {
    @Autowired
    private TicketServiceImplementation ticketService;

    @PostMapping
    @Operation(summary = "Create a ticket")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(ticketDTO)) ;
    }

    @GetMapping
    @Operation(summary = "Get a ticket by ticket id")
    public ResponseEntity<TicketDTO> getTicket(@RequestParam(value = "ticketId") Long ticketId){
        TicketDTO ticketDTO = ticketService.getTicket(ticketId);
        if(ticketDTO != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(ticketDTO);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    @Operation(summary = "Retrieve and Update a ticket using the ticket id")
    public ResponseEntity<TicketDTO> updateTicket(@RequestParam(value = "ticketId") Long ticketId, @RequestBody TicketDTO ticketDTO){
        return ResponseEntity.ok(ticketService.updateTicket(ticketId, ticketDTO)) ;
    }

    @DeleteMapping
    @Operation(summary = "Delete a ticket by ticket id")
    public void deleteTicket(@RequestParam(value = "ticketId") Long ticketId){
        ticketService.deleteTicket(ticketId);
    }
}
