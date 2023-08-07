package com.boroMediaLLP.boromedia.service.serviceImpl;

import com.boroMediaLLP.boromedia.entity.Users;
import com.boroMediaLLP.boromedia.entity.VerificationTokenUsers;
import com.boroMediaLLP.boromedia.payload.UserDto;
import com.boroMediaLLP.boromedia.payload.VerificationTokenUserDto;
import com.boroMediaLLP.boromedia.repo.VerificationTokenUserRepo;
import com.boroMediaLLP.boromedia.service.VerificationTokenUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class VerificationTokenUserServiceImpl implements VerificationTokenUserService {

    @Autowired
    private VerificationTokenUserRepo verificationTokenUserRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public VerificationTokenUserDto createVerificationToken(UserDto userDto, String token) {

        VerificationTokenUserDto verificationTokenUserDto = new VerificationTokenUserDto();
        verificationTokenUserDto.setUser(userDto);
        verificationTokenUserDto.setToken(token);

        Users user = this.modelMapper.map(userDto, Users.class);

        VerificationTokenUsers verificationTokenUser = this.modelMapper.map(verificationTokenUserDto, VerificationTokenUsers.class);
        verificationTokenUser.setUsers(user);


        VerificationTokenUsers verificationTokenUserCheck = this.verificationTokenUserRepo.findByUsers(user);

        VerificationTokenUserDto verificationTokenUserDtoCheck = null;

        if (verificationTokenUserCheck != null)
            verificationTokenUserDtoCheck = this.modelMapper.map(verificationTokenUserCheck, VerificationTokenUserDto.class);

        Calendar calendar = Calendar.getInstance();

        this.verificationTokenUserRepo.save(verificationTokenUser);

        return verificationTokenUserDto;
    }

    @Override
    public VerificationTokenUserDto getVerificationToken(UserDto userDto) {

        Users users = this.modelMapper.map(userDto, Users.class);

        VerificationTokenUsers verificationTokenUser = this.verificationTokenUserRepo.findByUsers(users);

        if (verificationTokenUser == null)
            return null;

        return this.modelMapper.map(verificationTokenUser, VerificationTokenUserDto.class);
    }

    @Override
    public void deleteVerificationToken(UserDto userDto) {

        Users users = this.modelMapper.map(userDto, Users.class);

        VerificationTokenUsers verificationTokenUsers = this.verificationTokenUserRepo.findByUsers(users);

        verificationTokenUserRepo.delete(verificationTokenUsers);

    }

}
