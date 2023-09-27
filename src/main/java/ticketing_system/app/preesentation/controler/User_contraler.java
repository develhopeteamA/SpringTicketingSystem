package ticketing_system.app.preesentation.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.servises.User_service;
import ticketing_system.app.preesentation.data.UserDTO;

@RestController
@RequestMapping(value = {"/api/v1/users"})
@AllArgsConstructor
@Tag(name = "user api", description = "perform CRUD operations on user")
public class User_contraler {

    private User_service userService;

    @PutMapping
    @Operation(summary = "create a user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){

        UserDTO user = this.userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping(value = {"{id}"})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(summary = "delete a user")
    public void deleteUser(@PathVariable("id") long id){

        this.userService.deleteUserById(id);
    }
}
