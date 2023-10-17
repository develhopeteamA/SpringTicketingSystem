package ticketing_system.app.Business.implementation.userServiceImplementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.userServices.UserService;
import ticketing_system.app.percistance.Entities.userEntities.Positions;
import ticketing_system.app.percistance.Entities.userEntities.Role;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.RoleRepository;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;
import ticketing_system.app.preesentation.data.userDTOs.UserDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The `UserImpematation` class is an implementation of the `{@link UserService}` interface and is responsible for managing user-related operations.
 * It provides methods for creating, retrieving, updating, and deleting users, as well as authentication and password validation.
 *
 * <p>This class uses the `ModelMapper` for converting between DTOs and entity objects and the `BCryptPasswordEncoder` for secure password hashing.
 *
 * <p>Dependencies:
 * - `ModelMapper`: Used for object mapping between DTOs and entity objects.
 * - `BCryptPasswordEncoder`: Used for securely hashing and verifying user passwords.
 * - `UserService`, `PositionService`, and `RoleService`: These interfaces provide the required business logic for user management, positions, and roles.
 * - `UserRepository`: Provides access to user data stored in the database.
 * - `JwtTokenProviderImpl`: Handles JWT-based user authorization.
 *
 * <p>Example Usage:
 * UserImpematation userService = new UserImpematation(modelMapper, userRepository, positionService, roleService);
 * UserDTO userDTO = new UserDTO(); // Initialize with user data
 * Users createdUser = userService.createUser(userEmailCreated, roleName, positionName, token, userDTO);
 *
 * Users retrievedUser = userService.retrieveUserById(userId, token);
 * userService.updateUser(userId, userUpdatingEmail, roleName, positionName, token, userDTO);
 * boolean deleted = userService.deleteUserById(userId, token);
 * Users authenticatedUser = userService.authenticateUser(email, password);
 *
 * @author Maron
 * @version 1.0
 */
@Service
@AllArgsConstructor
@Lazy
public class UserImpematation implements UserService  {

    Timestamp currentTimestamp = Timestamp.from(Instant.now());
    String pattern = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    String formattedTimestamp = currentTimestamp.toLocalDateTime().format(dateTimeFormatter);
    Timestamp currentTimestampFormatted = Timestamp.valueOf(formattedTimestamp);

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PositionServiceImpl positionService;
    private RoleServiceImpl roleService;

    @Autowired
    private JwtTokenProviderImpl tokenProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    public UserImpematation(ModelMapper modelMapper,
                            UserRepository userRepository,
                            PositionServiceImpl positionService,
                            RoleServiceImpl roleService)
    {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.positionService = positionService;
        this.roleService = roleService;
    }

    public UserDTO convertToDto(Users users){

        return modelMapper.map(users, UserDTO.class);
    }

