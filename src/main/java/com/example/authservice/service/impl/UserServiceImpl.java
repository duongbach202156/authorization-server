package com.example.authservice.service.impl;

//import com.example.authservice.mapper.UserMapper;
import com.example.authservice.exception.UserAlreadyExistException;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.model.entity.Role;
//import com.example.authservice.model.entity.User;
import com.example.authservice.model.entity.User;
import com.example.authservice.model.form.AdminRegistrationForm;
//import com.example.authservice.repository.jpa.UserRepository;
import com.example.authservice.repository.jpa.UserRepository;
import com.example.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createAdmin(AdminRegistrationForm adminRegistrationForm) {
        if (emailExists(adminRegistrationForm.getEmail())) {
            throw new UserAlreadyExistException("Email already exist: " + adminRegistrationForm.getEmail());
        }
        User user = userMapper.toAdminEntity(adminRegistrationForm);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
