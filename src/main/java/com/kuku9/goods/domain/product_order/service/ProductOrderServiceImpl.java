package com.kuku9.goods.domain.product_order.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.NOT_EQUAL_USER_ID;

import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.order_product.repository.OrderProductRepository;
import com.kuku9.goods.domain.product.dto.ProductResponse;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.product_order.dto.ProductOrderResponse;
import com.kuku9.goods.domain.product_order.dto.ProductOrdersRequest;
import com.kuku9.goods.domain.product_order.entity.ProductOrder;
import com.kuku9.goods.domain.product_order.repository.ProductOrderRepository;
import com.kuku9.goods.domain.user.entity.User;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService {

	private final ProductOrderRepository productOrderRepository;
	private final OrderProductRepository orderProductRepository;
	private final ProductRepository productRepository;

    @Override
    public ProductOrder createOrder(User user, ProductOrdersRequest productOrderRequest) {
        ProductOrder productOrder = productOrderRepository.save(
            new ProductOrder(user, productOrderRequest.getAddress()));
        for (int i = 0; i < productOrderRequest.getProducts().size(); i++) {
            Product product = productRepository.findById(
                    productOrderRequest.getProducts().get(i).getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
            if(product.getQuantity() < productOrderRequest.getProducts().get(i).getQuantity()){
                throw new IllegalArgumentException("재고가 부족합니다.");
            }
            product.updateQuantity(productOrderRequest.getProducts().get(i).getQuantity());
            orderProductRepository.save(new OrderProduct(productOrder, product,
                productOrderRequest.getProducts().get(i).getQuantity()));
        }
        return productOrder;
    }

    @Override
    public ProductOrderResponse getOrder(User user, Long orderId) throws AccessDeniedException {
        ProductOrder productOrder = productOrderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        if (!productOrder.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
        }
        return getProductOrderResponse(orderId, productOrder);
    }

    @Override
    //결제 수정권은 누가 가지고 있나요?
    public ProductOrderResponse updateOrder(User user, Long orderId) throws AccessDeniedException {
        ProductOrder productOrder = productOrderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        if (!productOrder.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
        }
        productOrder.updateStatus("결제 취소");
        return getProductOrderResponse(orderId, productOrder);
    }

    @Override
    public void deleteOrder(User user, Long orderId) throws AccessDeniedException {
        ProductOrder productOrder = productOrderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        if (!productOrder.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
        }
        productOrderRepository.delete(productOrder);
    }

    private ProductOrderResponse getProductOrderResponse(Long orderId, ProductOrder productOrder) {
        List<OrderProduct> orderProducts = orderProductRepository.findAllByProductOrderId(orderId);
        List<ProductResponse> products = new ArrayList<>();
        for (OrderProduct orderProduct : orderProducts) {
            products.add(new ProductResponse(orderProduct.getProduct()));
        }
        return new ProductOrderResponse(productOrder, products);
    }


}
