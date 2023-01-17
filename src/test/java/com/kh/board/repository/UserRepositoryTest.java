package com.kh.board.repository;

import com.kh.board.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    private final UserRepository userRepository;

    UserRepositoryTest(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}