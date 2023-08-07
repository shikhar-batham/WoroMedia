package com.boroMediaLLP.boromedia;

import com.boroMediaLLP.boromedia.entity.Roles;
import com.boroMediaLLP.boromedia.repo.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BoromediaApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;


    public static void main(String[] args) {
        SpringApplication.run(BoromediaApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Override
    public void run(String... args) {

        //table role
        Roles role = new Roles();
        role.setId(500);
        role.setName("USER");

        List<Roles> roles = List.of(role, role);

        List<Roles> result = this.roleRepo.saveAll(roles);

        result.forEach(r -> {
            System.out.println(r.getName());
        });

    }
}
