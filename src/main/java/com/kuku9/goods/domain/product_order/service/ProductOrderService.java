package com.kuku9.goods.domain.product_order.service;

import com.kuku9.goods.domain.product_order.dto.ProductOrderResponse;
import com.kuku9.goods.domain.product_order.dto.ProductOrdersRequest;
import com.kuku9.goods.domain.product_order.entity.ProductOrder;
import com.kuku9.goods.domain.user.entity.User;

public interface ProductOrderService {

	/**
	 * 주문 생성
	 *
	 * @param user                유저
	 * @param productOrderRequest 주문 생성 요청
	 * @return 주문
	 */
	ProductOrder createOrder(User user, ProductOrdersRequest productOrderRequest);

	/**
	 * 주문 조회
	 *
	 * @param user    유저
	 * @param orderId 주문 아이디
	 * @return 주문
	 */

	ProductOrderResponse getOrder(User user, Long orderId);

	/**
	 * 주문 수정
	 *
	 * @param user    유저
	 * @param orderId 주문 아이디
	 * @return 주문
	 */
	ProductOrderResponse updateOrder(User user, Long orderId);

	/**
	 * 주문 삭제
	 *
	 * @param user    유저
	 * @param orderId 주문 아이디
	 */
	void deleteOrder(User user, Long orderId);

}
