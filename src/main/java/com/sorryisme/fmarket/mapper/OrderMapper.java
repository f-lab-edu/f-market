package com.sorryisme.fmarket.mapper;

import com.sorryisme.fmarket.domain.Order;
import com.sorryisme.fmarket.domain.Product;
import com.sorryisme.fmarket.domain.ProductReview;
import com.sorryisme.fmarket.dto.request.OrderSearchDto;
import com.sorryisme.fmarket.dto.request.ProductSearchDto;
import com.sorryisme.fmarket.dto.response.OrderResponseDto;
import com.sorryisme.fmarket.dto.response.ProductResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> findAllOrderList(OrderSearchDto orderSearchDto);
    int countOrderList(OrderSearchDto orderSearchDto);
    boolean isExistOrderById(Long orderId);
    int updateOrder(Long orderId, String status);
    OrderResponseDto findOrderById(Long orderId);

}
