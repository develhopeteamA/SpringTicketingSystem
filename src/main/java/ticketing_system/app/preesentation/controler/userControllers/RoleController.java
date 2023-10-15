package ticketing_system.app.preesentation.controler.userControllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.implementation.userServiceImplementations.RoleServiceImpl;
import ticketing_system.app.preesentation.data.userDTOs.RoleDTO;


/**
 * The `RoleController` class provides a REST API for managing roles within the ticketing system.
 * It offers endpoints for creating, updating, retrieving, and deleting roles.
 *
 * <p>Dependencies:
 * - `RoleServiceImpl`: Handles role-related operations.
 *
 * <p>Example Usage:
 * RoleController roleController = new RoleController(roleService);
 * ResponseEntity<?> createdRole = roleController.createRole(authorizationHeader, roleCreatedEmail, roleDTO);
 * ResponseEntity<?> updatedRole = roleController.updateRole(authorizationHeader, roleId, roleUpdatedEmail, roleDTO);
 * ResponseEntity<?> allRoles = roleController.retrieveRoles(authorizationHeader);
 * ResponseEntity<?> roleById = roleController.retrieveRoleById(authorizationHeader, roleId);
 * ResponseEntity<?> deletedRole = roleController.deleteRoleById(authorizationHeader, roleId);
 *
 * @author Maron
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/role")
@Tag(name = "CRUD Rest APIs for role")
public class RoleController {

    @Autowired
        private RoleServiceImpl roleService;

    @Operation(description = "Create role REST API")
        @PostMapping("/create")
        public ResponseEntity<?> createRole(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestParam("roleCreatedEmail") String roleCreatedEmail, @RequestBody RoleDTO roleDTO){
            try {
                String token = authorizationHeader;
                System.out.println(roleCreatedEmail);
                return ResponseEntity.ok(roleService.createRole(roleCreatedEmail,token,roleDTO));
            }
            catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @Operation(description = "Update role by Id REST API")
        @PutMapping("/update/{roleId}")
        public ResponseEntity<?> updateRole(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("roleId") Long roleId,@RequestParam("roleUpdatedEmail") String roleUpdatedEmail, @RequestBody RoleDTO roleDTO){
            try {
                String token = authorizationHeader;
                System.out.println(roleId);
                return ResponseEntity.ok(roleService.updateRole(roleId,roleUpdatedEmail,token,roleDTO));
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @Operation(description = "Get all roles REST API")
        @GetMapping("/retrieve")
        public ResponseEntity<?> retrieveRoles(@RequestHeader(name = "Authorization") String authorizationHeader){
        try {
            String token = authorizationHeader;
            if (roleService.retrieveRoles(token).isEmpty()) {
                System.out.println("no roles found");
            }
            return ResponseEntity.ok(roleService.retrieveRoles(token));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        }

    @Operation(description = "Get role by Id REST API")
        @GetMapping("/retrieveById/{roleId}")
        public ResponseEntity<?> retrieveRoleById(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("roleId") Long roleId){
            try {
                String token = authorizationHeader;
                return ResponseEntity.ok(roleService.retrieveRoleById(roleId,token));
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @Operation(description = "Delete role by Id REST API")
        @DeleteMapping("/deleteById/{roleId}")
        public ResponseEntity<?> deleteRoleById(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("roleId") Long roleId){
            try {
                String token = authorizationHeader;
                return ResponseEntity.ok(roleService.deleteRoleById(roleId,token));
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }


