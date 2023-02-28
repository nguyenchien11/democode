package com.coderc.ltsn.controller;

import com.coderc.ltsn.models.OrderDetail;
import com.coderc.ltsn.models.ProductCart;
import com.coderc.ltsn.models.request.AddOrderRequest;
import com.coderc.ltsn.models.response.AddOrderDetailResponse;
import com.coderc.ltsn.models.response.AddOrderResponse;
import com.coderc.ltsn.repository.CartRepository;
import com.coderc.ltsn.repository.OrderDeatailRepository;
import com.coderc.ltsn.repository.OrderRepository;
import com.coderc.ltsn.repository.ProductRepository;
import com.coderc.ltsn.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RequestMapping("/api/order")
@RestController
public class OrderController {
    private final OrderDeatailRepository orderDeatailRepository;

    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ProductRepository productRepository, OrderDeatailRepository orderDeatailRepository, CartRepository cartRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.productRepository = productRepository;
        this.orderDeatailRepository = orderDeatailRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<AddOrderResponse> addOrder(@RequestBody AddOrderRequest orderRequest,
                                           @PathVariable long productId){
        var product = productRepository.findById(productId);
        var order = orderService.generateOrder(orderRequest);
        var orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setOrderId(order.getId());
        orderDetail.setProduct(product.get());
        orderDetail.setProductId(productId);
        orderDetail.setQuantity(orderRequest.getQuantityProduct());
        orderDetail.setTotalPrice(orderDetail.getQuantity() * product.get().getPrice());
        order.setQuantityProduct(1);
        order.setTotalPriceOrder(orderDetail.getTotalPrice());
        orderDeatailRepository.save(orderDetail);
        orderRepository.save(order);
        List<AddOrderDetailResponse> list = new ArrayList<>();
        var orderDeatailResponse = modelMapper.map(orderDetail , AddOrderDetailResponse.class);
        list.add(orderDeatailResponse);
        var orderResponse = new AddOrderResponse(order.getOrderDate(), order.getNameofreceiver(),
               order.getAddressofrecevicer(), order.getNumberofreceiver(), order.getStatus(),
                order.getQuantityProduct(), order.getTotalPriceOrder(), list);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PostMapping("/checkOut")
    public ResponseEntity<AddOrderResponse> checkOutFromCart(@RequestBody AddOrderRequest request){
        var order = orderService.generateOrder(request);
        var cart = cartRepository.findByUserId(order.getUser().getId()).get();
        var i = 0;
        List<AddOrderDetailResponse> list = new ArrayList<>();
        for (var p: cart.getProductCarts()) {
            i++;
            var orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setOrderId(order.getId());
            orderDetail.setProduct(p.getProduct());
            orderDetail.setProductId(p.getProduct().getId());
            orderDetail.setTotalPrice(p.getTotalprice());
            orderDetail.setQuantity(p.getQuantity());
            order.setTotalPriceOrder(order.getTotalPriceOrder() + p.getTotalprice());
            orderDeatailRepository.save(orderDetail);
            var orderDeatailResponse = modelMapper.map(orderDetail , AddOrderDetailResponse.class);
            list.add(orderDeatailResponse);
        }
        order.setQuantityProduct(i);
        orderRepository.save(order);
        var orderResponse = new AddOrderResponse(order.getOrderDate(), order.getNameofreceiver(),
                order.getAddressofrecevicer(), order.getNumberofreceiver(), order.getStatus(),
                order.getQuantityProduct(), order.getTotalPriceOrder(), list);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/getList")
    public ResponseEntity<List<AddOrderResponse>> getAll(){
        var listOrder = orderRepository.findAll();
        var listOrderResponse = listOrder.stream()
                .map(order -> modelMapper.map(order , AddOrderResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listOrderResponse, HttpStatus.OK);
    }
}
