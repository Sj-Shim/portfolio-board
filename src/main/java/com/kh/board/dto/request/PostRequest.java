package com.kh.board.dto.request;

import com.kh.board.domain.Category;
import com.kh.board.dto.CategoryDto;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.PostDto;
import com.kh.board.dto.UserDto;

public record PostRequest(
        String title,
//        Integer categoryId,
        String content

) {
   public static PostRequest of(String title, /*Integer categoryId,*/ String content) {
       return new PostRequest(title, /*categoryId,*/ content);
   }

   public PostDto toDto(UserDto userDto) {
       return PostDto.of(
               null,
               userDto,
//               this.categoryId,
               this.title,
               this.content
       );
   }
}
