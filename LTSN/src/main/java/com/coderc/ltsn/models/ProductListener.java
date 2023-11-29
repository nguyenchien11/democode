package com.coderc.ltsn.models;

import com.coderc.ltsn.service.RedisService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@AllArgsConstructor
public class ProductListener {

    @Autowired
    private RedisService redisService;

    @PrePersist
    public void onPrePersist(Product product) {
    }

    @PreUpdate
    public void onPreUpdate(Product product) {
        redisService.clearCache();
    }

    @PreRemove
    public void onPreRemove(Product product) {
    }
}
