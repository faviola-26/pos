package com.mycompany.app.unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.exceptions.InvalidEntityException;
import com.mycompany.app.model.Product;
import com.mycompany.app.services.ProductService;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CatalogControllerTest {  
    @LocalServerPort
    private int port;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private Product product;
    
    @Autowired
    private Product saved;
    
    @Autowired
    private TestRestTemplate rest;
    
    @MockBean
    private ProductService service;
    
    public String getEndPointSaveProduct(){
        return "http://localhost:" + port + "/catalog/product";
    }
    
    @BeforeEach
    public void init(){
        product.setId(null);
        product.setName("Shoe");
        product.setDescription("Formal footware");
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
        mapper.disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES);
    }
    
    @Test
    public void given_product_has_id_when_saving_then_should_fail() throws InvalidEntityException, URISyntaxException, JsonProcessingException{
        //given
        product.setId(Long.valueOf("1"));
        when(service.save(product)).thenThrow(InvalidEntityException.class);
        
        int expected = 422;
        
        URI uri = new URI(this.getEndPointSaveProduct());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        ResponseEntity response = rest.postForEntity(uri, entity, Product.class);
        
        Assertions.assertEquals(expected, response.getStatusCodeValue());
    }
    
    @Test
    public void given_product_name_hasnt_min_value_when_saving_then_should_fail() throws InvalidEntityException, URISyntaxException{
        //given
        product.setName("a");
        when(service.save(product)).thenThrow(InvalidEntityException.class);
        
        int expected = 422;
        
        URI uri = new URI(this.getEndPointSaveProduct());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Product> entity = new HttpEntity<Product>(headers);
        var response = rest.postForEntity(uri, entity, Product.class);
    }
    
    @Test
    public void given_product_name_hasnt_max_value_when_saving_then_should_fail() throws InvalidEntityException{
        String name = "";
        //given
        for(int i = 0; i < 52; i++){
            name += "a";
        }
        product.setName(name);
        when(service.save(product)).thenThrow(InvalidEntityException.class);
        
        int expected = 422;
        
        
    }
    
    @Test
    public void given_product_description_hasnt_max_value_when_saving_then_should_fail(){
        
    }
    
    @Test
    public void given_user_provides_no_id_when_request_product_then_should_fail(){
        
    }
    
    @Test
    public void given_user_provides_non_existent_id_when_request_product_then_should_fail(){
        
    }
    
    @Test
    public void given_product_hasnt_id_when_saving_then_should_pass() throws InvalidEntityException, URISyntaxException{
        //given
        product.setId(null);
        when(service.save(product)).thenReturn(saved.getId());
        //en que momento usamos el save que definimos en el when?
        int expected = 200;
        
        URI uri = new URI(this.getEndPointSaveProduct());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Product> entity = new HttpEntity<Product>(headers);
        
        var response = rest.postForEntity(uri, entity, Product.class);
    }
}
