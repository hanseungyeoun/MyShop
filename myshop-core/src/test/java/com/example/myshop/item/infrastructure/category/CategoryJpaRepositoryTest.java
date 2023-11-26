package com.example.myshop.item.infrastructure.category;
import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.InMemoryDBJpaTest;
import com.example.myshop.config.TestJpaConfig;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;


@Import({TestJpaConfig.class})
@InMemoryDBJpaTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
class CategoryJpaRepositoryTest {

    @Autowired
    CategoryJpaRepository jpaRepository;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void findAll_test(){
        //given &when
        Category category = new Category( null, "top");
        jpaRepository.save(category);
        Category category1 = new Category(category.getId(), "top1");
        jpaRepository.save(category1);

        //then
        List<Category> c = jpaRepository.findAll();
        Assertions.assertThat(c).hasSize(2);
    }

}