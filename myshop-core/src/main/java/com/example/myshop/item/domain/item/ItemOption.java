package com.example.myshop.item.domain.item;

import com.example.myshop.order.dto.Option;
import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_options")
@Entity
public class ItemOption extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_option_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_option_group_id")
    private ItemOptionGroup itemOptionGroup;

    private Integer ordering;
    private String itemOptionName;

    //==생성 메서드==//
    @Builder
    public ItemOption(Integer ordering, String itemOptionName) {
        if (ordering == null) throw new InvalidParamException("ItemOption.ordering");
        if (!StringUtils.hasText(itemOptionName)) throw new InvalidParamException("ItemOption.itemOptionName");

        this.ordering = ordering;
        this.itemOptionName = itemOptionName;
    }

    //==비즈니스 로직==//
    public boolean isSatisfiedBy(Option option) {
        return Objects.equals(itemOptionName, option.getItemOptionName());
    }
}
