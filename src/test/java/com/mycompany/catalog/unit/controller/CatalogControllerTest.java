package com.mycompany.catalog.unit.controller;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.model.Product;
import com.mycompany.catalog.services.ProductService;
import com.mycompany.catalog.util.URL;
import java.net.URISyntaxException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class CatalogControllerTest {  
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private Product product;
    
    private MockMvc mvc;
    
    private URL url;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @MockBean
    private ProductService service;
    

    @BeforeAll
    public void setUp() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
        url = new URL(port);
    }
    
    @BeforeEach
    public void init(){
        product.setId(null);
        product.setName("Shoe");
        product.setDescription("Formal footware");
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
        mapper.setSerializationInclusion(Include.NON_NULL);
    }
  
    @Test
    public void given_product_has_id_when_saving_then_should_fail() throws InvalidEntityException, URISyntaxException, JsonProcessingException, Exception{
        //given
        product.setId(Long.valueOf("1"));
        when(service.save(any(Product.class))).thenThrow(InvalidEntityException.class);
        
        mvc.perform(post(url.getSaveProduct())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
    
    @Test
    public void given_product_name_hasnt_min_when_saving_then_should_fail() throws InvalidEntityException, URISyntaxException, JsonProcessingException, Exception{
        //given
        product.setName("a");
        when(service.save(any(Product.class))).thenThrow(new InvalidEntityException("product name hast min value"));
        
        mvc.perform(post(url.getSaveProduct())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product)))
                .andExpect(status().isUnprocessableEntity());
    }
    
}
