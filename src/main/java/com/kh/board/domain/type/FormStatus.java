package com.kh.board.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FormStatus {
    CREATE("작성", false),
    UPDATE("수정", true);
    private final String description;
    private final Boolean update;
}
