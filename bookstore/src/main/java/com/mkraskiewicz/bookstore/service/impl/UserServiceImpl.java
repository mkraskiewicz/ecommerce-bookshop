package com.mkraskiewicz.bookstore.service.impl;

import com.mkraskiewicz.bookstore.domain.User;
import com.mkraskiewicz.bookstore.domain.UserTokens;
import com.mkraskiewicz.bookstore.repository.UserRepository;
import com.mkraskiewicz.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Map<String, String> resetPassword(User user, UserTokens userTokens, String password) {

        Map<String, String> returnMap = new HashMap<>();
        // Set new password
        user.setPassword(encoder.encode(password));

        // Set the reset token to null so it cannot be used again
        userTokens.setForgetToken(null);

        // Save user
        save(user);

        // In order to set a model attribute on a redirect, we must use
        // RedirectAttributes
        returnMap.put("message","You have successfully reset your password. You may now login.");
        return returnMap;
    }

    @Override
    public Map<String, String> activateAccount(User user, UserTokens userTokens) {

        Map<String, String> returnMap = new HashMap<>();
        // Set new password
        user.setEnabled(true);

        // Set the reset token to null so it cannot be used again
        userTokens.setForgetToken(null);

        // Save user
        save(user);

        // In order to set a model attribute on a redirect, we must use
        // RedirectAttributes
        returnMap.put("message","You have successfully activated account.");
        return returnMap;
    }

    @Override
    public Boolean ifUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean ifUserWithEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
