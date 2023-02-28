package com.coderc.ltsn.repository;

import com.coderc.ltsn.models.Cart;
import com.coderc.ltsn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(long userId);
}
