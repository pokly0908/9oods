package com.kuku9.goods.domain.order.service;

import com.kuku9.goods.domain.order.dto.OrderResponse;
import com.kuku9.goods.domain.order.dto.OrdersRequest;
import com.kuku9.goods.domain.order.entity.Order;
import com.kuku9.goods.domain.user.entity.User;
import java.nio.file.AccessDeniedException;

public interface OrderService {

    /**
     * 주문 생성
     *
     * @param user                유저
     * @param productOrderRequest 주문 생성 요청
     * @return 주문
     */
    Order createOrder(User user, OrdersRequest productOrderRequest);

    /**
     * 주문 조회
     *
     * @param user    유저
     * @param orderId 주문 아이디
     * @return 주문
     */

    OrderResponse getOrder(User user, Long orderId) throws AccessDeniedException;

    /**
     * 주문 수정
     *
     * @param user    유저
     * @param orderId 주문 아이디
     * @return 주문
     */
    OrderResponse updateOrder(User user, Long orderId) throws AccessDeniedException;

    /**
     * 주문 삭제
     *
     * @param user    유저
     * @param orderId 주문 아이디
     */
    void deleteOrder(User user, Long orderId) throws AccessDeniedException;
}
