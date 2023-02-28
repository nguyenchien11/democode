package com.coderc.ltsn.service;

import com.coderc.ltsn.models.Cart;
import com.coderc.ltsn.models.User;
import com.coderc.ltsn.models.request.UpdateCartRequest;

import java.util.Optional;

public interface CartService {
    Optional<Cart> findCartByUserId();

    Cart addToCart(long productId);

    Cart findCartByUser();

    String updateCart(UpdateCartRequest request);

    boolean deleteFormCart(long productId);

}
