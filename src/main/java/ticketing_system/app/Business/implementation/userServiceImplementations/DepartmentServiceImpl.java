package ticketing_system.app.Business.implementation.userServiceImplementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.userServices.DepartmentService;
import ticketing_system.app.percistance.Entities.userEntities.Department;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.DepartmentRepository;
import ticketing_system.app.preesentation.data.userDTOs.DepartmentDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The `DepartmentServiceImpl` class is responsible for managing department-related operations within the ticketing system.
 * It provides methods for creating, retrieving, updating, and deleting departments, as well as mapping department data to DTOs.
 *
 * <p>Dependencies:
 * - `JwtTokenProviderImpl`: Provides user authentication and authorization via JWT tokens.
 * - `UserImpematation`: Handles user-related operations.
 *
 * <p>Example Usage:
 * DepartmentServiceImpl departmentService = new DepartmentServiceImpl(jwtTokenProvider, userImpematation);
 * DepartmentDTO newDepartment = departmentService.createDepartment(departmentCreatedEmail, departmentDirectorEmail, token, departmentDTO);
 * List<DepartmentDTO> allDepartments = departmentService.retrieveDepartments(token);
 * DepartmentDTO updatedDepartment = departmentService.updateDepartment(departmentId, departmentCreatedEmail, departmentDirectorEmail, token, departmentDTO);
 * boolean isDeleted = departmentService.deleteDepartmentById(departmentId, token);
 *
 * @author [Your Name]
 * @version 1.0
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    Timestamp currentTimestamp = Timestamp.from(Instant.now());
    String pattern = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    String formattedTimestamp = currentTimestamp.toLocalDateTime().format(dateTimeFormatter);
    Timestamp currentTimestampFormatted = Timestamp.valueOf(formattedTimestamp);


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JwtTokenProviderImpl tokenProvider;

    @Lazy
    @Autowired
    private UserImpematation userImpematation;

    public DepartmentDTO convertToDto(Department department){

        return modelMapper.map(department, DepartmentDTO.class);
    }

    public Department convertToDepartment(DepartmentDTO departmentDTO){

        return modelMapper.map(departmentDTO, Department.class);
    }

    public List<DepartmentDTO> convertToListDTOs(List<Department> departmentList) {
        return departmentList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO createDepartment(String departmentCreatedEmail,String departmentDirectorEmail,String token,DepartmentDTO departmentDTO) {
        if (departmentDTO.getDepartmentName().isBlank() || departmentDTO.getDepartmentName() == null) {
            throw new IllegalArgumentException("Item name can neither be null nor blank");
        }
        if (departmentDTO.getDescription().isBlank() || departmentDTO.getDescription() == null) {
            throw new IllegalArgumentException("Item description can neither be null nor blank");
        }

        if (departmentDTO == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Department department = convertToDepartment(departmentDTO);
        //set created on
        department.setCreatedOn(currentTimestampFormatted);

        //set department director
        Users departmentDirector = userImpematation.retrieveUserByEmail(departmentDirectorEmail);
        department.setDepartmentDirector(departmentDirector);

        //set created by
        Users createdByUsers = userImpematation.retrieveUserByEmail(departmentCreatedEmail);
        System.out.println(createdByUsers);
        department.setCreatedBy(createdByUsers.getCreatedBy());

        //Department departments = convertToDepartment(department);
        if(tokenProvider.loggedInUserRole(token).equalsIgnoreCase("admin")) {

        return this.convertToDto(departmentRepository.save(department));
        }else {
            throw new IllegalArgumentException("Only admin can perform");
        }
    }

    @Override
    public List<DepartmentDTO> retrieveDepartments(String token) {
        if(tokenProvider.loggedInUserRole(token).equalsIgnoreCase("admin")) {
            return this.convertToListDTOs(departmentRepository.findAll());
        }
        else {throw new IllegalArgumentException("Only admin can perform");}
    }

    @Override
    public DepartmentDTO retrieveDepartmentById(Long departmentId,String token) {
        if (departmentRepository.existsById(departmentId)) {
            if(tokenProvider.loggedInUserRole(token).equalsIgnoreCase("admin")) {
                return this.convertToDto(departmentRepository.findById(departmentId).get());
            }else {
                throw new IllegalArgumentException("Only admin can perform");
            }
        }
        throw new IllegalArgumentException("Department not found");
    }


    @Override
    public DepartmentDTO updateDepartment(Long departmentId,String departmentCreatedEmail,String departmentDirectorEmail,String token, DepartmentDTO departmentDTO) {
        if (departmentRepository.existsById(departmentId)){

            Department department = convertToDepartment(departmentDTO);
            //set department Id
            department.setDepartmentId(departmentId);
            //set updated on
            department.setUpdatedOn(currentTimestampFormatted);


            //set department director
            Users departmentDirector = userImpematation.retrieveUserByEmail(departmentDirectorEmail);
            department.setDepartmentDirector(departmentDirector);

            //set created by
            Users createdByUsers = userImpematation.retrieveUserByEmail(departmentCreatedEmail);
            System.out.println(createdByUsers);
            department.setUpdatedBy(createdByUsers.getCreatedBy());
            if(tokenProvider.loggedInUserRole(token).equalsIgnoreCase("admin")) {
             return convertToDto(departmentRepository.save(department));
        }else {
            throw new IllegalArgumentException("Only admin can perform");
            }
        }
        throw new IllegalArgumentException("Department not found");
    }

    @Override
    public Department retrieveDepartmentByName(String departmentName) {
        Department department = departmentRepository.findByDepartmentName(departmentName);
        if (department!= null){
            return department;
        }
        return null;
    }

    @Override
    public boolean deleteDepartmentById(Long departmentId,String token) {
        if (departmentRepository.existsById(departmentId)) {
            if(tokenProvider.loggedInUserRole(token).equalsIgnoreCase("admin")) {
                departmentRepository.deleteById(departmentId);
                return true;
            }else {
                throw new IllegalArgumentException("Only admin can perform");
            }
        }
        throw new IllegalArgumentException("Department not found");
    }
}
