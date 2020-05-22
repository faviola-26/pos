package com.mycompany.app.conf;

import com.mycompany.app.model.Category;
import com.mycompany.app.model.Product;
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
