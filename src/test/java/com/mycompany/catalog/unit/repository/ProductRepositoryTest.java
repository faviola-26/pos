package com.mycompany.catalog.unit.repository;

import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.model.Product;
import com.mycompany.catalog.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test_catalog.properties")
@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private Product product;
    
    @Resource(name = "productRepository")
    private ProductRepository repository;
    
    @BeforeEach
    public void init(){
        product.setId(null);
        product.setName("Shoe");
        product.setDescription("Formal footware");
    }
    
    /*@Test
    public void given_product_has_id_when_saving_then_should_fail(){
        //given
        product.setId(Long.valueOf("1"));
        Assertions.assertThrows(ConstraintViolationException.class, ()->{ 
            //when
            repository.saveAndFlush(product);
        });
    }*/
    
    @Test
    public void given_product_hasnt_id_when_saving_then_should_pass(){
        //given
        product.setId(null);
        //when
        Product saved = repository.save(product);
        //then
        Assertions.assertNotNull(saved);
    }
    
    @Test
    public void given_product_name_hasnt_min_value_when_saving_then_should_fail(){
        //given
        product.setName("a");
        Assertions.assertThrows(ConstraintViolationException.class, ()->{ 
            //when
            repository.save(product);
        });
    }
    
    @Test
    public void given_product_name_has_min_value_when_saving_then_should_pass(){
        //given
        product.setName("aa");
        //when
        Product saved = repository.save(product);
        //then
        Assertions.assertNotNull(saved);
    }
    
    @Test
    public void given_product_name_hasnt_max_value_when_saving_then_should_fail(){
        String name = "";
        //given
        for(int i = 0; i < 52; i++){
            name += "a";
        }
        product.setName(name);
        Assertions.assertThrows(ConstraintViolationException.class, ()->{ 
            //when
            repository.save(product);
        });
    }
    
    @Test
    public void given_product_name_has_max_value_when_saving_then_should_pass(){
        String name = "";
        //given
        for(int i = 0; i < 50; i++){
            name += "a";
        }
        product.setName(name);
        //when
        Product saved = repository.save(product);
        //then
        Assertions.assertNotNull(saved);
    }
    
    @Test
    public void given_product_description_hasnt_max_value_when_saving_then_should_fail(){
        String description = "";
        //given
        for(int i = 0; i < 502; i++){
            description += "a";
        }
        product.setDescription(description);
        Assertions.assertThrows(ConstraintViolationException.class, ()->{ 
            //when
            repository.save(product);
        });
    }
    
    @Test
    public void given_product_description_has_max_value_when_saving_then_should_pass(){
        String description = "";
        //given
        for(int i = 0; i < 100; i++){
            description += "a";
        }
        product.setDescription(description);
        //when
        Product saved = repository.save(product);
        //then
        Assertions.assertNotNull(saved);
    }
    
    @Test
    public void given_user_provides_no_id_when_request_product_then_should_fail(){
        //given
        Long id = null;        
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, ()->{ 
            //when
            Product saved = repository.getOne(id);
        });
    }
    
    @Test
    public void given_user_provides_non_existent_id_when_request_product_then_should_fail(){
        //given
        Long id = Long.valueOf("100");
        //when
        Optional<Product> result = repository.findById(id);
        //then
        Assertions.assertTrue(result.isEmpty());
    }
    
    @Test
    public void given_user_provides_an_existent_id_when_request_product_then_should_pass(){
        Long id = Long.valueOf("1");
        //when
        Optional<Product> result = repository.findById(id);
        //then
        Assertions.assertTrue(result.isPresent());
    }   

    @Test
    public void given_user_list_when_request_all_products_then_should_pass(){
        //given
        //does not apply
        //when
        List<Product> result = repository.findAll();
        //then
        Assertions.assertEquals(3, result.size());
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void given_products_by_category_id_long_when_request_products_then_should_fail(){
        //given
        List<Product> result = repository.findByCategory(Long.valueOf(-1));
        //then
        Assertions.assertTrue(result.isEmpty());  
    }
    
    @Test
    public void given_products_by_category_id_when_request_products_then_should_fail(){
        //given
        List<Product> result = repository.findByCategory(Long.valueOf(-1));
        //then
        Assertions.assertTrue(result.isEmpty());
        
    }
    
    @Test
    public void given_products_by_category_id_when_request_products_then_should_pass(){
        
    }
    
    @Test
    public void given_products_by_characteristics_id_when_request_products_then_should_fail(){
        
    }
    
    @Test
    public void given_products_by_characteristics_id_when_request_products_then_should_pass(){
        
    }
    
    @Test
    public void given_user_not_valid_id_when_delete_product_then_should_fail(){
        Long id = Long.parseLong("100");
        
        Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{
            repository.deleteById(id);
        });
    }
    
    @Test
    public void given_user_null_id_when_delete_product_then_should_fail(){
        Long id = null;
        
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, ()->{
            repository.deleteById(id);
        });
    }
    
    @Test
    public void given_user_valid_product_when_update_product_then_should_pass(){
        String name = "aaaa";
        product.setId(Long.valueOf("1"));
        product.setName(name);
                   
        Product updated = repository.update(product);
        
        Assertions.assertEquals(name, updated.getName());
    }
    
    @Test
    public void given_user_name_hasnt_max_value_when_update_product_then_should_fail(){
        
    }
}
