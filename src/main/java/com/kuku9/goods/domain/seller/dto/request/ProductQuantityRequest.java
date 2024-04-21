package com.kuku9.goods.domain.seller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor
@Value
public class ProductQuantityRequest {
    // todo :: 나중에 제한 걸어두기

    @NotBlank(message = "수량은 1개 이상 입력 해야합니다.")
    int quantity;

}
