package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.entity.User;
import org.acme.exception.UserDontFoundException;
import org.acme.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public void createUser(User user){
        userRepository.persist(user);
    }

    public List<User> findAllUsers(Integer page, Integer pageSize){
        return userRepository.findAll().page(page, pageSize).list();
    }

    public User findUserById(UUID userId){
        var user = userRepository.findByIdOptional(userId).orElseThrow(UserDontFoundException::new);
        return user;
    }

    @Transactional
    public void updateUserById(UUID userId, User user){
        var userSaved = findUserById(userId);
        if(user.getEmail() != null || !user.getEmail().isBlank()){
            userSaved.setEmail(user.getEmail());
        }
        if(user.getSenha() != null || !user.getEmail().isBlank()){
            userSaved.setEmail(user.getEmail());
        }
        userRepository.persist(userSaved);
    }

    @Transactional
    public void deleteUserById(UUID userId){
        var user = findUserById(userId);
        userRepository.delete(user);
    }
}
