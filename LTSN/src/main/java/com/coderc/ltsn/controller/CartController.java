package com.coderc.ltsn.controller;


import com.coderc.ltsn.models.request.UpdateCartRequest;
import com.coderc.ltsn.models.response.AddCartResponse;
import com.coderc.ltsn.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/cart")
@RestController
public class CartController {

    private final CartService cartService;

    private final ModelMapper modelMapper;

    public CartController(CartService cartService, ModelMapper modelMapper) {
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/getCart")
    public AddCartResponse getlist(){
        var cart = cartService.findCartByUser();
        var cartResponse = modelMapper.map(cart, AddCartResponse.class);
        return cartResponse;
    }


    @PostMapping("/add/{id}")
    public ResponseEntity<AddCartResponse> addToCart(@PathVariable long id){
        var cart = cartService.addToCart(id);
        var cartResponse = modelMapper.map(cart, AddCartResponse.class);
        return new ResponseEntity<>(cartResponse , HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCart(@RequestBody UpdateCartRequest request){
        var cart = cartService.updateCart(request);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public boolean deleFormCart(@PathVariable long productId){
        return cartService.deleteFormCart(productId);
    }


}
