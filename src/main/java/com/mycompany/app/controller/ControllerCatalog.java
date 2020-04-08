package com.mycompany.app.controller;

import com.mycompany.app.model.Product;
import com.mycompany.app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class ControllerCatalog {
    
    @PostMapping("/product")
    public Long saveProduct(@RequestBody Product product){
        return serviceProduct.saveProduct(product);
    }
    
    @Autowired 
    ProductService serviceProduct;
}
