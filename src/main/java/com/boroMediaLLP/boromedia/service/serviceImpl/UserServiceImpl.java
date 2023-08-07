package com.boroMediaLLP.boromedia.service.serviceImpl;

import com.boroMediaLLP.boromedia.entity.Users;
import com.boroMediaLLP.boromedia.exception.ResourceNotFoundException;
import com.boroMediaLLP.boromedia.payload.UserDto;
import com.boroMediaLLP.boromedia.repo.UserRepo;
import com.boroMediaLLP.boromedia.service.UserService;
import com.boroMediaLLP.boromedia.service.VerificationTokenUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenUserService verificationTokenUserService;

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        Users user = this.modelMapper.map(userDto, Users.class);

        Optional<Users> fetchedUser = this.userRepo.findByEmail(userDto.getEmail());
        if (fetchedUser.isPresent())
            return null;
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto getUserByUsername(String username) {

        Users fetchedUser = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("", "", 0));

        if (fetchedUser == null)
            return null;

        return this.modelMapper.map(fetchedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        Users fetchedUser = this.userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Seeker not found with email ", email, -1));


        return this.modelMapper.map(fetchedUser, UserDto.class);
    }

    @Override
    public void logout(String email) {

        Users users = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", "user_id" + email, -1));

        UserDto userDto = this.modelMapper.map(users, UserDto.class);

        this.verificationTokenUserService.deleteVerificationToken(userDto);

    }
}
