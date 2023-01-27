package com.kh.board.repository;

import com.kh.board.domain.ChannelManager;
import com.kh.board.dto.ChannelManagerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ChannelManagerRepository extends JpaRepository<ChannelManager, Integer> {

    List<ChannelManager> findByChannel_ChannelName(String channelName);
}