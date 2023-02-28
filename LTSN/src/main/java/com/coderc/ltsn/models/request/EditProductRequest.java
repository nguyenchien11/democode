package com.coderc.ltsn.models.request;

import lombok.Data;

@Data
public class EditProductRequest {
    private long id;

    private String name;

    private long quantity;

    private long price;
}
