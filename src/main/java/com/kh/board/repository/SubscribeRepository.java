package com.kh.board.repository;

import com.kh.board.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Query(value = "SELECT count(channel.channelName) FROM Subscribe where channel.channelName like concat('%', #{keyword}, '%')")
    Integer countByChannel_ChannelName(@Param(value="keyword") String keyword);



}