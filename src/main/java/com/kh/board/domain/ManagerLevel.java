package com.kh.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ManagerLevel {
    PRIME("관리자")
    , VICE("부관리자");

    private final String description;

}
