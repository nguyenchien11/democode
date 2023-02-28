package com.coderc.ltsn.repository;

import com.coderc.ltsn.models.Order;
import com.coderc.ltsn.models.OrderDetail;
import com.coderc.ltsn.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDeatailRepository extends JpaRepository<OrderDetail , Long> {
    OrderDetail findByProductAndAndOrder(Product product, Order order);
}
