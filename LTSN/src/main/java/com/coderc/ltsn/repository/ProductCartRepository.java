package com.coderc.ltsn.repository;


import com.coderc.ltsn.models.Cart;
import com.coderc.ltsn.models.Product;
import com.coderc.ltsn.models.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart , Long> {
    ProductCart findByCartAndProduct(Cart cart, Product product);

    List<ProductCart> findByCart(Cart cart);
}
