//package com.kh.board.service;
//
//import com.kh.board.domain.Category;
//import com.kh.board.dto.CategoryDto;
//import com.kh.board.repository.CategoryRepository;
//import com.kh.board.repository.ChannelRepository;
//import com.kh.board.repository.PostRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
////@Service
//@Slf4j
//@Transactional
//@RequiredArgsConstructor
//public class CategoryService {
//
////    @Autowired private CategoryRepository categoryRepository;
//    @Autowired private ChannelRepository channelRepository;
//    @Autowired private PostRepository postRepository;
//
//    public List<CategoryDto> getCategoryListByChannel(String channelName) {
//        return categoryRepository.findByChannel_ChannelName(channelName).stream().map(CategoryDto::from).collect(Collectors.toList());
//    }
//
//    public void addCategory(String channelName, String categoryName) {
//        if(!categoryRepository.existsByCategoryNameAndChannel_ChannelName(categoryName, channelName)){
//            Category category = Category.of(channelRepository.getReferenceById(channelName), categoryName);
//            categoryRepository.save(category);
//        }
//    }
//    public void updateCategory(String channelName, String categoryName, String newName) {
//        if(categoryRepository.existsByCategoryNameAndChannel_ChannelName(categoryName, channelName)){
//            Category category = categoryRepository.getCategoryByCategoryNameAndAndChannel_ChannelName(categoryName, channelName);
//            category.setCategoryName(newName);
//        } else {
//            addCategory(channelName, newName);
//        }
//    }
//
//    public void deleteCategory(String channelName, String categoryName) {
//        if(categoryRepository.existsByCategoryNameAndChannel_ChannelName(categoryName, channelName)){
//            Category category = categoryRepository.getCategoryByCategoryNameAndAndChannel_ChannelName(categoryName, channelName);
//            categoryRepository.delete(category);
//        } else {
//            log.warn("해당하는 카테고리가 없습니다.");
//        }
//    }
//
//
//}
