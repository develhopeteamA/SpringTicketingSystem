package ticketing_system.app.Business.servises.userServices;

import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.preesentation.data.userDTOs.UserDTO;

import java.util.List;

public interface UserService {
    Users createUser(String createdByEmail,String positionName, String roleName,String token,UserDTO userDTO);
    List<Users> retrieveUsers(String token);

    Users retrieveUserById(Long userId,String token);
    Users retrieveUserByEmail(String email);

    Users updateUser(Long userId,String userUpdatedEmail,String positionName,String roleName, String token,UserDTO userDTO);

    boolean deleteUserById(Long userId,String token);
    Users authenticateUser(String email, String password);
}
