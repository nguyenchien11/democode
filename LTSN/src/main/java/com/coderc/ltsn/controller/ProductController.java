package com.coderc.ltsn.controller;

import com.coderc.ltsn.models.request.AddProductRequest;
import com.coderc.ltsn.models.request.EditProductRequest;
import com.coderc.ltsn.models.response.AddProductResponse;
import com.coderc.ltsn.repository.ProductRepository;
import com.coderc.ltsn.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/product")
@RestController
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper, ProductRepository productRepository) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AddProductResponse>> getAllProduct(){
        var listProduct = productService.getAll().stream()
                .map(product -> modelMapper.map(product , AddProductResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AddProductResponse> addProduct(@ModelAttribute AddProductRequest request, @RequestPart("image") MultipartFile file) throws IOException {
        var product = productService.add(request,file);
        var productResponse = modelMapper.map(product, AddProductResponse.class);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<AddProductResponse> editProduct(@ModelAttribute EditProductRequest request, @RequestPart("image") MultipartFile file) throws IOException {
        var product = productService.update(request,file);
        var productResponse = modelMapper.map(product, AddProductResponse.class);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable long id){
        return productService.delete(id);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AddProductResponse> getById(@PathVariable long id){
        var product = productRepository.findById(id);
        var productResponse = modelMapper.map(product.get(), AddProductResponse.class);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
}
