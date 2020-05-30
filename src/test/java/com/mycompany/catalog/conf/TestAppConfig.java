package com.mycompany.catalog.conf;

import com.mycompany.catalog.model.Category;
import com.mycompany.catalog.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

//@Import(ProductRepository.class)
//@Configuration
public class TestAppConfig {    
    @Bean
    @Scope("prototype")
    public Category category(){
        return new Category();
    }
    
    @Bean
    @Scope("prototype")
    public Product product(){
        return new Product();
    }
    
}
