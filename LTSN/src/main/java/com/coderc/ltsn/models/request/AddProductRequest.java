package com.coderc.ltsn.models.request;

import lombok.Data;

@Data
public class AddProductRequest {

    private String cateName;

    private String name;

    private long quantity;

    private long price;

}
