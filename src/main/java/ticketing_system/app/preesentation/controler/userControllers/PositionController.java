package ticketing_system.app.preesentation.controler.userControllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.implementation.userServiceImplementations.PositionServiceImpl;
import ticketing_system.app.preesentation.data.userDTOs.PositionDTO;


/**
 * The `PositionController` class provides a REST API for managing positions within the ticketing system.
 * It offers endpoints for creating, updating, retrieving, and deleting positions.
 *
 * <p>Dependencies:
 * - `PositionServiceImpl`: Handles position-related operations.
 *
 * <p>Example Usage:
 * PositionController positionController = new PositionController(positionService);
 * ResponseEntity<?> createdPosition = positionController.createPosition(authorizationHeader, positionCreatedEmail, departmentName, positionDTO);
 * ResponseEntity<?> updatedPosition = positionController.updatePosition(positionId, authorizationHeader, userUpdatedEmail, departmentName, positionDTO);
 * ResponseEntity<?> allPositions = positionController.retrievePositions(authorizationHeader);
 * ResponseEntity<?> positionById = positionController.retrievePositionById(authorizationHeader, positionId);
 * ResponseEntity<?> deletedPosition = positionController.deletePositionById(positionId, authorizationHeader);
 *
 * @author Maron
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/position")
@Api(value = "CRUD Rest APIs for position")
public class PositionController {
        private PositionServiceImpl positionService;

        @Autowired
        public PositionController(PositionServiceImpl positionService) {
            this.positionService = positionService;
        }

    @ApiOperation(value = "Create position REST API")
        @PostMapping("/create")
        public ResponseEntity<?> createPosition(@RequestHeader(name = "Authorization") String authorizationHeader,@RequestParam("positionCreatedEmail") String positionCreatedEmail, @RequestParam("departmentName") String departmentName, @RequestBody PositionDTO positionDTO){
            try {
                String token = authorizationHeader;
                System.out.println(positionCreatedEmail);
                return ResponseEntity.ok(positionService.createPosition(positionCreatedEmail,departmentName,token,positionDTO));
            }
            catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @ApiOperation(value = "Update position by Id REST API")
        @PutMapping("/update/{positionId}")
        public ResponseEntity<?> updateUser(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("positionId") Long positionId,@RequestParam("userUpdatedEmail") String userUpdatedEmail,@RequestParam("departmentName") String departmentName, @RequestBody PositionDTO positionDTO){
            try {
                System.out.println(positionId);
                String token = authorizationHeader;
                return ResponseEntity.ok(positionService.updatePosition(positionId,userUpdatedEmail,departmentName,token, positionDTO));
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @ApiOperation(value = "Get all positions REST API")
    @GetMapping("/retrieve")
    public ResponseEntity<?> retrievePositions(@RequestHeader(name = "Authorization") String authorizationHeader){
        try {
            String token = authorizationHeader;
            if (positionService.retrievePositions(token).isEmpty()) {
                System.out.println("no position found");
            }
            return ResponseEntity.ok(positionService.retrievePositions(token));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Get position by Id REST API")
    @GetMapping("/retrieveById/{positionId}")
    public ResponseEntity<?> retrievePositionById(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("positionId") Long positionId){
        try {
            String token = authorizationHeader;
            return ResponseEntity.ok(positionService.retrievePositionById(positionId,token));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Delete Position by Id REST API")
    @DeleteMapping("/deleteById/{positionId}")
    public ResponseEntity<?> deletePositionById(@RequestHeader(name = "Authorization") String authorizationHeader,@PathVariable("positionId") Long positionId){
        try {
            String token = authorizationHeader;
            if (positionService.deletePositionById(positionId,token)) {
                return ResponseEntity.ok("Position successfully deleted");
            }else{
                return ResponseEntity.badRequest().body("Not deleted. Try later..........");
            }
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
