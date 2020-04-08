package com.mycompany.app.model.unit;

import com.mycompany.app.model.Product;
import com.mycompany.app.repository.ProductRepository;
import javax.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {
    @Autowired
    private Product product;
    
    @Autowired
    private ProductRepository repository;
    
    @BeforeEach
    public void init(){
        product.setId(null);
        product.setName("Shoe");
        product.setDescription("Formal footware");
    }
    
    @Test
    public void given_product_has_id_when_saving_product_then_should_fail(){
        //given
        product.setId(Long.valueOf("1"));
        Assertions.assertThrows(ConstraintViolationException.class, ()->{ 
            //when
            repository.save(product);
        });
    }
}
