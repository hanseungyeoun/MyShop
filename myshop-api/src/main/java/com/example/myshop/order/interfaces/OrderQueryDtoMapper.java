package com.example.myshop.order.interfaces;

import com.example.myshop.order.domain.OrderInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderQueryDtoMapper {
    @Mapping(source = "orderId", target = "id", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "orderedAt", target = "orderedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    OrderResponseDto.Main of(OrderInfo.Main mainResult);

}
