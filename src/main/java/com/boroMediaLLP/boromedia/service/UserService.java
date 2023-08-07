package com.boroMediaLLP.boromedia.service;

import com.boroMediaLLP.boromedia.payload.UserDto;

public interface UserService {

    //register new user
    UserDto registerNewUser(UserDto userDto);

    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);

    // logout user
    void logout(String email);
}
