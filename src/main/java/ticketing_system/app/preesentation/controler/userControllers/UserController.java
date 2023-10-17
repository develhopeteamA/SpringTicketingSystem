package ticketing_system.app.preesentation.controler.userControllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.implementation.userServiceImplementations.UserImpematation;
import ticketing_system.app.preesentation.data.userDTOs.UserDTO;


/**
 * The `UserController` class provides a REST API for managing users within the ticketing system.
 * It offers endpoints for creating, updating, retrieving, and deleting users.
 *
 * <p>Dependencies:
 * - `UserImpematation`: Handles user-related operations.
 *
 * <p>Example Usage:
 * UserController userController = new UserController(userImplementation);
 * ResponseEntity<?> createdUser = userController.createUser(authorizationHeader, userCreatedEmail, positionName, roleName, userDTO);
 * ResponseEntity<?> updatedUser = userController.updateUser(userId, authorizationHeader, userUpdatedEmail, positionName, roleName, userDTO);
 * ResponseEntity<?> allUsers = userController.retrieveUsers(authorizationHeader);
 * ResponseEntity<?> userById = userController.retrieveUserById(authorizationHeader, userId);
 * ResponseEntity<?> deletedUser = userController.deleteUserById(userId, authorizationHeader);
 *
 * @author Maron
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/user")
@Tag(name = "CRUD Rest APIs for User")
//@Tag(name = "user api", description = "perform CRUD operations on user")
public class UserController {
    private UserImpematation userImplementation;

    @Autowired
    public UserController(UserImpematation userImplementation) {
        this.userImplementation = userImplementation;
    }

    @Operation(description = "Create user REST API")
    @PostMapping("/create")
    //@PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> createUser(@RequestParam("userCreatedEmail") String userCreatedEmail,@RequestParam("positionName") String positionName,@RequestParam("roleName") String roleName, @RequestBody UserDTO userDTO){
        try {
            //String token = authorizationHeader;
            System.out.println(userCreatedEmail);
            return ResponseEntity.ok(userImplementation.createUser(userCreatedEmail,roleName,positionName, userDTO));
        }
         catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(description = "Update user by Id REST API")
    @PutMapping("/update/{userId}")
    //@PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId,@RequestParam("userUpdatedEmail") String userUpdatedEmail,@RequestParam("positionName") String positionName,@RequestParam("roleName") String roleName, @RequestBody UserDTO userDTO){
        try {
            //String token = authorizationHeader;
            System.out.println(userId);
            return ResponseEntity.ok(userImplementation.updateUser(userId,userUpdatedEmail,roleName,positionName,userDTO));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/elevateUserRole")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUserRole(@RequestParam("email") String email, @RequestParam("UserNewRoleName") String newRoleName){
        return ResponseEntity.ok(userImplementation.updateUserRole(email,newRoleName));
    }

    @Operation(description = "Get all users REST API")
    @GetMapping("/retrieve")
    //@PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> retrieveUsers(){
       // String token = authorizationHeader;
        return ResponseEntity.ok(userImplementation.retrieveUsers());
    }

    @Operation(description = "Get user by Id REST API")
    @GetMapping("/retrieveById/{userId}")
    public ResponseEntity<?> retrieveUserById(@PathVariable("userId") Long userId){
        try {
           // String token = authorizationHeader;
            return ResponseEntity.ok(userImplementation.retrieveUserById(userId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(description = "Delete user by Id REST API")
    @DeleteMapping("/deleteById/{userId}")
    //@PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId){
        try {
            //String token = authorizationHeader;
            boolean isDeleted = userImplementation.deleteUserById(userId);
            if (isDeleted){
                return ResponseEntity.ok("User deleted successfully");
            }
            else {
                return ResponseEntity.internalServerError().build();
            }

        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
