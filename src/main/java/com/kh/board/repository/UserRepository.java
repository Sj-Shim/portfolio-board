package com.kh.board.repository;

import com.kh.board.domain.User;
import com.kh.board.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserId(String userId);


    Optional<User> findByNicknameEquals(String nickname);

    Optional<User> findByEmailEqualsIgnoreCase(String email);

    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
