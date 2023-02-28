package com.coderc.ltsn.service.impl;

import com.coderc.ltsn.models.Product;
import com.coderc.ltsn.models.request.AddProductRequest;
import com.coderc.ltsn.models.request.EditProductRequest;
import com.coderc.ltsn.repository.CategoryRepository;
import com.coderc.ltsn.repository.ProductRepository;
import com.coderc.ltsn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceimpl implements ProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceimpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    private static final String CURRENT_FOLDER = "C:/SaveImage/img";


    @Override
    public Product add(AddProductRequest request, MultipartFile image) throws IOException {

        String fileName = image.getOriginalFilename();
        try {
            FileCopyUtils.copy(image.getBytes(), new File(CURRENT_FOLDER + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categoryRepository.findByName(request.getCateName())
                .map(category -> {
                    var newProduct = new Product();
                    newProduct.setName(request.getName());
                    newProduct.setQuantity(request.getQuantity());
                    newProduct.setPrice(request.getPrice());
                    newProduct.setImage(fileName);
                    newProduct.setCategory(category);
                    return productRepository.save(newProduct);
                }).orElse(null);
    }

    @Override
    public Product update(EditProductRequest request, MultipartFile image) throws IOException {

        String fileName = image.getOriginalFilename();
        try {
            FileCopyUtils.copy(image.getBytes(), new File(CURRENT_FOLDER + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productRepository.findById(request.getId())
                .map(product -> {
                    product.setName(request.getName());
                    product.setQuantity(request.getQuantity());
                    product.setPrice(request.getPrice());
                    File filem = new File(CURRENT_FOLDER + product.getImage());
                    if (filem.isFile()) {
                        filem.delete();
                    }else {
                        System.out.println("đ phải file");
                    }
                    product.setImage(fileName);
                    return productRepository.save(product);
                }).orElse(null);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public boolean delete(long id) {
        return productRepository.findById(id).map(product -> {
           productRepository.delete(product);
           return true;
        }).orElse(false);
    }
}
