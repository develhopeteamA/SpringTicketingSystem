package ticketing_system.app.preesentation.controler.userControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.implementation.userServiceImplementations.UserImpematation;

import ticketing_system.app.preesentation.data.userDTOs.UserDTO;

@RestController
@RequestMapping("/api/v1/sign")
@Tag(name = "Sign Up endpoint",description = "user to create account")
public class SignUpController {

        @Autowired
        UserImpematation userImpematation;

        @PostMapping("/createAccount")
        //@PreAuthorize("hasAuthority('ROLE_ANONYMOUS')")
        @Operation(description = "Create account REST API")
        public ResponseEntity<?> createUserAccount(@RequestParam("positionName") String positionName, @RequestBody UserDTO userDTO){
            try {
                String roleName = "user";
                return ResponseEntity.ok(userImpematation.createUserIn(roleName,positionName,userDTO));
            }
            catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    }
