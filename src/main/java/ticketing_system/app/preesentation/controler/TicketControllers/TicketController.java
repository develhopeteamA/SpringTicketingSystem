package ticketing_system.app.preesentation.controler.TicketControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.servises.TicketServices.TicketService;
import ticketing_system.app.Business.servises.TicketServices.utilities.TaskMapper;
import ticketing_system.app.Business.servises.TicketServices.utilities.TicketMapper;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Enums.TicketPriority;
import ticketing_system.app.percistance.Enums.TicketStatus;
import ticketing_system.app.preesentation.data.TicketData.*;


import java.util.List;

/**
 * the ticket controller.
 * @author kamar baraka.*/

@Log4j2
@RestController
@RequestMapping(value = {"/api/v1/tickets"})
@RequiredArgsConstructor
@Tag(name = "Ticket Api", description = "the ticket API exposing ticket operations")
public class TicketController {

    /*inject dependencies*/
    private final TaskMapper taskMapper;
    private final TicketMapper ticketMapper;
    private final TicketService ticketService;

    @GetMapping
    @Parameter(name = "user role",required = false)
    @Operation(tags = {"Ticket Api"},summary = "get all tickets",description = "get all tickets in the database")
    public ResponseEntity<List<? extends EntityModel<? extends DTOType>>> getAllTickets(@RequestHeader(value = "ROLE",required = false,defaultValue = "USER")
                                                                                        String  userRole){

        /*get all tickets*/
        List<Tickets> allTickets = ticketService.getAllTickets();
        List<Long> listIds = allTickets.stream().map(Tickets::getTicketId).toList();
        List<? extends DTOType> allTicketsDTO;
        /*get DTO based on roles*/
        switch (userRole){
            case "AGENT" -> allTicketsDTO = allTickets.stream().map(ticketMapper::mapToAgentDTO).toList();
            case "ADMIN" -> allTicketsDTO = allTickets;
            case "USER" -> allTicketsDTO = allTickets.stream().map(ticketMapper::mapToNormalDTO).toList();
            default -> allTicketsDTO = null;

        }

        assert allTicketsDTO != null;
        final int[] index = {0};
        /*create the Entity model*/
        List<? extends EntityModel<? extends DTOType>> entityModels = allTicketsDTO.stream().map(EntityModel::of).toList();
        /*add links*/
        entityModels = entityModels.stream().peek(entity ->
        {

            entity.add(WebMvcLinkBuilder.linkTo(TicketController.class).
                    slash("byId").slash(listIds.get(index[0])).withRel("ticket_details"));
            index[0]++;
        }).toList();

        /*create and return a response*/
        return ResponseEntity.ok(entityModels);

    }

    @GetMapping(value = {"byName/{name}"})
    @Operation(summary = "get ticket", description = "get ticket by name")
    public ResponseEntity<?> getTicketByName(@RequestHeader(name = "ROLE", defaultValue = "USER") String userRole ,
                                                                   @PathVariable("name") String name){

        /*get ticket by name*/
        List<Tickets> ticketByName = ticketService.getTicketByName(name);
        /*declare a ticket*/
        List<? extends DTOType> tickets;
        try {
            /*get DTO based on a role*/
            switch (userRole) {
                case "USER" -> tickets = ticketByName.stream().map(ticketMapper::mapToNormalDTO).toList();
                case "ADMIN" -> tickets = ticketByName;
                case "AGENT" -> tickets = ticketByName.stream().map(ticketMapper::mapToAgentDTO).toList();
                default -> tickets = null;
            }

            assert tickets != null;
            /*create an entity model*/
            //EntityModel<? extends List<? extends DTOType>> listEntityModel = EntityModel.of(tickets);

            /*return the response*/
            return ResponseEntity.ok(tickets);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }

    }

