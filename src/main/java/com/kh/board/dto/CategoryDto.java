package com.kh.board.dto;

import com.kh.board.domain.Category;
import com.kh.board.dto.ChannelDto;
import com.kh.board.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * A DTO for the {@link Category} entity
 */

public record CategoryDto(
        String channelName
        , String categoryName) implements Serializable {

    public static CategoryDto of(
            String channelName
            , String categoryName){
        return new CategoryDto(channelName, categoryName);
    }

    public static CategoryDto from(Category category) {
        return new CategoryDto(ChannelDto.from(category.getChannel()).channelName(), category.getCategoryName());
    }

    public Category toEntity(ChannelRepository channelRepository) {
        return Category.of(channelRepository.getReferenceById(this.channelName), this.categoryName);
    }
}