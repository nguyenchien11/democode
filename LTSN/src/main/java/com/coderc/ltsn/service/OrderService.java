package com.coderc.ltsn.service;


import com.coderc.ltsn.models.Order;
import com.coderc.ltsn.models.Product;
import com.coderc.ltsn.models.request.AddOrderRequest;

import java.util.List;

public interface OrderService {

    Order generateOrder(AddOrderRequest request);

    String updateStatus(long orderId);

    List<Order> getListOrderByOrderSuccess(String status);

    List<Order> getListOrderByOrderComplete(String status);

}
