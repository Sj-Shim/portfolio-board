package com.kh.board.dto;

import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.ManagerLevel;
import com.kh.board.repository.ChannelRepository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link com.kh.board.domain.ChannelManager} entity
 */
public record ChannelManagerDto(
        String channelName
        , UserDto user
        , ManagerLevel managerLevel) implements Serializable {

    public static ChannelManagerDto of(
            String channelName
            , UserDto user
            , ManagerLevel managerLevel) {
        return new ChannelManagerDto(channelName, user, managerLevel);
    }

    public static ChannelManagerDto from(ChannelManager channelManager) {
        return new ChannelManagerDto(ChannelDto.from2(channelManager.getChannel()).channelName(), UserDto.from(channelManager.getUser()), channelManager.getManagerLevel());
    }

    public ChannelManager toEntity(ChannelRepository channelRepository) {
        return ChannelManager.of(channelRepository.getReferenceById(channelName), this.user.toEntity(), this.managerLevel);
    }

    public static List<ChannelManagerDto> from(List<ChannelManager> channelManagers) {
        return channelManagers.stream().map(ChannelManagerDto::from).collect(Collectors.toList());
    }
}