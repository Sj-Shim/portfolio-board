package com.kh.board.repository;

import com.kh.board.domain.Channel;
import com.kh.board.domain.QChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.OrderBy;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ChannelRepository extends JpaRepository<Channel, String>
        ,QuerydslPredicateExecutor<Channel>
        ,QuerydslBinderCustomizer<QChannel> {

    List<Channel> findByChannelNameContainingIgnoreCase(String keyword);

    List<Channel> findAllByOrderBySubCountDesc();

    Optional<Channel> findByChannelNameEquals(String channelName);
    Optional<Channel> findBySlug(String slug);

    Optional<Channel> findBySlugEquals(String slug);
    Optional<Channel> findBySlugContainingIgnoreCase(String slug);

    boolean existsByChannelName(String channelName);


    @Override
    Channel getReferenceById(String s);



    @Override
    default void customize(QuerydslBindings bindings, QChannel root){
      bindings.excludeUnlistedProperties(false);
    };
}