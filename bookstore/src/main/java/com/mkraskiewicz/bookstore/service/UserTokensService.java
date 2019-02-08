package com.mkraskiewicz.bookstore.service;

import com.mkraskiewicz.bookstore.domain.User;
import com.mkraskiewicz.bookstore.domain.UserTokens;

public interface UserTokensService {

    UserTokens findByForgetToken(String userToken);
    UserTokens findByUser(User user);
}
