package com.coderc.ltsn.repository;

import com.coderc.ltsn.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long>{

}
