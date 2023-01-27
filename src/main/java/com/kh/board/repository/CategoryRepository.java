package com.kh.board.repository;

import com.kh.board.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category getCategoryByCategoryName(String categoryName);

    Category getCategoryByCategoryNameAndAndChannel_ChannelName(String categoryName, String channelName);
    List<Category> findByChannel_ChannelName(String channelName);

    boolean existsByCategoryNameAndChannel_ChannelName(String categoryName, String channelName);

}