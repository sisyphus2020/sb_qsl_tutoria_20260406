package com.qsl.qsl_tutorial.base;

import com.qsl.qsl_tutorial.boundedcontext.user.entity.SiteUser;
import com.qsl.qsl_tutorial.boundedcontext.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestInitData {

  @Bean
  CommandLineRunner inti(UserRepository userRepository) {

    return args -> {
      List<SiteUser> users = new ArrayList<>();

      for (int i = 1; i < 101; i++) {
        SiteUser user = SiteUser.builder()
            .username("user" + i)
            .password("{noop}1234")
            .email("user" + i + "@test.com")
            .build();
        users.add(user);
      }

      userRepository.saveAll(users);
    };
  }

}
