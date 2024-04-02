package com.kuku9.goods.domain.seller.entity;

import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.user.dto.request.RegisterSellerRequest;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "seller", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_name", "domain_name", "email", "phone_number"})
})
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE seller SET deleted_at=CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at IS NULL")
public class Seller extends BaseEntity {

    @OneToMany(mappedBy = "seller")
    private final List<Product> products = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("셀러 고유 식별자")
    private Long id;
    @Column
    @Comment("브랜드 이름")
    private String brandName;
    @Column
    @Comment("도메인 이름")
    private String domainName;
    @Column
    @Comment("브랜드 소개")
    private String introduce;
    @Column
    @Comment("판매자 문의 이메일")
    private String email;
    @Column
    @Comment("판매자 문의 전화번호")
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Seller(RegisterSellerRequest request, User user) {
        this.brandName = request.getBrandName();
        this.domainName = request.getDomainName();
        this.introduce = request.getIntroduce();
        this.email = request.getEmail();
        this.phoneNumber = request.getPhoneNumber();
        this.user = user;
    }

    public static Seller from(RegisterSellerRequest request, User user) {
        return new Seller(request, user);
    }


}
