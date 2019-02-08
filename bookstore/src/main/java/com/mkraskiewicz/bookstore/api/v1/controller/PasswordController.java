package com.mkraskiewicz.bookstore.api.v1.controller;

import com.mkraskiewicz.bookstore.api.v1.model.ResetPasswordDto;
import com.mkraskiewicz.bookstore.domain.User;
import com.mkraskiewicz.bookstore.domain.UserTokens;
import com.mkraskiewicz.bookstore.repository.UserRepository;
import com.mkraskiewicz.bookstore.repository.UserTokensRepository;
import com.mkraskiewicz.bookstore.service.EmailService;
import com.mkraskiewicz.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PasswordController.BASE_URL)
public class PasswordController {

    public static final String BASE_URL = "/api/auth/forgot-password";
    private static final String SERWER_NAME ="http://localhost:4200";
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    UserTokensRepository userTokensRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private TemplateEngine templateEngine;



    // Process form submission from forgotPassword page
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> processForgotPasswordForm(@RequestParam("email") String userEmail,
                                                  HttpServletRequest request) {


        Map<String, String> returnMap = new HashMap<>();
        Optional<User> optional = userService.findByEmail(userEmail);

        if (!optional.isPresent()) {
            returnMap.put("error", "We didn't find an account for that e-mail address.");
            return returnMap;
        } else {

            // Generate random 36-character string token for reset password
            User user = optional.get();
            UserTokens userTokens = user.getTokens();
            userTokens.setForgetToken(UUID.randomUUID().toString());


            // Save token to database
            userService.save(user);

            // Email message
//            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
//            passwordResetEmail.setFrom("mkraskiewicz.bookshop@gmail.com");
//            passwordResetEmail.setTo(user.getEmail());
//            passwordResetEmail.setSubject("Password Reset Request");
//            passwordResetEmail.setText("To reset your password, click the link below:\n" + SERWER_NAME
//                    + "/auth/resetpassword/" + user.getTokens().getForgetToken());
//
//            emailService.SendPasswordReminder(passwordResetEmail);
            Context context = new Context();
            context.setVariable("header", "Mkraskiewicz Bookshop");
            context.setVariable("title", "Password Reset Request");
            context.setVariable("description", "To reset your password, click the link below:\n"
                    + SERWER_NAME + "/auth/resetpassword/" + user.getTokens().getForgetToken());
            String body = templateEngine.process("resetPassword", context);
            emailService.SendPasswordReminder(userEmail, "Reset Password", body);

            // Add success message to view
            returnMap.put("message", "A password reset link has been sent to " + userEmail);
            return returnMap;
        }
    }


    @PostMapping("/reset")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> setNewPassword(@RequestBody ResetPasswordDto requestBody) {

        Map<String, String> returnMap = new HashMap<>();
        Optional<UserTokens> userTokensOptional = Optional
                .ofNullable(userTokensRepository.findByForgetToken(requestBody.getToken()));
        Optional<User> resetUser = Optional.empty();
        if(userTokensOptional.isPresent()){
            resetUser = Optional.ofNullable(userTokensOptional.get().getUser());
        }

        if (resetUser.isPresent()) {
            return userService.resetPassword(resetUser.get(),userTokensOptional.get(),requestBody.getPassword());
        } else {
            returnMap.put("error","Oops!  This is an invalid password reset link.");
           return returnMap;

        }
    }
}