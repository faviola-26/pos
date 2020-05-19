package com.mycompany.app.unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.exceptions.InvalidEntityException;
import com.mycompany.app.model.Product;
import com.mycompany.app.services.ProductService;
import java.net.URI;
import java.net.URISyntaxException;
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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CatalogControllerTest {  
    @LocalServerPort
    private int port;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private Product product;
    
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
        
        int expected = 403;
        
        var uri = new URI(this.getEndPointSaveProduct());
        var headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        var entity = new HttpEntity<Product>(headers);
        var response = rest.postForEntity(uri, entity, Product.class);
        
        //Assertions.assertEquals(expected, response.());
    }
    
    @Test
    public void given_product_hasnt_id_when_saving_then_should_pass(){
        //given
        
    }
    
}
