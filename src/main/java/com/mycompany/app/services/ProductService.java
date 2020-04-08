package com.mycompany.app.services;

import com.mycompany.app.model.Product;
import com.mycompany.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    public Long saveProduct(Product product){
        return repository.save(product).getId();
    }
    
    @Autowired
    private ProductRepository repository;
}
