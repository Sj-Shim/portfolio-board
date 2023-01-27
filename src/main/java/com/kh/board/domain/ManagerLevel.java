package com.kh.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ManagerLevel {
    PRIME("관리자")
    , VICE("부관리자");

    private final String name;

    public static ManagerLevel find(String s) {
        if (s.equals(ManagerLevel.PRIME.getName())){
            return ManagerLevel.PRIME;
        } else if (s.equals(ManagerLevel.VICE.getName())) {
            return ManagerLevel.VICE;
        } else {
            return null;
        }
    }
}
