package com.kh.board.repository;

import com.kh.board.domain.ChannelManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChannelManagerRepository extends JpaRepository<ChannelManager, Integer> {

}