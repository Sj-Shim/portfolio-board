package com.kh.board.repository;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.kh.board.domain.QComment.*;

@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.post.id.eq(postId))
                .orderBy(
                        comment.parent.id.asc().nullsFirst(),
                        comment.createdDate.desc()
                ).fetch();
    }
}
