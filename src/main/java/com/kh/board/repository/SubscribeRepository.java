package com.kh.board.repository;

import com.kh.board.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {


    List<Subscribe> findByUser_UserId(String userId);
    List<Subscribe> findByChannel_ChannelName(String channelName);

    Optional<Subscribe> findByUser_UserIdAndChannel_ChannelName(String userId, String channelName);
}