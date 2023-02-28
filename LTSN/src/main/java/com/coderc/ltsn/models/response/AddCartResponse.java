package com.coderc.ltsn.models.response;

import lombok.Data;

import java.util.List;


@Data
public class AddCartResponse {
    private long id;

    List<ProductCartDto> productCarts;
}
