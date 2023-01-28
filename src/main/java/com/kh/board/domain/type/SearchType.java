package com.kh.board.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum SearchType {
    ALL("전체"),
    TITLE("제목"),
    TITLEORCONTENT("제목/내용"),
    CONTENT("내용"),
    USER("작성자");

    private final String description;

}
