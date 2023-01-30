package com.kh.board.dto.request;

import java.util.Optional;

public record PostUpdateDto(
        Optional<String> title,
        Optional<String> content
) {
}
