package ticketing_system.app.preesentation.controler.userControllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@Api(value = "CRUD Rest APIs for User")
//@Tag(name = "user api", description = "perform CRUD operations on user")
public class UserController {
    private UserImpematation userImplementation;

    @Autowired
    public UserController(UserImpematation userImplementation) {
        this.userImplementation = userImplementation;
    }

    @ApiOperation(value = "Create user REST API")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestParam("userCreatedEmail") String userCreatedEmail,@RequestParam("positionName") String positionName,@RequestParam("roleName") String roleName, @RequestBody UserDTO userDTO){
        try {
            String token = authorizationHeader;
            System.out.println(userCreatedEmail);
            return ResponseEntity.ok(userImplementation.createUser(userCreatedEmail,roleName,positionName,token, userDTO));
        }
         catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getStackTrace());
        }
    }

    @ApiOperation(value = "Update user by Id REST API")
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId,@RequestHeader(name = "Authorization") String authorizationHeader,@RequestParam("userUpdatedEmail") String userUpdatedEmail,@RequestParam("positionName") String positionName,@RequestParam("roleName") String roleName, @RequestBody UserDTO userDTO){
        try {
            String token = authorizationHeader;
            System.out.println(userId);
            return ResponseEntity.ok(userImplementation.updateUser(userId,userUpdatedEmail,roleName,positionName,token,userDTO));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Get all users REST API")
    @GetMapping("/retrieve")
    public ResponseEntity<?> retrieveUsers(@RequestHeader(name = "Authorization") String authorizationHeader){
        String token = authorizationHeader;
        return ResponseEntity.ok(userImplementation.retrieveUsers(token));
    }

    @ApiOperation(value = "Get user by Id REST API")
    @GetMapping("/retrieveById/{userId}")
    public ResponseEntity<?> retrieveUserById(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("userId") Long userId){
        try {
            String token = authorizationHeader;
            return ResponseEntity.ok(userImplementation.retrieveUserById(userId,token));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Delete user by Id REST API")
    @DeleteMapping("/deleteById/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId,@RequestHeader(name = "Authorization") String authorizationHeader){
        try {
            String token = authorizationHeader;
            boolean isDeleted = userImplementation.deleteUserById(userId,token);
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
