package ticketing_system.app.preesentation.controler.userControllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    //@PreAuthorize("hasAuthority('admin')")
        public ResponseEntity<?> createRole(@RequestParam("roleCreatedEmail") String roleCreatedEmail, @RequestBody RoleDTO roleDTO){
            try {
               // String token = authorizationHeader;
                System.out.println(roleCreatedEmail);
                return ResponseEntity.ok(roleService.createRole(roleCreatedEmail,roleDTO));
            }
            catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @Operation(description = "Update role by Id REST API")
        @PutMapping("/update/{roleId}")
    //@PreAuthorize("hasAuthority('admin')")
        public ResponseEntity<?> updateRole(@PathVariable("roleId") Long roleId,@RequestParam("roleUpdatedEmail") String roleUpdatedEmail, @RequestBody RoleDTO roleDTO){
            try {
               // String token = authorizationHeader;
                System.out.println(roleId);
                return ResponseEntity.ok(roleService.updateRole(roleId,roleUpdatedEmail,roleDTO));
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @Operation(description = "Get all roles REST API")
        @GetMapping("/retrieve")
   // @PreAuthorize("hasAuthority('admin')")
        public ResponseEntity<?> retrieveRoles(){
        try {
            //String token = authorizationHeader;
            return ResponseEntity.ok(roleService.retrieveRoles());
            }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        }

    @Operation(description = "Get role by Id REST API")
   // @PreAuthorize("hasAuthority('admin')")
        @GetMapping("/retrieveById/{roleId}")
        public ResponseEntity<?> retrieveRoleById(@PathVariable("roleId") Long roleId){
            try {
               // String token = authorizationHeader;
                return ResponseEntity.ok(roleService.retrieveRoleById(roleId));
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @Operation(description = "Delete role by Id REST API")
        @DeleteMapping("/deleteById/{roleId}")
   // @PreAuthorize("hasAuthority('admin')")
        public ResponseEntity<?> deleteRoleById(@PathVariable("roleId") Long roleId){
            try {
                //String token = authorizationHeader;
                boolean isDeleted = roleService.deleteRoleById(roleId);
                if (isDeleted){
                    return ResponseEntity.ok("Role deleted successfully");
                }
                else {
                    return ResponseEntity.internalServerError().build();
                }
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }


