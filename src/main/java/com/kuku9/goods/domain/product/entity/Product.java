package com.kuku9.goods.domain.product.entity;

import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.dto.ProductUpdateRequestDto;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String price;

    @Column
    private Boolean status = true;

    public Product(ProductRegistRequestDto requestDto, Seller seller) {
        this.name = requestDto.getProductName();
        this.description = requestDto.getProductDescription();
        this.price = requestDto.getProductPrice();
        this.seller = seller;
    }

    public void updateOrderStatus(Boolean status) {
        if (status) {
            this.status = false;
        }
    }

    public void updateProduct(ProductUpdateRequestDto requestDto) {
        if (!requestDto.getName().isEmpty()) {
            this.name = requestDto.getName();
        }
        if (!requestDto.getDescription().isEmpty()) {
            this.description = requestDto.getDescription();
        }
        if (!requestDto.getPrice().isEmpty()) {
            this.price = requestDto.getPrice();
        }
    }
}
