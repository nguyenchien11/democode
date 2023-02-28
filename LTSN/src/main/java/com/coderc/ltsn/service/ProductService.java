package com.coderc.ltsn.service;

import com.coderc.ltsn.models.Product;
import com.coderc.ltsn.models.request.AddProductRequest;
import com.coderc.ltsn.models.request.EditCategoryRequest;
import com.coderc.ltsn.models.request.EditProductRequest;
import com.coderc.ltsn.models.response.AddProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product add(AddProductRequest request, MultipartFile image) throws IOException;

    Product update(EditProductRequest request, MultipartFile image) throws IOException;

    List<Product> getAll();

    boolean delete(long id);
}
