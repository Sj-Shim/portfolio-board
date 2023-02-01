package com.kh.board.repository;

import com.kh.board.domain.QUser;
import com.kh.board.domain.User;
import com.kh.board.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String>
                    , QuerydslPredicateExecutor<User>
                    , QuerydslBinderCustomizer<QUser> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByNickname(String nickname);



    Optional<User> findByNicknameEquals(String nickname);

    Optional<User> findByEmailEqualsIgnoreCase(String email);

    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    @Override
    default void customize(QuerydslBindings bindings, QUser root){
        bindings.excludeUnlistedProperties(false);
    };
}
