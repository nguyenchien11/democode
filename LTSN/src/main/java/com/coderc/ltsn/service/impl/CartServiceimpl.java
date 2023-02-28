package com.coderc.ltsn.service.impl;

import com.coderc.ltsn.models.Cart;
import com.coderc.ltsn.models.ProductCart;
import com.coderc.ltsn.models.User;
import com.coderc.ltsn.models.request.UpdateCartRequest;
import com.coderc.ltsn.repository.CartRepository;
import com.coderc.ltsn.repository.ProductCartRepository;
import com.coderc.ltsn.repository.ProductRepository;
import com.coderc.ltsn.service.CartService;
import com.coderc.ltsn.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceimpl implements CartService {

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final CustomUserDetailService userDetailsService;

    private final ProductCartRepository productCartRepository;


    @Autowired
    public CartServiceimpl(ProductRepository productRepository, CartRepository cartRepository, CustomUserDetailService userDetailsService, ProductCartRepository productCartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userDetailsService = userDetailsService;
        this.productCartRepository = productCartRepository;
    }


    @Override
    public Optional<Cart> findCartByUserId() {
        var user = new User();
        user.setUsername(userDetailsService.getPrincipal().getUsername());
        user.setPassword(userDetailsService.getPrincipal().getPassword());
        user.setId(userDetailsService.getPrincipal().getId());
        user.setCart(userDetailsService.getPrincipal().getCart());
        return cartRepository.findByUserId(user.getId());
    }

    @Override
    public Cart addToCart(long productId) {
        var product = productRepository.findById(productId);
        var user = new User();
        user.setUsername(userDetailsService.getPrincipal().getUsername());
        user.setPassword(userDetailsService.getPrincipal().getPassword());
        user.setId(userDetailsService.getPrincipal().getId());
        user.setCart(userDetailsService.getPrincipal().getCart());
        var cart = cartRepository.findByUserId(user.getId());
        if (cart.isEmpty()){
            var newCart = new Cart();
            newCart.setUser(user);
            cartRepository.save(newCart);
            if (productCartRepository.findByCartAndProduct(newCart, product.get()) == null){
                var productCartN = new ProductCart();
                productCartN.setProduct(product.get());
                productCartN.setCart(newCart);
                productCartN.setProductId(productId);
                productCartN.setCartId(newCart.getId());
                productCartN.setTotalprice(product.get().getPrice());
                productCartN.setQuantity(1);
                productCartRepository.save(productCartN);
                return newCart;
            }
        }
        var productCartN = productCartRepository.findByCartAndProduct(cart.get(), product.get());
        if(productCartN == null){
            var productCart = new ProductCart();
            productCart.setProduct(product.get());
            productCart.setCart(cart.get());
            productCart.setProductId(productId);
            productCart.setCartId(cart.get().getId());
            productCart.setTotalprice(product.get().getPrice());
            productCart.setQuantity(1);
            productCartRepository.save(productCart);
            return cart.get();
        }else {
            productCartN.setQuantity(productCartN.getQuantity() + 1);
            productCartN.setTotalprice(productCartN.getQuantity() * product.get().getPrice());
            productCartRepository.save(productCartN);
            return cart.get();
        }
    }

    @Override
    public Cart findCartByUser() {
        return findCartByUserId().get();
    }

    @Override
    public String updateCart(UpdateCartRequest request) {
        var user = new User();
        user.setUsername(userDetailsService.getPrincipal().getUsername());
        user.setPassword(userDetailsService.getPrincipal().getPassword());
        user.setId(userDetailsService.getPrincipal().getId());
        user.setCart(userDetailsService.getPrincipal().getCart());
        return productRepository.findById(request.getProductId())
                .map(product -> cartRepository.findByUserId(user.getId()))
                .map(cart -> productCartRepository.findByCartAndProduct(cart.get(), productRepository.findById(request.getProductId()).get()))
                .map(productCart -> {
                    productCart.setQuantity(request.getQuantity());
                    productCart.setTotalprice(request.getQuantity() * productRepository.findById(request.getProductId()).get().getPrice());
                    productCartRepository.save(productCart);
                    return "Change Success";
                }).orElse("Error");
    }

    @Override
    public boolean deleteFormCart(long productId) {
        var user = new User();
        user.setUsername(userDetailsService.getPrincipal().getUsername());
        user.setPassword(userDetailsService.getPrincipal().getPassword());
        user.setId(userDetailsService.getPrincipal().getId());
        user.setCart(userDetailsService.getPrincipal().getCart());
        return productRepository.findById(productId)
                .map(product -> cartRepository.findByUserId(user.getId()))
                .map(cart -> productCartRepository.findByCartAndProduct(cart.get(), productRepository.findById(productId).get()))
                .map(productCart -> {
                    productCartRepository.delete(productCart);
                    return true;
                }).orElse(false);
    }


}
