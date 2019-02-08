package com.mkraskiewicz.bookstore.repository;

import com.mkraskiewicz.bookstore.domain.User;
import com.mkraskiewicz.bookstore.domain.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokensRepository  extends JpaRepository<UserTokens, Long> {

    UserTokens findByForgetToken(String userToken);
    UserTokens findByUser(User user);

}
