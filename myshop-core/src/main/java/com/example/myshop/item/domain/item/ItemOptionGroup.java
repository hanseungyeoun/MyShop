package com.example.myshop.item.domain.item;

import com.example.myshop.order.dto.Option;
import com.example.myshop.order.dto.OptionGroup;
import com.example.myshop.exception.InvalidParamException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_option_groups")
@Entity
public class ItemOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_option_group_id")
    private Long id;

    private Integer ordering;
    private String itemOptionGroupName;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemOptionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordering asc")
    private final List<ItemOption> itemOptions = new ArrayList<>();

    //==생성 메서드==//
    @Builder
    public ItemOptionGroup(Integer ordering, String itemOptionGroupName, List<ItemOption> itemOptions) {
        if (ordering == null) throw new InvalidParamException("ItemOptionGroup.ordering");
        if (StringUtils.isEmpty(itemOptionGroupName))
            throw new InvalidParamException("ItemOptionGroup.itemOptionGroupName");

        this.ordering = ordering;
        this.itemOptionGroupName = itemOptionGroupName;
        addOptionGroup(itemOptions);
    }

    public void addOptionGroup(List<ItemOption> itemOptionList) {
        for (ItemOption itemOption : itemOptionList) {
            addOptionGroup(itemOption);
        }
    }

    public void addOptionGroup(ItemOption itemOption) {
        itemOptions.add(itemOption);
        itemOption.setItemOptionGroup(this);
    }

    public void clearOption() {
        itemOptions.clear();
    }

    //==비즈니스 로직==//

    public boolean isSatisfiedBy(OptionGroup optionGroup) {
        return isSatisfied(optionGroup.getItemOptionGroupName(), satisfied(optionGroup.getOptions()));
    }

    private boolean isSatisfied(String groupName, List<Option> satisfied) {
        if (!itemOptionGroupName.equals(groupName)) {
            return false;
        }

      return !satisfied.isEmpty();
    }

    private List<Option> satisfied(List<Option> options) {
        return itemOptions
                .stream()
                .flatMap(spec -> options.stream().filter(spec::isSatisfiedBy))
                .toList();
    }
}
