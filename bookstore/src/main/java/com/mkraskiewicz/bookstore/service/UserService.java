package com.mkraskiewicz.bookstore.service;

import com.mkraskiewicz.bookstore.domain.User;
import com.mkraskiewicz.bookstore.domain.UserTokens;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    public Optional<User> findByEmail(String email);
    public void save(User user);
    public Map<String, String> resetPassword(User user, UserTokens userTokens,
                                             String password);

}
