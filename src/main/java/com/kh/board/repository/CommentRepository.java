package com.kh.board.repository;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.domain.QComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.selectFrom;

@RepositoryRestResource
public interface CommentRepository extends JpaRepository<Comment, Long>
                                , QuerydslPredicateExecutor<Comment>
                                , QuerydslBinderCustomizer<QComment> {


    Optional<Comment> findById(Long id);

    @Query("SELECT c from Comment c LEFT JOIN FETCH c.parent where c.id = :id")
    Optional<Comment> findCommentByIdWithParent(@Param("id") Long id);
    List<Comment> findByPost_Id(Long postId);
    List<Comment> findDistinctByContentContainingIgnoreCase(String keyword);
    Page<Post> findDistinctByContentContainingIgnoreCase(String keyword, Pageable pageable);

    List<Comment> findAllByPost(Post post);

    void deleteByIdAndUser_UserId(Long commentId, String userId);
    @Override
    default void customize(QuerydslBindings bindings, QComment root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.user.nickname, root.createdDate);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.user.nickname).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdDate).first(DateTimeExpression::before);
    }
}