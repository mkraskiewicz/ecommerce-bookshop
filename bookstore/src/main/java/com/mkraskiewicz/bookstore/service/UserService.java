package com.mkraskiewicz.bookstore.service;

import com.mkraskiewicz.bookstore.domain.User;
import com.mkraskiewicz.bookstore.domain.UserTokens;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    void save(User user);
    Map<String, String> resetPassword(User user, UserTokens userTokens,
                                             String password);
    Map<String, String> activateAccount(User user, UserTokens userTokens);
    Boolean ifUserExists(String username);
    Boolean ifUserWithEmailExists(String email);

}
