package com.kuku9.goods.domain.product.entity;

import com.kuku9.goods.domain.seller.dto.*;
import com.kuku9.goods.domain.seller.entity.*;
import com.kuku9.goods.global.common.entity.*;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET deleted_at=CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at IS NULL")
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
	private Long price;

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
//        if (!requestDto.getPrice().isEmpty()) {
//            this.price = requestDto.getPrice();
//        }
	}
}
