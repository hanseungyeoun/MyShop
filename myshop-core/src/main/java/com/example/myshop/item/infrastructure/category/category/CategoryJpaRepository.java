package com.example.myshop.item.infrastructure.category.category;

import com.example.myshop.item.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query("delete from Category c where c.path like concat( c.path, '%')")
    void deleteWithChild();


    @Query("select c from Category c join ItemCategory pc on c.id = pc.categoryId where pc.item.id =:itemId")
    List<Category> findByItemId(@Param("itemId")Long itemId);

    @Query("select c from Category c where c.path != :parentPath and c.path like concat(:parentPath, '%')")
    List<Category> getCategoryParentPath(@Param("parentPath")String parentPath);
}