package com.mkraskiewicz.bookstore.service.impl;

import com.mkraskiewicz.bookstore.domain.User;
import com.mkraskiewicz.bookstore.domain.UserTokens;
import com.mkraskiewicz.bookstore.repository.UserTokensRepository;
import com.mkraskiewicz.bookstore.service.UserTokensService;

public class UserTokensServiceImpl implements UserTokensService {

    private final UserTokensRepository userTokensRepository;

    public UserTokensServiceImpl(UserTokensRepository userTokensRepository) {
        this.userTokensRepository = userTokensRepository;
    }

    @Override
    public UserTokens findByForgetToken(String userToken) {
        return userTokensRepository.findByForgetToken(userToken);
    }

    @Override
    public UserTokens findByUser(User user) {
        return userTokensRepository.findByUser(user);
    }
}
