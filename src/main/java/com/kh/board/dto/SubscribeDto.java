package com.kh.board.dto;

import com.kh.board.domain.Subscribe;
import com.kh.board.exception.ChannelException;
import com.kh.board.exception.ChannelExceptionType;
import com.kh.board.exception.UserException;
import com.kh.board.exception.UserExceptionType;
import com.kh.board.repository.ChannelRepository;
import com.kh.board.repository.UserRepository;
import lombok.Setter;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.kh.board.domain.Subscribe} entity
 */

public record SubscribeDto(Long id
        , ChannelDto channelDto
        , UserDto userDto) implements Serializable {
    public static SubscribeDto of(Long id
            , ChannelDto channelDto
            , UserDto userDto) {
        return new SubscribeDto(id, channelDto, userDto);
    }


    public static SubscribeDto from(Subscribe subscribe) {
        return new SubscribeDto(subscribe.getId(), ChannelDto.from(subscribe.getChannel()), UserDto.from(subscribe.getUser()));
    }


    public Subscribe toEntity(ChannelRepository channelRepository, UserRepository userRepository) {
        return Subscribe.of(channelRepository.findById(channelDto.slug()).orElseThrow(() -> new ChannelException(ChannelExceptionType.CHANNEL_NOT_FOUND)), userRepository.findById(userDto.userId()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER)));
    }
}