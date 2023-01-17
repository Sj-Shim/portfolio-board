package com.kh.board.repository;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import com.kh.board.domain.QComment;
import com.kh.board.domain.QPost;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Long>
                                , QuerydslPredicateExecutor<Post>
                                , QuerydslBinderCustomizer<QPost> {
    /** 전체 검색 */
    Page<Post> findByIdContainingIgnoreCaseOrTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrUser_UserIdContainingIgnoreCaseOrComments_ContentContainingIgnoreCase(String keyword, Pageable pageable);
    /** 제목 검색 */
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    /** 제목/내용 검색 */
    Page<Post> findByTitleContainingIgnoreCaseOrContentIsContainingIgnoreCase(String keyword, Pageable pageable);
    /** 내용 검색*/
    Page<Post> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
    /** 작성자 검색*/
    Page<Post> findByUser_UserIdContainingIgnoreCase(String keyword, Pageable pageable);
    /** 코멘트 내용 검색*/
    Page<Post> findByComments_ContentContainingIgnoreCase(String keyword, Pageable pageable);

    void deleteByIdAndUser_UserId(Long id, String userId);

}
