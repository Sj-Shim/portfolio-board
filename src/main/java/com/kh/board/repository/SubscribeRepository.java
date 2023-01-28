package com.kh.board.repository;

import com.kh.board.domain.QSubscribe;
import com.kh.board.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface SubscribeRepository extends JpaRepository<Subscribe, Long>
                    , QuerydslPredicateExecutor<Subscribe>
                    , QuerydslBinderCustomizer<QSubscribe> {


    List<Subscribe> findByUser_UserId(String userId);
    List<Subscribe> findByChannel_ChannelName(String channelName);

    Optional<Subscribe> findByUser_UserIdAndChannel_ChannelName(String userId, String channelName);

    boolean existsByChannel_ChannelNameAndUser_UserId(String channelName, String userId);

    @Override
    default void customize(QuerydslBindings bindings, QSubscribe root){
        bindings.excludeUnlistedProperties(false);
    };
}