package com.example.jsonex;

import com.example.jsonex.service.CategoryService;
import com.example.jsonex.service.ProductService;
import com.example.jsonex.service.UserService;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;

public class CommandLineRunnerImpl implements CommandLineRunner {
   private final CategoryService categoryService;
   private final UserService userService;
   private final ProductService productService;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        seedData();
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProduct();
    }
}
