package com.mkraskiewicz.bookstore.api.v1.controller;

import com.mkraskiewicz.bookstore.domain.Role;
import com.mkraskiewicz.bookstore.domain.RoleName;
import com.mkraskiewicz.bookstore.domain.User;
import com.mkraskiewicz.bookstore.security.jwt.JwtProvider;
import com.mkraskiewicz.bookstore.security.message.request.LoginForm;
import com.mkraskiewicz.bookstore.security.message.request.SignUpForm;
import com.mkraskiewicz.bookstore.security.message.response.JwtResponse;
import com.mkraskiewicz.bookstore.security.message.response.ResponseMessage;
import com.mkraskiewicz.bookstore.service.RoleService;
import com.mkraskiewicz.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {


	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final RoleService roleService;
	private final PasswordEncoder encoder;
	private final JwtProvider jwtProvider;
	private final PasswordController passwordController;

	public AuthRestAPIs(AuthenticationManager authenticationManager, UserService userService, RoleService roleService,
						PasswordEncoder encoder, JwtProvider jwtProvider, PasswordController passwordController) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.roleService = roleService;
		this.encoder = encoder;
		this.jwtProvider = jwtProvider;
		this.passwordController = passwordController;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userService.ifUserExists(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userService.ifUserWithEmailExists(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
				case "admin":
					Role adminRole = roleService.getRole(RoleName.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleService.getRole(RoleName.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
					roles.add(userRole);
			}
		});

		user.setRoles(roles);
		userService.save(user);
		passwordController.processActivation(user.getEmail());
		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
}