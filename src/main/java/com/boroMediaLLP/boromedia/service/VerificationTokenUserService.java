package com.boroMediaLLP.boromedia.service;

import com.boroMediaLLP.boromedia.payload.UserDto;
import com.boroMediaLLP.boromedia.payload.VerificationTokenUserDto;


public interface VerificationTokenUserService {

    //create verification token
    VerificationTokenUserDto createVerificationToken(UserDto userDto, String token);

    //get verification token
    VerificationTokenUserDto getVerificationToken(UserDto userDto);

    void deleteVerificationToken(UserDto userDto);

}