    public Users convertToUser(UserDTO userDTO){

        return modelMapper.map(userDTO, Users.class);
    }
    public List<UserDTO> convertToListDTOs(List<Users> usersList) {
        return usersList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Users createUser(String userEmailCreated, String roleName, String positionName, UserDTO userDTO) {

        if (userDTO.getFirstName().isBlank() || userDTO.getFirstName() == null) {
            throw new IllegalArgumentException("Users first name can neither be null nor blank");
        }
        if (userDTO.getSurname().isBlank() || userDTO.getSurname() == null) {
            throw new IllegalArgumentException("Users Surname field cannot be null nor blank");
        }
        if (userDTO.getEmail().isBlank() || userDTO.getEmail() == null) {
            throw new IllegalArgumentException("Item description can neither be null nor blank");
        }

        if (userDTO == null) {
            throw new IllegalArgumentException("Users cannot be null");
        }

        Users user = convertToUser(userDTO);

        //set created on
        user.setCreatedOn(currentTimestampFormatted);

        //set position
        Positions userPosition = positionService.retrievePositionByName(positionName);
        user.setPositions(userPosition);
        //set role
        Role userRole = roleService.retrieveRoleByName(roleName);
        user.setRole(userRole);

        //set created by
        Users createdByUsers = retrieveUserByEmail(userEmailCreated);
        System.out.println(createdByUsers);
       user.setCreatedBy(createdByUsers.getCreatedBy());

       //enable
        user.setEnabled(true);

        // Hash the user's password before storing it in the database
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Users userInDb = userRepository.findByEmail(user.getEmail());
    if (userInDb !=null && userInDb.equals(user)){
        throw new IllegalArgumentException("User exist");
    }
        return userRepository.save(user);
    }

    @Override
    public List<Users> retrieveUsers() {
        //PermissionByRole permission = new PermissionByRoleImpl();
            return userRepository.findAll();

    }

    @Override
    public Users retrieveUserById(Long userId) {
        if (userRepository.existsById(userId)) {

            return userRepository.findById(userId).get();
        }
    return null;
    }

    @Override
    public Users retrieveUserByEmail(String email) {
        Users users = userRepository.findByEmail(email);

            System.out.println(users);
            return users;
    }
    @Override
    @Transactional
    public Users updateUserRole(String email, String newRoleName) {
        Users user = userRepository.findByEmail(email);
        if (user != null) {
            Role userNewRole = roleRepository.findRoleByRoleName(newRoleName);
            user.setRole(userNewRole);
           return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("no such user");
        }
        // return userRepository.updateRoleByEmail(email, newRoleName);
    }

    @Override
    public Users updateUser(Long userId,String userUpdatingEmail,String roleName,String positionName,UserDTO userDTO) {
        if (userRepository.existsById(userId)) {

            System.out.println(userId);

            Users user = convertToUser(userDTO);
            //set id
            user.setId(userId);

            //set updated on
            user.setUpdatedOn(currentTimestampFormatted);

            //set enable
            user.setEnabled(true);
            //set position
            Positions userPosition = positionService.retrievePositionByName(positionName);
            user.setPositions(userPosition);
            //set role
            Role userRole = roleService.retrieveRoleByName(roleName);
            user.setRole(userRole);

            //set updated by
            Users updatedByUsers = retrieveUserByEmail(userUpdatingEmail);
            System.out.println(updatedByUsers);
            user.setUpdatedBy(updatedByUsers.getId());

            //PermissionByRole permission = new PermissionByRoleImpl();


            return userRepository.save(user);

        }
        return null;
    }
    @Override
    public boolean deleteUserById(Long userId) {
        if (userRepository.existsById(userId)){

            userRepository.deleteById(userId);
            return true;

    }
        return false;
    }

    @Override
    public Users authenticateUser(String email, String password) {
        Users user = userRepository.findByEmail(email);

        if (user!=null && isPasswordValid(password,user.getPassword())){
            return user;
        }
        return null;
    }

    public boolean isPasswordValid(String inputPassword, String storedPassword) {
        // Verify a password provided by a user against the stored password hash
        return bCryptPasswordEncoder.matches(inputPassword, storedPassword);
    }


    @Override
    public Users createUserIn(String roleName, String positionName,UserDTO userDTO) {

        if (userDTO.getFirstName().isBlank() || userDTO.getFirstName() == null) {
            throw new IllegalArgumentException("Users first name can neither be null nor blank");
        }
        if (userDTO.getSurname().isBlank() || userDTO.getSurname() == null) {
            throw new IllegalArgumentException("Users Surname field cannot be null nor blank");
        }
        if (userDTO.getEmail().isBlank() || userDTO.getEmail() == null) {
            throw new IllegalArgumentException("Item description can neither be null nor blank");
        }

        if (userDTO == null) {
            throw new IllegalArgumentException("Users cannot be null");
        }
        String creatorEmail = userDTO.getEmail();

        Users user = convertToUser(userDTO);

        //set created on
        user.setCreatedOn(currentTimestampFormatted);

        //set position
        Positions userPosition = positionService.retrievePositionByName(positionName);
        user.setPositions(userPosition);
        //set role
        Role userRole = roleService.retrieveRoleByName(roleName);
        user.setRole(userRole);

        //set created by
        Users createdByUsers = retrieveUserByEmail(creatorEmail);
        System.out.println(createdByUsers);
        user.setCreatedBy(0);

        //set enabled
        user.setEnabled(true);

        // Hash the user's password before storing it in the database
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Users userInDb = userRepository.findByEmail(user.getEmail());
        if (userInDb != null && userInDb.equals(user)) {
            throw new IllegalArgumentException("User exist");
        }

        Users savedUsers = this.userRepository.save(user);
        return savedUsers;
    }


}
