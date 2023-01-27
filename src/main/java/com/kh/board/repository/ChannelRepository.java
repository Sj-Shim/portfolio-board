package com.kh.board.repository;

import com.kh.board.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.OrderBy;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ChannelRepository extends JpaRepository<Channel, String> {
    @OrderBy("subCount desc")
    List<Channel> findByChannelNameContainingIgnoreCase(String keyword);

    Optional<Channel> findBySlugEquals(String slug);

    @Override
    Channel getReferenceById(String s);
}