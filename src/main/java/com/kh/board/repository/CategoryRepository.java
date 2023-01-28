//package com.kh.board.repository;
//
//import com.kh.board.domain.Category;
//import com.kh.board.domain.QCategory;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
//import org.springframework.data.querydsl.binding.QuerydslBindings;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//
//import java.util.List;
//import java.util.Optional;
//
////@RepositoryRestResource
//public interface CategoryRepository extends JpaRepository<Category, Integer>
//                    /*, QuerydslPredicateExecutor<Category>
//                    , QuerydslBinderCustomizer<QCategory> */{
//
//    Category getCategoryByCategoryName(String categoryName);
//
//    Optional<Category> findById(Integer id);
//    Category getCategoryByCategoryNameAndAndChannel_ChannelName(String categoryName, String channelName);
//    List<Category> findByChannel_ChannelName(String channelName);
//
//    boolean existsByCategoryNameAndChannel_ChannelName(String categoryName, String channelName);
//
//
//    @Override
//    default void customize(QuerydslBindings bindings, QCategory root){
//        bindings.excludeUnlistedProperties(false);
//    };
//}