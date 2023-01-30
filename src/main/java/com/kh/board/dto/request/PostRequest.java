package com.kh.board.dto.request;

import com.kh.board.domain.Post;
import com.kh.board.dto.ChannelDto;
import com.kh.board.dto.PostDto;
import com.kh.board.dto.UserDto;

import javax.validation.constraints.NotBlank;

public record PostRequest(
        @NotBlank(message = "제목 입력") String title,
        @NotBlank(message = "내용 입력") String content

) {
   public static PostRequest of(String title, String content) {
       return new PostRequest(title, content);
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

   public Post toEntity(){
       return Post.builder().title(title).content(content).build();
   }
}
