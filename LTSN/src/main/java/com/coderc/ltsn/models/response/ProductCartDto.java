package com.coderc.ltsn.models.response;

import com.coderc.ltsn.models.Product;
import lombok.Data;

@Data
public class ProductCartDto {

    private long id;

    private long quantity;

    private long totalprice;

    private AddProductResponse product;
}
