package com.mkraskiewicz.bookstore.bootstrap;

import com.mkraskiewicz.bookstore.domain.*;
import com.mkraskiewicz.bookstore.repository.BookRepository;
import com.mkraskiewicz.bookstore.repository.RoleRepository;
import com.mkraskiewicz.bookstore.repository.UserRepository;
import com.mkraskiewicz.bookstore.repository.UserTokensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserTokensRepository userTokensRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {

            loadData();
    }

    private void loadData() {

        Role a = new Role(RoleName.ROLE_USER);
        Role c = new Role(RoleName.ROLE_ADMIN);
        roleRepository.save(a);
        roleRepository.save(c);
        User admin = new User("Majciej", "maciej", "test@gmail.com", encoder.encode("123456"));
        Set<Role> roles = new HashSet<>();
        roles.add(a);
        roles.add(c);
        admin.setRoles(roles);
        userRepository.save(admin);

        UserTokens ut = new UserTokens();
        ut.setUser(admin);
        ut.setForgetToken("ABCD");

        admin.setTokens(ut);
        userRepository.save(admin);



        User user = new User("user", "user", "mail@mail.com", encoder.encode("123456"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(a);
        user.setRoles(userRoles);
        userRepository.save(user);


        Book book = new Book();
        book.setTitle("Test Book");
        bookRepository.save(book);
//        User user1 = new User();
//        user1.setFirstName("John");
//        user1.setLastName("Adams");
//        user1.setUsername("j");
//        user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
//        user1.setEmail("JAdams@gmail.com");
//        Set<UserRole> userRoles = new HashSet<>();
//        Role role1 = new Role();
//        role1.setRoleId(1);
//        role1.setName("ROLE_USER");
//        userRoles.add(new UserRole(user1, role1));
//
//        userService.createUser(user1, userRoles);
//
//        userRoles.clear();
//
//        User user2 = new User();
//        user2.setFirstName("Admin");
//        user2.setLastName("Admin");
//        user2.setUsername("admin");
//        user2.setPassword(SecurityUtility.passwordEncoder().encode("p"));
//        user2.setEmail("Admin@gmail.com");
//        Role role2 = new Role();
//        role2.setRoleId(0);
//        role2.setName("ROLE_ADMIN");
//        userRoles.add(new UserRole(user2, role2));
//
//        userService.createUser(user2, userRoles);
    }
}
