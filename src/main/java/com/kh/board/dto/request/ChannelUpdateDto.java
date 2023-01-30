package com.kh.board.dto.request;

import java.util.Optional;

public record ChannelUpdateDto(
        Optional<String> description
) {
}
