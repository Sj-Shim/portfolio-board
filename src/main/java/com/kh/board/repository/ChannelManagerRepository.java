package com.kh.board.repository;

import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.QChannelManager;
import com.kh.board.dto.ChannelManagerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ChannelManagerRepository extends JpaRepository<ChannelManager, Integer>
                    , QuerydslPredicateExecutor<ChannelManager>
                    , QuerydslBinderCustomizer<QChannelManager> {

    List<ChannelManager> findByChannel_Slug(String slug);

    @Override
    default void customize(QuerydslBindings bindings, QChannelManager root){
        bindings.excludeUnlistedProperties(false);
    };
}