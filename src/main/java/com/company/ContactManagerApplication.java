package com.company;

import com.company.config.security.SessionUser;
import com.company.dtos.authuser.TokenRequest;
import com.company.entities.AuthUser;
import com.company.entities.Group;
import com.company.enums.Role;
import com.company.enums.Status;
import com.company.repositories.AuthUserRepository;
import com.company.repositories.GroupRepository;
import com.company.services.AuthUserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@EnableWebMvc
@OpenAPIDefinition
@EnableJpaAuditing
@SpringBootApplication
public class ContactManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactManagerApplication.class, args);
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public AuditorAware<Integer> auditorAware(SessionUser sessionUser) {
        return () -> Optional.of(sessionUser.id());
    }

//    @Bean
    public CommandLineRunner commandLineRunner(AuthUserRepository repository,
                                               PasswordEncoder encoder,
                                               GroupRepository groupRepository,
                                               AuthUserService authUserService) {
        return args -> {
            repository.save(AuthUser.builder()
                    .email("admin@gmail.com")
                    .password(encoder.encode("admin"))
                    .role(Role.ADMIN)
                    .fullName("Admin")
                    .status(Status.ACTIVE)
                    .build()
            );
            System.out.println(authUserService.generateToken(new TokenRequest("admin@gmail.com", "admin")));

            List<Group> groupList = new ArrayList<>();
            groupList.add(Group.builder().name("Family").build());
            groupList.add(Group.builder().name("Friends").build());
            groupList.add(Group.builder().name("Work").build());
            groupList.add(Group.builder().name("Others").build());
            groupRepository.saveAll(groupList);
        };
    }
}
