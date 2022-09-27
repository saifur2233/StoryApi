package com.example.storyapi.services;

import com.example.storyapi.converter.UserConverter;
import com.example.storyapi.dto.UserDTO;
import com.example.storyapi.exceptions.DuplicateEmailException;
import com.example.storyapi.exceptions.EntityNotFoundException;
import com.example.storyapi.models.Users;
import com.example.storyapi.repositories.UserRepository;
import com.example.storyapi.utils.UserRouteProtection;
import com.example.storyapi.utils.UserUpdateSetProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRouteProtection userRouteProtection;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserUpdateSetProperties userUpdateSetProperties;

    public List<UserDTO> getAllUsers(){
        Iterable<Users> allUser = userRepository.findAll();
        List<Users> list = Streamable.of(allUser).toList();
        return userConverter.listUserDto(list);
    }

//    public Users getUser(Integer id){
//        Optional<Users> user = userRepository.findById(id);
//        if (user.isEmpty()) throw new EntityNotFoundException(Users.class, "id", String.valueOf(id));
//        return user.get();
//    }

    public UserDTO getUserInfo(String email){
        Optional<Users> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new EntityNotFoundException(Users.class, "Email", String.valueOf(email));
        return userConverter.entityToDto(user.get());
    }

    public UserDTO updateUser(Integer id, Users users){
        userRouteProtection.checkUserValidation(id);
        Optional<Users> userObj =  userRepository.findById(id);
        if (userObj.isEmpty()){
            throw new EntityNotFoundException(Users.class, "id", String.valueOf(id));
        }
        Optional<Users> existEmail = userRepository.findByEmail(users.getEmail());
        if (existEmail.isPresent() && !(id.equals(existEmail.get().getId()))){
            throw new DuplicateEmailException(Users.class, " Email ", existEmail.get().getEmail());
        }

        userUpdateSetProperties.setUserProperties(userObj.get(), users);
        userRepository.save(userObj.get());
        return userConverter.entityToDto(userObj.get());
    }

    public void deleteUser(Integer id){
        userRouteProtection.checkUserValidation(id);
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        }
    }
}
