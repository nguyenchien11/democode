package com.coderc.ltsn.service.impl;


import com.coderc.ltsn.models.Order;
import com.coderc.ltsn.models.OrderDetail;
import com.coderc.ltsn.models.Product;
import com.coderc.ltsn.models.User;
import com.coderc.ltsn.models.request.AddOrderRequest;
import com.coderc.ltsn.repository.*;
import com.coderc.ltsn.service.CustomUserDetailService;
import com.coderc.ltsn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceimpl implements OrderService {


    private final ProductRepository productRepository;
    private final OrderDeatailRepository orderDeatailRepository;
    private final OrderRepository orderRepository;
    private final CustomUserDetailService userDetailService;
    private final CartRepository cartRepository;
    private final ProductCartRepository productCartRepository;

    public OrderServiceimpl(ProductRepository productRepository, OrderDeatailRepository orderDeatailRepository, OrderRepository orderRepository, CustomUserDetailService userDetailService, CartRepository cartRepository, ProductCartRepository productCartRepository) {
        this.productRepository = productRepository;
        this.orderDeatailRepository = orderDeatailRepository;
        this.orderRepository = orderRepository;
        this.userDetailService = userDetailService;
        this.cartRepository = cartRepository;
        this.productCartRepository = productCartRepository;
    }

    @Override
    public Order generateOrder(AddOrderRequest request) {
        var user = new User();
        user.setUsername(userDetailService.getPrincipal().getUsername());
        user.setPassword(userDetailService.getPrincipal().getPassword());
        user.setId(userDetailService.getPrincipal().getId());
        var order = new Order();
        order.setNameofreceiver(request.getNameofreceiver());
        order.setNumberofreceiver(request.getNumberofreceiver());
        order.setAddressofrecevicer(request.getAddressofrecevicer());
        order.setStatus("Order Success");
        order.setOrderDate(new Date());
        order.setUser(user);
        return orderRepository.save(order);
    }




    @Override
    public String updateStatus(long orderId) {
        return null;
    }

    @Override
    public List<Order> getListOrderByOrderSuccess(String status) {
        return orderRepository.findByStatus("Order Success");
    }

    @Override
    public List<Order> getListOrderByOrderComplete(String status) {
        return orderRepository.findByStatus("Order Complete");
    }

}
