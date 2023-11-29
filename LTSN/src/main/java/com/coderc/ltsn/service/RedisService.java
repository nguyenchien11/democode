package com.coderc.ltsn.service;

import com.coderc.ltsn.config.RedisConfig;
import com.coderc.ltsn.models.response.AddProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.servers.Server;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<AddProductResponse> getAllproduct(String name) throws JsonProcessingException {
        String key = String.format("list_product_%s",name);
        String json = (String) redisTemplate.opsForValue().get(key);
        List<AddProductResponse> product_resp =
                json != null ? objectMapper.readValue(json, new TypeReference<List<AddProductResponse>>() {}) : null;
        return product_resp;
    }

    public String saveCache(List<AddProductResponse> product_list, String name) throws JsonProcessingException {
        String key = String.format("list_product_%s",name);
        String json = objectMapper.writeValueAsString(product_list);
        redisTemplate.opsForValue().set(key,json);
        return "OK";
    }

    public String clearCache(){
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        return "OK";
    }

}
