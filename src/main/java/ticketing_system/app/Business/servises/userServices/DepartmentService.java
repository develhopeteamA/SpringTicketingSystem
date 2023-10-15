package ticketing_system.app.Business.servises.userServices;

import ticketing_system.app.percistance.Entities.userEntities.Department;
import ticketing_system.app.preesentation.data.userDTOs.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(String departmentCreatedEmail, String departmentDirectorEmail,String token,DepartmentDTO departmentDTO);
    List<DepartmentDTO> retrieveDepartments(String token);

    DepartmentDTO retrieveDepartmentById(Long departmentId,String token);

    DepartmentDTO updateDepartment(Long departmentId,String departmentCreatedEmail,String departmentDirectorEmail,String token, DepartmentDTO departmentDTO);

    Department retrieveDepartmentByName(String departmentName);

    boolean deleteDepartmentById(Long departmentId,String token);
}
