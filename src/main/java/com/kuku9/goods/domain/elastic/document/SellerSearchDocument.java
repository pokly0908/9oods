//package com.kuku9.goods.domain.elastic.document;
//
//import java.lang.annotation.Documented;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.elasticsearch.annotations.Document;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class SellerSearchDocument {
//
//    private String brand;
//    private String brand_chosung;
//    private String brand_jamo;
//    private String brand_engtokor;
//    private String introduce;
//    private String introduce_chosung;
//    private String introduce_jamo;
//    private String introduce_engtokor;
//
//    private SellerSearchDocument (
//        String brand, String brand_chosung, String brand_jamo, String brand_engtokor,
//        String introduce, String introduce_chosung, String introduce_jamo, String introduce_engtokor
//    ) {
//        this.brand = brand;
//        this.brand_chosung = brand_chosung;
//        this.brand_jamo = brand_jamo;
//        this.brand_engtokor = brand_engtokor;
//        this.introduce = introduce;
//        this.introduce_chosung = introduce_chosung;
//        this.introduce_jamo = introduce_jamo;
//        this.introduce_engtokor = introduce_engtokor;
//    }
//
//    public static SellerSearchDocument of (
//        String brand, String brand_chosung, String brand_jamo, String brand_engtokor,
//        String introduce, String introduce_chosung, String introduce_jamo, String introduce_engtokor
//    ) {
//        return new SellerSearchDocument(
//            brand,
//            brand_chosung,
//            brand_jamo,
//            brand_engtokor,
//            introduce,
//            introduce_chosung,
//            introduce_jamo,
//            introduce_engtokor
//        );
//    }
//
//}
