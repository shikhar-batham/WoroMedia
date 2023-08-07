package com.boroMediaLLP.boromedia.controller;

import com.boroMediaLLP.boromedia.exception.ApiException;
import com.boroMediaLLP.boromedia.payload.*;
import com.boroMediaLLP.boromedia.security.JWTTokenHelper;
import com.boroMediaLLP.boromedia.service.UserService;
import com.boroMediaLLP.boromedia.service.VerificationTokenUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/boromedia/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private VerificationTokenUserService getVerificationToken;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private VerificationTokenUserService verificationTokenUserService;

    @PostMapping("/")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {

        UserDto savedUser = this.userService.registerNewUser(userDto);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login/user")
    public ResponseEntity<JwtAuthResponse> createUserToken(@RequestBody JwtAuthRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {

        JwtAuthResponse response;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
        calendar.getTimeZone();
        String username;

        UserDto userDtoByUsername = this.userService.getUserByUsername(request.getUsername());

        if (userDtoByUsername != null)
            username = userDtoByUsername.getEmail();
        else
            username = request.getUsername();

        this.authenticate(username, request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        UserDto userDto = this.userService.getUserByEmail(username);

        if (userDto == null)
            return new ResponseEntity<>(new JwtAuthResponse(), HttpStatus.NOT_FOUND);

        VerificationTokenUserDto verificationTokenUserDto = this.verificationTokenUserService.getVerificationToken(userDto);

        if (verificationTokenUserDto != null) {

            //if token is expired
            if (verificationTokenUserDto.getExpiration().getTime() - calendar.getTime().getTime() > 0) {

                response = new JwtAuthResponse();
                response.setToken(verificationTokenUserDto.getToken());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        String token = this.jwtTokenHelper.generateToken(userDetails.getUsername());

        this.verificationTokenUserService.createVerificationToken(userDto, token);

        response = new JwtAuthResponse();

        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid username or password");
        }
    }

    @PostMapping("/logout/user/")
    public ResponseEntity<ApiResponse> logout(@RequestParam("email") String email) {
        this.userService.logout(email);
        return new ResponseEntity<>(new ApiResponse("successfully logout!!", true), HttpStatus.OK);
    }


}
