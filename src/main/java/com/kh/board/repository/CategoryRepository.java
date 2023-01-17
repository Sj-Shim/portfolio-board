package com.kh.board.repository;

import com.kh.board.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}