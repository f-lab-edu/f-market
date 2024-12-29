package com.sorryisme.fmarket.controller;

import com.sorryisme.fmarket.annotation.LoginUserId;
import com.sorryisme.fmarket.annotation.RequireLogin;
import com.sorryisme.fmarket.common.dto.ResponseDto;
import com.sorryisme.fmarket.domain.Order;
import com.sorryisme.fmarket.dto.request.OrderSearchDto;
import com.sorryisme.fmarket.dto.response.OrderResponseDto;
import com.sorryisme.fmarket.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/orders")
    @RequireLogin
    public ResponseDto<Page<Order>> findAllOrderList(
            @RequestBody OrderSearchDto orderSearchDto,
            @PageableDefault Pageable pageable,
            @LoginUserId Long userId
    ) {
        Page<Order> searchResult = orderService.findAllOrderList(OrderSearchDto.from(orderSearchDto, pageable, userId));
        return ResponseDto.success(searchResult);
    }

    @PutMapping("orders/{id}/cancel")
    @RequireLogin
    public ResponseDto<Long> cancelOrder(@PathVariable Long id) {
        return ResponseDto.success(orderService.cancelOrder(id));
    }

    @PutMapping("orders/{id}/confirm")
    @RequireLogin
    public ResponseDto<Long> confirmOrder(@PathVariable Long id) {
        return ResponseDto.success(orderService.confirmOrder(id));
    }

    @GetMapping("/orders/{id}")
    @RequireLogin
    public ResponseDto<OrderResponseDto> findOneOrder(@PathVariable Long id) {
        return ResponseDto.success(orderService.findOneOrder(id));
    }

}
