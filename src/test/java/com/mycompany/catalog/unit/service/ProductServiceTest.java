package com.mycompany.catalog.unit.service;

import com.mycompany.catalog.exceptions.EntityNotFoundException;
import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.model.Product;
import com.mycompany.catalog.repository.ProductRepository;
import com.mycompany.catalog.services.ProductService;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test_catalog.properties")
public class ProductServiceTest {
    @Autowired
    private Product product;
    
    @Autowired
    private Product saved;
    
    @Autowired
    private ProductService service;
    
    @MockBean
    private ProductRepository mockRepository;
    
    @BeforeEach
    public void init(){
        product.setId(null);
        product.setName("Shoe");
        product.setDescription("Formal footware");
    }
    
    @Test
    public void given_product_has_id_when_saving_then_should_fail(){
        //given
        product.setId(Long.valueOf("1"));
        when(mockRepository.save(product)).thenThrow(ConstraintViolationException.class);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{ 
            service.save(product);
        });
    }
    
    @Test
    public void given_product_hasnt_id_when_saving_then_should_pass() throws InvalidEntityException{
        //given
        product.setId(null);
        when(mockRepository.save(product)).thenReturn(saved);
        Long expected = Long.valueOf("1");
        saved.setId(expected);
        Long actual = service.save(product);
        //then
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void given_product_name_hasnt_min_value_when_saving_then_should_fail(){
        //given
        product.setName("a");
        when(mockRepository.save(product)).thenThrow(ConstraintViolationException.class);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{ 
            service.save(product);
        });
    }
    
    @Test
    public void given_product_name_has_min_value_when_saving_then_should_pass() throws InvalidEntityException{
        //given
        product.setName("aa");
        when(mockRepository.save(product)).thenReturn(saved);
        Long expected = saved.getId();
        Long actual = service.save(product);
        //then
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void given_product_name_hasnt_max_value_when_saving_then_should_fail(){
        String name = "";
        //given
        for(int i = 0; i < 52; i++){
            name += "a";
        }
        product.setName(name);
        when(mockRepository.save(product)).thenThrow(ConstraintViolationException.class);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
           service.save(product);
        });
    }
    
    @Test
    public void given_product_name_has_max_value_when_saving_then_should_pass() throws InvalidEntityException{
        String name = "";
        //given
        for(int i = 0; i < 50; i++){
            name += "a";
        }
        product.setName(name);
        when(mockRepository.save(product)).thenReturn(saved);
        Long expected = saved.getId();
        Long actual = service.save(product);
        //then
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void given_product_description_hasnt_max_value_when_saving_then_should_fail(){
        String description = "";
        //given
        for(int i = 0; i < 502; i++){
            description += "a";
        }
        product.setDescription(description);
        when(mockRepository.save(product)).thenThrow(ConstraintViolationException.class);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{ 
            service.save(product);
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
        when(mockRepository.save(product)).thenReturn(saved);
        Long expected = saved.getId();
        Long actual = product.getId();
        //then
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void given_user_provides_no_id_when_request_product_then_should_fail(){
        //given
        Long id = null;
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, ()->{ 
            //when
            service.getProductById(id);
        });
    }
    
    @Test
    public void given_user_provides_non_existent_id_when_request_product_then_should_fail(){
        //given
        Long id = Long.valueOf("100");
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            service.getProductById(id);
        });       
    }
    
    @Test
    public void given_user_provides_an_existent_id_when_request_product_then_should_pass() throws InvalidEntityException{
        //given
        Long id = Long.valueOf("1");
        //when
        saved.setId(id);
        when(mockRepository.findById(id)).thenReturn(Optional.of(saved));
        //then
        Product result = service.getProductById(id);
        Assertions.assertNotNull(result);
    }
}
