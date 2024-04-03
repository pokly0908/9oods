package com.kuku9.goods.domain.order.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.NOT_EQUAL_USER_ID;

import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.order_product.repository.OrderProductRepository;
import com.kuku9.goods.domain.product.dto.ProductResponse;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.order.dto.OrderResponse;
import com.kuku9.goods.domain.order.dto.OrdersRequest;
import com.kuku9.goods.domain.order.entity.Order;
import com.kuku9.goods.domain.order.repository.OrderRepository;
import com.kuku9.goods.domain.user.entity.User;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderProductRepository orderProductRepository;
	private final ProductRepository productRepository;

    @Override
    public Order createOrder(User user, OrdersRequest productOrderRequest) {
        Order order = orderRepository.save(
            new Order(user, productOrderRequest.getAddress()));
        for (int i = 0; i < productOrderRequest.getProducts().size(); i++) {
            //N+1 문제?_?
            Product product = productRepository.findById(
                    productOrderRequest.getProducts().get(i).getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
            if(product.getQuantity() < productOrderRequest.getProducts().get(i).getQuantity()){
                orderRepository.delete(order);
                throw new IllegalArgumentException("재고가 부족합니다.");
            }
            product.updateQuantity(productOrderRequest.getProducts().get(i).getQuantity());
            orderProductRepository.save(new OrderProduct(order, product,
                productOrderRequest.getProducts().get(i).getQuantity()));
        }
        return order;
    }

    @Override
    public OrderResponse getOrder(User user, Long orderId) throws AccessDeniedException {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
        }
        return getProductOrderResponse(orderId, order);
    }

    @Override
    //결제 수정권은 누가 가지고 있나요?
    public OrderResponse updateOrder(User user, Long orderId) throws AccessDeniedException {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
        }
        order.updateStatus("결제 취소");
        return getProductOrderResponse(orderId, order);
    }

    @Override
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
