package com.kh.board.dto;

import com.kh.board.domain.ChannelManager;
import com.kh.board.domain.ManagerLevel;
import com.kh.board.exception.ChannelException;
import com.kh.board.exception.ChannelExceptionType;
import com.kh.board.repository.ChannelRepository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link com.kh.board.domain.ChannelManager} entity
 */
public record ChannelManagerDto(
        String slug
        , UserDto user
        , ManagerLevel managerLevel) implements Serializable {

    public static ChannelManagerDto of(
            String slug
            , UserDto user
            , ManagerLevel managerLevel) {
        return new ChannelManagerDto(slug, user, managerLevel);
    }

    public static ChannelManagerDto from(ChannelManager channelManager) {
        return new ChannelManagerDto(channelManager.getChannel().getSlug(), UserDto.from(channelManager.getUser()), channelManager.getManagerLevel());
    }

    public ChannelManager toEntity(ChannelRepository channelRepository) {
        return ChannelManager.of(channelRepository.findById(slug).orElseThrow(() -> new ChannelException(ChannelExceptionType.CHANNEL_NOT_FOUND)), this.user.toEntity(), this.managerLevel);
    }

    public static List<ChannelManagerDto> from(List<ChannelManager> channelManagers) {
        return channelManagers.stream().map(ChannelManagerDto::from).collect(Collectors.toList());
    }
}