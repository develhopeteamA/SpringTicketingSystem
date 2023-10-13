package ticketing_system.app.Business.servises.userServices;

import ticketing_system.app.percistance.Entities.userEntities.Role;
import ticketing_system.app.preesentation.data.userDTOs.RoleDTO;

import java.util.List;

public interface RoleService {
    Role createRole(String roleCreatedEmail,String token, RoleDTO roleDTO);
    List<RoleDTO> retrieveRoles(String token);

    RoleDTO retrieveRoleById(Long roleId,String token);

    Role updateRole(Long roleId,String roleUpdatedEmail,String token, RoleDTO roleDTO);
    Role retrieveRoleByName(String roleName);

    boolean deleteRoleById(Long roleId,String token);
}
