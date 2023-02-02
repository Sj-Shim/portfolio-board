package com.kh.board.repository;

import com.kh.board.domain.Comment;
import com.kh.board.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public interface CustomCommentRepository {

    List<Comment> findAllByPostId(Long postId);
}
