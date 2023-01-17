package com.kh.board.dto;

import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.ManagerLevel;

import java.io.Serializable;

/**
 * A DTO for the {@link com.kh.board.domain.ChannelManager} entity
 */
public record ChannelManagerDto(Integer id
        , ChannelDto channel
        , UserDto user
        , ManagerLevel managerLevel) implements Serializable {

    public static ChannelManagerDto of(Integer id
            , ChannelDto channel
            , UserDto user
            , ManagerLevel managerLevel) {
        return new ChannelManagerDto(id, channel, user, managerLevel);
    }

    public static ChannelManagerDto from(ChannelManager channelManager) {
        return new ChannelManagerDto(channelManager.getId(), ChannelDto.from(channelManager.getChannel()), UserDto.from(channelManager.getUser()), channelManager.getManagerLevel());
    }

    public ChannelManager toEntity() {
        return ChannelManager.of(this.channel.toEntity(), this.user.toEntity(), this.managerLevel);
    }
}