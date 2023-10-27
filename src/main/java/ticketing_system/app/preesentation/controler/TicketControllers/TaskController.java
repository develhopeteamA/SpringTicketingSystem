package ticketing_system.app.preesentation.controler.TicketControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Access;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.servises.TicketServices.TaskService;
import ticketing_system.app.preesentation.data.TicketData.CreatedDTO;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/tasks")
@Tag(name = "Task Api", description = "CRUD operations for Task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping(value = {"task/{ticketId}"})
    @Operation(summary = "Add task", description = "Add a task to a ticket")
    public ResponseEntity<?> addTaskToTicket(@PathVariable("ticketId") final long ticketId ,
                                             @RequestBody TaskDTO task){
//        log.warn("passed {}", task);
//
//        /*create the task*/
//        ticketService.addTaskToTicket(ticketId, task);
//        /*create the response*/
//        EntityModel<DTOType> response = EntityModel.of(new CreatedDTO("task added successfully. click the link to see"));
//        /*create links*/
//        Link ticketLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).
//                getTicketById("USER", ticketId)).withRel("ticket");
//
//        /*add links*/
//        response.add(ticketLink);
//
//        log.warn("creed {}at", task);

        try{

            return ResponseEntity.status(201).body(taskService.addTaskToTicket(ticketId, task));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }

        /*create response*/

    }
    @PutMapping(value = "/update")
    @Operation(summary = "update task", description = "Update a task by ID")
    public ResponseEntity<?> updateTaskById(@RequestParam(value = "taskId")Long taskId, @RequestBody TaskDTO taskDTO){
        try{
            return ResponseEntity.ok(taskService.updateTaskById(taskId, taskDTO));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping(value = "/complete")
    @Operation(summary = "complete task", description = "Mark task as completed")
    public ResponseEntity<?> completeTaskById(@RequestParam(value = "taskId")Long taskId){
        try{
            return ResponseEntity.ok(taskService.completeTaskById(taskId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "delete task", description = "Delete a task by ID")
    public ResponseEntity<EntityModel<CreatedDTO>> deleteTicketById(@RequestParam(value = "ticketId")Long ticketId, @RequestParam(value = "taskId")Long taskId){
        taskService.deleteTaskById(taskId, ticketId);

        EntityModel<CreatedDTO> response = EntityModel.of(new CreatedDTO("Task successfully deleted"));

        return ResponseEntity.ok(response);
    }


}
