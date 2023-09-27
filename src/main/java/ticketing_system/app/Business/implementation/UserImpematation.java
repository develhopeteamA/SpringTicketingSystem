package ticketing_system.app.Business.implementation;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ticketing_system.app.Business.servises.User_service;
import ticketing_system.app.percistance.Entities.User;
import ticketing_system.app.percistance.repositories.Userrepository;
import ticketing_system.app.preesentation.data.UserDTO;

@Service
@AllArgsConstructor
public class UserImpematation implements User_service {

    private ModelMapper modelMapper;
    private Userrepository userrepository;

    private UserDTO convertToDto(User user){

        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO){

        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.convertToUser(userDTO);

        User savedUser = this.userrepository.save(user);

        return this.convertToDto(savedUser);
    }

    @Transactional
    @Override
    public void deleteUserById(long id) {

        this.userrepository.deleteById(id);

    }
}
