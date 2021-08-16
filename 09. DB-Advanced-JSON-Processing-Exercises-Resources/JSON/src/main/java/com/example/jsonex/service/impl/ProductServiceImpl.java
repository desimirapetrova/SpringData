package com.example.jsonex.service.impl;

import com.example.jsonex.constants.GlobalCOnstants;
import com.example.jsonex.model.dto.ProductSeedDto;
import com.example.jsonex.model.entity.Product;
import com.example.jsonex.repository.ProductRepository;
import com.example.jsonex.service.CategoryService;
import com.example.jsonex.service.ProductService;
import com.example.jsonex.service.UserService;
import com.example.jsonex.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class ProductServiceImpl implements ProductService {
    public static final String PRODUCT_FILE_NAME="products.json";
   private final Gson gson;
   private final ProductRepository productRepository;
   private final ModelMapper modelMapper;
   private final ValidationUtil validationUtil;
   private final UserService userService;
   private final CategoryService categoryService;
    public ProductServiceImpl(Gson gson, ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.gson = gson;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedProduct() throws IOException {
        String fileContent= Files.readString(Path.of(GlobalCOnstants.RESOURCE_FILE_PATH+PRODUCT_FILE_NAME));
        ProductSeedDto[]productSeedDtos=gson
                .fromJson(fileContent,ProductSeedDto[].class);
        Arrays.stream(productSeedDtos).filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product=modelMapper.map(productSeedDto,Product.class);
                product.setSeller(userService.findRandomUser());
                if(product.getPrice().compareTo(BigDecimal.valueOf(500L))>0){
                    product.setBuyer(userService.findRandomUser());
                }
                product.setCategories(categoryService.findRandomCategories());
                //productRepository.save(product)
                    return product;
                })
        .forEach(productRepository::save);

    }
}
