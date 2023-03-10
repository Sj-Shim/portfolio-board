package com.kh.board.config;

import com.kh.board.repository.CustomCommentRepository;
import com.kh.board.repository.CustomCommentRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public CustomCommentRepository customCommentRepository(@Autowired JPAQueryFactory jpaQueryFactory) {
        return new CustomCommentRepositoryImpl(jpaQueryFactory);
    }
}
