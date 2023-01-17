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
    private final EntityManager em;

    UserRepositoryTest(@Autowired UserRepository userRepository, @Autowired EntityManager em) {
        this.userRepository = userRepository;
        this.em = em;
    }
    @AfterEach
    private void after() {
        em.clear();
    }

    @Test
    public void saveUser() throws Exception {
        //Given
        User user = User.of("test1", "1111", "test@email.com", "tester1");

        //When
        User saveUser = userRepository.save(user);

        //Then
        User findUser = userRepository.findByUserId(saveUser.getUserId()).orElseThrow(() -> new RuntimeException());
        assertThat(findUser.getUserId()).isSameAs(saveUser.getUserId());
        assertThat(findUser.getUserId()).isSameAs(user.getUserId());
    }
}