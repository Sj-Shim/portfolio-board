package com.kh.board.repository;

import com.kh.board.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.OrderBy;
import java.util.List;

@RepositoryRestResource
public interface ChannelRepository extends JpaRepository<Channel, String> {

    @OrderBy("subCount desc")
    List<Channel> findAll();

    @OrderBy("subCount desc")
    List<Channel> findByChannelNameContainingIgnoreCase();

}