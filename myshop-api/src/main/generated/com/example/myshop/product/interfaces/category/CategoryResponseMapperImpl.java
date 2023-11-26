package com.example.myshop.item.interfaces.category;

import com.example.myshop.item.domain.category.CategoryInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:19+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class CategoryResponseMapperImpl implements CategoryResponseMapper {

    @Override
    public CategoryResponse of(CategoryInfo info) {
        if ( info == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId( info.getId() );
        categoryResponse.setParentCategoryId( info.getParentCategoryId() );
        categoryResponse.setPath( info.getPath() );
        categoryResponse.setName( info.getName() );
        categoryResponse.setCreatedAt( info.getCreatedAt() );
        categoryResponse.setHasPrevious( info.isHasPrevious() );
        categoryResponse.setChildCategories( categoryInfoListToCategoryResponseList( info.getChildCategories() ) );

        return categoryResponse;
    }

    protected List<CategoryResponse> categoryInfoListToCategoryResponseList(List<CategoryInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<CategoryResponse> list1 = new ArrayList<CategoryResponse>( list.size() );
        for ( CategoryInfo categoryInfo : list ) {
            list1.add( of( categoryInfo ) );
        }

        return list1;
    }
}
