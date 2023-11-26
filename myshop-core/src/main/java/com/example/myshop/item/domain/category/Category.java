package com.example.myshop.item.domain.category;

import com.example.myshop.constant.Constant;
import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
@Getter
@Entity
@NoArgsConstructor
@Table(name = "categorys")
public class Category extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private Long parentCategoryId;

    private String name;

    @Column(unique = true)
    private String path;

    private Long adminId;

    //==생성 메서드==//
    @Builder
    public Category(Long parentCategoryId, String name) {
        if (!StringUtils.hasText(name)) throw new InvalidParamException("Category.name");
      //  if (adminId == null) throw new InvalidParamException("Category.adminId");

        this.parentCategoryId = parentCategoryId;
        this.name = name;
    }

    //==비즈니스 로직==//
    public void setPath(String parentPath, Long id) {
        if (StringUtils.endsWithIgnoreCase(parentPath, Constant.CATEGORY_PATH_DELIMITER)) {
            this.path = parentPath + id;
        } else {
            this.path = parentPath + Constant.CATEGORY_PATH_DELIMITER + id;
        }
    }

     public void changeCategoryName(String newName) {
        this.name = newName;
    }
}
