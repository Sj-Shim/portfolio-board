package com.kh.board.repository;

import com.kh.board.domain.*;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.sun.jdi.connect.Connector;
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

    /** 전체 검색*/
    Page<Post> findByTitleContainingIgnoreCaseOrContentIsContainingIgnoreCaseOrUser_NicknameContainingIgnoreCase(String keywordT, String keywordC, String keywordN, Pageable pageable);
    /** 제목 검색 */
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    /** 제목/내용 검색 */
    Page<Post> findByTitleContainingIgnoreCaseOrContentIsContainingIgnoreCase(String keyword, String keyword2, Pageable pageable);
    /** 내용 검색*/
    Page<Post> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
    /** 작성자 검색*/
    Page<Post> findByUser_NicknameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Post> findByChannelEquals(Channel channel, Pageable pageable);
//    /** 코멘트 내용 검색*/
//    Page<Post> findByComments_ContentContainingIgnoreCase(String keyword, Pageable pageable);

    /** 채널별 게시글 전체*/
    Page<Post> findByChannel_ChannelName(String channelName, Pageable pageable);

    /** 채널별 카테고리 필터*/
//    Page<Post> findByChannel_ChannelNameAndCategory_CategoryName(String channelName, String categoryName, Pageable pageable);

    void deleteByIdAndUser_UserId(Long id, String userId);


    @Override
    default void customize(QuerydslBindings bindings, QPost root) {
        bindings.excludeUnlistedProperties(true);

        bindings.including(root.title, root.content, root.comments, root.user, root.createdDate, root.channel/*, root. category*/);

        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdDate).first(DateTimeExpression::before);
        bindings.bind(root.channel.channelName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.user.userId).first(StringExpression::containsIgnoreCase);

    }
}
