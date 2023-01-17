package com.kh.board.dto;

import com.kh.board.domain.Category;
import com.kh.board.dto.ChannelDto;

import java.io.Serializable;

/**
 * A DTO for the {@link Category} entity
 */
public record CategoryDto(Integer id
        , ChannelDto channel
        , String categoryName) implements Serializable {

    public static CategoryDto of(Integer id
            , ChannelDto channel
            , String categoryName){
        return new CategoryDto(id, channel, categoryName);
    }

    public static CategoryDto from(Category category) {
        return new CategoryDto(category.getId(), ChannelDto.from(category.getChannel()), category.getCategoryName());
    }

    public Category toEntity() {
        return Category.of(this.channel.toEntity(), this.categoryName);
    }
}