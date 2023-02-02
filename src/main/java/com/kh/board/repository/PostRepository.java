package com.kh.board.repository;

import com.kh.board.domain.*;
import com.kh.board.domain.type.SearchType;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.sun.jdi.connect.Connector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Long>
                                , QuerydslPredicateExecutor<Post>
                                , QuerydslBinderCustomizer<QPost> {

    @EntityGraph(attributePaths = {"user"})
    Optional<Post> findWithUserById(Long id);



    /** 전체 검색*/
    @Query("SELECT p FROM Post p WHERE (p.title LIKE %:keyword% OR p.content LIKE %:keyword% OR p.user.nickname LIKE %:keyword%) AND p.channel.slug LIKE :slug ORDER BY p.createdDate DESC")
    Page<Post> findByAllAndSlug(@Param("keyword") String keyword, @Param("slug") String slug, Pageable pageable);

    /** 제목 검색 */
    @Query("SELECT p FROM Post p WHERE (p.title LIKE %:keyword%) AND p.channel.slug LIKE :slug ORDER BY p.createdDate DESC")
    Page<Post> findByTitleAndSlug(@Param("keyword") String keyword, @Param("slug") String slug, Pageable pageable);

    /** 제목/내용 검색 */
    @Query("SELECT p FROM Post p WHERE (p.title LIKE %:keyword% OR p.content LIKE %:keyword%) AND p.channel.slug LIKE :slug ORDER BY p.createdDate DESC")
    Page<Post> findByKeywordAndSlug(@Param("keyword") String keyword, @Param("slug") String slug, Pageable pageable);

    /** 내용 검색*/
    @Query("SELECT p FROM Post p WHERE (p.content LIKE %:keyword%) AND p.channel.slug LIKE :slug ORDER BY p.createdDate DESC")
    Page<Post> findByContentAndSlug(@Param("keyword") String keyword, @Param("slug") String slug, Pageable pageable);

    /** 작성자 검색*/
    @Query("SELECT p FROM Post p WHERE (p.user.nickname LIKE %:keyword%) AND p.channel.slug LIKE :slug ORDER BY p.createdDate DESC")
    Page<Post> findByNicknameAndSlug(@Param("keyword") String keyword, @Param("slug") String slug, Pageable pageable);


    Page<Post> findByChannelEquals(Channel channel, Pageable pageable);


    /** 채널별 게시글 전체*/
    Page<Post> findByChannel_Slug(String slug, Pageable pageable);



    void deleteByIdAndUser_UserId(Long id, String userId);


    @Override
    default void customize(QuerydslBindings bindings, QPost root) {
        bindings.excludeUnlistedProperties(true);

        bindings.including(root.title, root.content, root.user, root.createdDate, root.channel);

        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdDate).first(DateTimeExpression::before);
        bindings.bind(root.channel.channelName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.user.userId).first(StringExpression::containsIgnoreCase);

    }

}
