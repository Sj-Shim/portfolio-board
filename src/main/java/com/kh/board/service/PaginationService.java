package com.kh.board.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 10;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {

        int startNumber = Math.max(0, currentPageNumber - (BAR_LENGTH/2));

        int endPage = Math.min(startNumber + BAR_LENGTH, totalPages);

        return IntStream.range(startNumber, endPage).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }

}
