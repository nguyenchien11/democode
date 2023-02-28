package com.coderc.ltsn.models.response;

import lombok.Data;

@Data
public class AddOrderDetailResponse {

    private long quantity;

    private long totalPrice;

    private AddProductResponse product;
}
