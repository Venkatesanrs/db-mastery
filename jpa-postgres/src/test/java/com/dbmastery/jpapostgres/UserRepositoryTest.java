package com.dbmastery.jpapostgres;

import com.dbmastery.jpapostgres.entity.UserEntity;
import com.dbmastery.jpapostgres.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldSaveAndFindByEmail() {
        UserEntity user = UserEntity.builder()
                .name("Ravi")
                .email("ravi@dbmastery.com")
                .role(UserEntity.UserRole.ADMIN)
                .build();

        userRepository.save(user);

        assertThat(userRepository.findByEmail("ravi@dbmastery.com")).isPresent();
    }
}
