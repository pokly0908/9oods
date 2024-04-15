package com.kuku9.goods.domain.order.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.NOT_EQUAL_USER_ID;

import com.kuku9.goods.domain.issued_coupon.entity.IssuedCoupon;
import com.kuku9.goods.domain.issued_coupon.repository.IssuedCouponRepository;
import com.kuku9.goods.domain.order.dto.OrderResponse;
import com.kuku9.goods.domain.order.dto.OrdersRequest;
import com.kuku9.goods.domain.order.entity.Order;
import com.kuku9.goods.domain.order.repository.OrderRepository;
import com.kuku9.goods.domain.order_product.dto.OrderProductRequest;
import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.order_product.repository.OrderProductRepository;
import com.kuku9.goods.domain.product.dto.ProductResponse;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.event.OrderEvent;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final RedissonClient redissonClient;
	private final OrderRepository orderRepository;
	private final OrderProductRepository orderProductRepository;
	private final ProductRepository productRepository;
	private final IssuedCouponRepository issuedCouponRepository;
	private final ApplicationEventPublisher publisher;

	@Override
	public Order createOrder(User user, OrdersRequest OrderRequest) {
		//분산락 생성
		RLock lock = redissonClient.getLock("orderCreationLock");
		try {
			lock.lock();
			//상품 및 재고확인
			List<Long> productIds = OrderRequest.getProducts().stream()
				.map(OrderProductRequest::getProductId)
				.collect(Collectors.toList());

			List<Product> products = productRepository.findAllById(productIds);

			if (products.size() != productIds.size()) {
				throw new IllegalArgumentException("존재하지 않는 상품이 포함되어 있습니다.");
			}

			for (OrderProductRequest productRequest : OrderRequest.getProducts()) {
				Product product = products.stream()
					.filter(p -> p.getId().equals(productRequest.getProductId()))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

				if (product.getQuantity() < productRequest.getQuantity()) {
					throw new IllegalArgumentException("재고가 부족합니다.");
				}
			}

			//쿠폰 사용
			Long issuedCouponId = OrderRequest.getIssuedCouponId();

			if (issuedCouponId != null) {
				IssuedCoupon issuedCoupon = issuedCouponRepository.findById(
						issuedCouponId)
					.orElseThrow(() -> new IllegalArgumentException("해당 쿠폰은 존재하지 않습니다."));

				publisher.publishEvent(new OrderEvent(issuedCoupon.getId()));
			}

			//저장
			Order order = orderRepository.save(
				new Order(user, OrderRequest.getAddress()));
			for (int i = 0; i < products.size(); i++) {
				products.get(i).updateQuantity(OrderRequest.getProducts().get(i).getQuantity());
				int price =
					products.get(i).getPrice() * OrderRequest.getProducts().get(i).getQuantity();
				order.addTotalPrice(price);
				orderProductRepository.save(new OrderProduct(order, products.get(i),
					OrderRequest.getProducts().get(i).getQuantity()));
			}
			if (issuedCouponId != null) {
				int discount = (int) (order.getTotalPrice() * 0.1);
				order.discount(discount);
			}

			orderRepository.save(order);

			return order;
		} finally {
			lock.unlock();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public OrderResponse getOrder(User user, Long orderId) throws AccessDeniedException {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
		if (!order.getUser().getId().equals(user.getId())) {
			throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
		}
		return getProductOrderResponse(orderId, order);
	}

	@Override
	@Transactional
	public OrderResponse updateOrder(User user, Long orderId) throws AccessDeniedException {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
		if (!order.getUser().getId().equals(user.getId()) || order.getStatus().equals("결제 취소")) {
			throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
		}
		List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(orderId);
		for (OrderProduct orderProduct : orderProducts) {
			orderProduct.getProduct().updateQuantity(-orderProduct.getQuantity());
		}
		order.updateStatus("결제 취소");
		return getProductOrderResponse(orderId, order);
	}

	@Override
	@Transactional
	public void deleteOrder(User user, Long orderId) throws AccessDeniedException {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
		if (!order.getUser().getId().equals(user.getId())) {
			throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
		}
		orderRepository.delete(order);
	}

	private OrderResponse getProductOrderResponse(Long orderId, Order order) {
		List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(orderId);
		List<ProductResponse> products = new ArrayList<>();
		for (OrderProduct orderProduct : orderProducts) {
			products.add(new ProductResponse(orderProduct.getProduct()));
		}
		return new OrderResponse(order, products);
	}


}
