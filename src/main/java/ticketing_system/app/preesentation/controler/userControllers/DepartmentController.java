package ticketing_system.app.preesentation.controler.userControllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.implementation.userServiceImplementations.DepartmentServiceImpl;
import ticketing_system.app.preesentation.data.userDTOs.DepartmentDTO;


/**
 * The `DepartmentController` class provides a REST API for managing departments within the ticketing system.
 * It offers endpoints for creating, updating, retrieving, and deleting departments.
 *
 * <p>Dependencies:
 * - `DepartmentServiceImpl`: Handles department-related operations.
 *
 * <p>Example Usage:
 * DepartmentController departmentController = new DepartmentController(departmentService);
 * ResponseEntity<?> createdDepartment = departmentController.createDepartment(authorizationHeader, departmentCreatedEmail, departmentDirectorEmail, departmentDTO);
 * ResponseEntity<?> updatedDepartment = departmentController.updateDepartment(departmentId, authorizationHeader, departmentUpdatedEmail, departmentDirectorEmail, departmentDTO);
 * ResponseEntity<?> allDepartments = departmentController.retrieveDepartments(authorizationHeader);
 * ResponseEntity<?> departmentById = departmentController.retrieveDepartmentById(authorizationHeader, departmentId);
 * ResponseEntity<?> deletedDepartment = departmentController.deleteDepartmentById(departmentId, authorizationHeader);
 *
 * @author Maron
 * @version 1.0
 * */
@RestController
@RequestMapping("/api/v1/department")
//@Api(value = "CRUD Rest APIs for department")
@Tag(name = "CRUD Rest APIs for department")
public class DepartmentController {
    private DepartmentServiceImpl departmentService;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(description = "Create department REST API")
    @PostMapping("/create")
    public ResponseEntity<?> createDepartment(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestParam("departmentCreatedEmail") String departmentCreatedEmail, @RequestParam("departmentDirectorEmail") String departmentDirectorEmail, @RequestBody DepartmentDTO departmentDTO){
        try {
            String token = authorizationHeader;
            System.out.println(departmentCreatedEmail);
            return ResponseEntity.ok(departmentService.createDepartment(departmentCreatedEmail,departmentDirectorEmail,token, departmentDTO));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(description = "update department REST API")
    @PutMapping("/update/{departmentId}")
    public ResponseEntity<?> updateDepartment(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("departmentId") Long departmentId,@RequestParam("departmentUpdatedEmail") String departmentUpdatedEmail,@RequestParam("departmentDirectorEmail") String departmentDirectorEmail, @RequestBody DepartmentDTO departmentDTO){
        try {
            String token = authorizationHeader;
            System.out.println(departmentId);
            return ResponseEntity.ok(departmentService.updateDepartment(departmentId,departmentUpdatedEmail,departmentDirectorEmail,token,departmentDTO));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(description = "Get all departments REST API")
    @GetMapping("/retrieve")
    public ResponseEntity<?> retrievePositions(@RequestHeader(name = "Authorization") String authorizationHeader){
       try {
           String token = authorizationHeader;

           if (departmentService.retrieveDepartments(token).isEmpty()) {
               return ResponseEntity.notFound().build();
           }
           return ResponseEntity.ok(departmentService.retrieveDepartments(token));
       }catch (IllegalArgumentException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @Operation(description = "Get department by Id REST API")
    @GetMapping("/retrieveById/{departmentId}")
    public ResponseEntity<?> retrieveDepartmentById(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("departmentId") Long departmentId){
        try {
            String token = authorizationHeader;
            return ResponseEntity.ok(departmentService.retrieveDepartmentById(departmentId,token));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(description = "Delete department by Id REST API")
    @DeleteMapping("/deleteById/{departmentId}")
    public ResponseEntity<?> deleteDepartmentById(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("departmentId") Long departmentId){
        try {
            String token = authorizationHeader;
            if (departmentService.deleteDepartmentById(departmentId,token)) {
                return ResponseEntity.ok("Department successfully deleted");
            }
            else{
                return ResponseEntity.badRequest().body("Department not deleted. Try later..........");
            }
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
