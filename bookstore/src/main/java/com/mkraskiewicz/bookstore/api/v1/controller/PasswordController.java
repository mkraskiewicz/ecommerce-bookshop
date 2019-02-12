package com.mkraskiewicz.bookstore.api.v1.controller;

import com.mkraskiewicz.bookstore.api.v1.model.ResetPasswordDto;
import com.mkraskiewicz.bookstore.domain.User;
import com.mkraskiewicz.bookstore.domain.UserTokens;
import com.mkraskiewicz.bookstore.repository.UserRepository;
import com.mkraskiewicz.bookstore.repository.UserTokensRepository;
import com.mkraskiewicz.bookstore.service.EmailService;
import com.mkraskiewicz.bookstore.service.UserService;
import com.mkraskiewicz.bookstore.service.UserTokensService;
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

    public static final String BASE_URL = "/api/auth/account";
    private static final String SERWER_NAME ="http://localhost:4200";

    private final UserService userService;
    private final  EmailService emailService;
    private final UserTokensService userTokensService;
    private final PasswordEncoder encoder;
    private final  TemplateEngine templateEngine;

    public PasswordController(UserService userService, EmailService emailService, UserTokensService userTokensService,
                              PasswordEncoder encoder, TemplateEngine templateEngine) {
        this.userService = userService;
        this.emailService = emailService;
        this.userTokensService = userTokensService;
        this.encoder = encoder;
        this.templateEngine = templateEngine;
    }

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

            Context context = new Context();
            context.setVariable("header", "Mkraskiewicz Bookshop");
            context.setVariable("title", "Account Activation");
            context.setVariable("description", "To activate your account, click the link below:\n"
                    + SERWER_NAME + "/auth/activateaccount/" + user.getTokens().getForgetToken());
            String body = templateEngine.process("resetPassword", context);
            emailService.SendPasswordReminder(userEmail, "Account Activation", body);

            // Add success message to view
            returnMap.put("message", "An activation link has been sent to " + userEmail);
            return returnMap;
        }
    }


    @PostMapping("/resendemail")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> processActivation(@RequestParam("email") String userEmail) {

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
                .ofNullable(userTokensService.findByForgetToken(requestBody.getToken()));
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

    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> activateUser(@RequestParam String token) {

        Map<String, String> returnMap = new HashMap<>();
        Optional<UserTokens> userTokensOptional = Optional
                .ofNullable(userTokensService.findByForgetToken(token));
        Optional<User> resetUser = Optional.empty();
        if(userTokensOptional.isPresent()){
            resetUser = Optional.ofNullable(userTokensOptional.get().getUser());
        }

        if (resetUser.isPresent()) {
            return userService.activateAccount(resetUser.get(),userTokensOptional.get());
        } else {
            returnMap.put("error","Oops!  This is an invalid password reset link.");
            return returnMap;

        }
    }
}