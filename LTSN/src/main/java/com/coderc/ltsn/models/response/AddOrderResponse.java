package com.coderc.ltsn.models.response;

import com.coderc.ltsn.models.OrderDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AddOrderResponse {

    private Date orderDate;

    private String addressofrecevicer , numberofreceiver, nameofreceiver;

    private String status;

    private long totalPriceOrder;

    private long quantityProduct;

    private List<AddOrderDetailResponse> orderdetails = new ArrayList<>();

    public AddOrderResponse(Date orderDate, String nameofreceiver, String addressofrecevicer, String numberofreceiver, String status, long quantityProduct, long totalPriceOrder, List<AddOrderDetailResponse> list) {
        this.orderDate = orderDate;
        this.nameofreceiver = nameofreceiver;
        this.addressofrecevicer = addressofrecevicer;
        this.numberofreceiver = numberofreceiver;
        this.status = status;
        this.quantityProduct = quantityProduct;
        this.totalPriceOrder = totalPriceOrder;
        this.orderdetails = list;
    }
}
