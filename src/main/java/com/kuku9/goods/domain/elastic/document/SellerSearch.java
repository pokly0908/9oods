//package com.kuku9.goods.domain.elastic.document;
//
//import lombok.Getter;
//import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
//
//@Getter
//public class SellerSearch {
//
//    private String brandName;
//    private String introduce;
//
//    private SellerSearch (String brandName, String introduce) {
//        if (brandName == null) {
//            throw new NullPointerException("titleName must be notnull");
//        }
//        if (introduce == null) {
//            throw new NullPointerException("introduce must be notnull");
//        }
//
//        this.brandName = brandName;
//        this.introduce = introduce;
//    }
//
//    public static SellerSearch of(String brandName, String introduce) {
//        return new SellerSearch(
//            brandName,
//            introduce
//        );
//    }
//}
