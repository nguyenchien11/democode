package com.coderc.ltsn.models.response;

import lombok.Data;

@Data
public class AddProductResponse {

    private long id;

    private String name;

    private long quantity;

    private long price;

    private String image;


}