    @GetMapping(value = {"byId/{ticketId}"})
    @Operation(summary = "get ticket", description = "get a ticket by ID")
    public ResponseEntity<?> getTicketById(@RequestHeader(name = "ROLE", defaultValue = "USER")
                                                              String userRole, @PathVariable("ticketId")
                                                              long ticketId){

        try{
            /*get a ticket by the ID*/
            Tickets ticketByTicketId = ticketService.getTicketByTicketId(ticketId);
            DTOType ticketDTO;
            /*get DTO based on the role*/
            switch (userRole){

                case "USER" -> ticketDTO = ticketByTicketId;
                case "AGENT" -> ticketDTO = ticketByTicketId;
                case "ADMIN" -> ticketDTO = ticketByTicketId;
                default -> ticketDTO = null;
            }
            assert ticketDTO != null;
            /*create an entity model*/
            EntityModel<DTOType> dtoType = EntityModel.of(ticketByTicketId);
            /*add a link*/


            /*return the response*/
            return ResponseEntity.ok(dtoType);

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    /**
     * create ticket
     */
    @PostMapping
    @Operation(tags = {"Ticket Api"},summary = "create ticket",description = "create a ticket" )
    public ResponseEntity<?> createTicket(@Validated @RequestBody final TicketDTO ticketDTO){

//        /*create the ticket*/
//        Tickets ticket = ticketService.createTicket(ticketDTO);
//
//        /*create the response*/
//        EntityModel<CreatedDTO> response = EntityModel.of(new CreatedDTO("successfully Created"),
//                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).getTicketById(
//                        "USER", ticket.getTicketId()
//                )).withRel("the ticket")
//        );

        try{
            return ResponseEntity.status(201).body(ticketService.createTicket(ticketDTO));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }

    }


    @PutMapping(value = "/update")
    @Operation(summary = "update ticket", description = "Update a ticket by ID")
    public ResponseEntity<?> updateTicketById(@RequestParam(value = "ticket_id")Long ticketId, @RequestBody TicketDTO ticketDTO){
       try{
           return ResponseEntity.ok(ticketService.updateTicketById(ticketId, ticketDTO));
       } catch (Exception e){
           return ResponseEntity.badRequest().body(e);
       }
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "delete ticket", description = "Delete a ticket by ID")
    public ResponseEntity<EntityModel<CreatedDTO>> deleteTicketById(@RequestParam(value = "ticket_id")Long ticketId){
        ticketService.deleteTicketById(ticketId);

        EntityModel<CreatedDTO> response = EntityModel.of(new CreatedDTO("Ticket successfully deleted"));

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/assign")
    @Operation(summary = "assign ticket", description = "Assign a ticket to an agent")
    public ResponseEntity<?> assignTicketToAgent(@RequestParam(value = "ticket_id") Long ticketId,
                                                              @RequestParam(value = "user_id") Long userId){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.assignTicketToAgent(ticketId, userId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(value = "/update-status")
    @Operation(summary = "update ticket status", description = "Retrieve and update ticket status by ticket id")
    public ResponseEntity<?> updateTicketStatus(@RequestParam(value = "ticketId") Long ticketId, @RequestParam(value = "ticketStatus") String ticketStatus){
        try {
            return ResponseEntity.ok(ticketService.updateTicketStatus(ticketId, TicketStatus.valueOf(ticketStatus))) ;

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
    @PutMapping(value = "/update-priority-level")
    @Operation(summary = "update ticket priority", description= "Retrieve and update ticket priority")
    public ResponseEntity<?> updateTicketPriorityLevel(@RequestParam(value = "ticketId") Long ticketId, @RequestParam(value = "ticketPriorityLevel") String ticketPriorityLevel){
        try{
            return ResponseEntity.ok(ticketService.updateTicketPriorityLevel(ticketId, TicketPriority.valueOf(ticketPriorityLevel))) ;
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping(value = "/close")
    @Operation(summary = "close ticket", description= "Admin can close ticket")
    public ResponseEntity<?> closeTicket(@RequestParam(value = "ticketId") Long ticketId){
        try {
            ticketService.closeTicket(ticketId);
            return ResponseEntity.ok("Ticket successfully closed!") ;

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}