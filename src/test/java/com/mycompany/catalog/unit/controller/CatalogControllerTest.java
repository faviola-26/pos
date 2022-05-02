package com.mycompany.catalog.unit.controller;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.catalog.exceptions.EntityNotFoundException;
import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.model.Product;
import com.mycompany.catalog.services.ProductService;
import com.mycompany.catalog.util.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test_catalog.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
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
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        url = new URL(port);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    @BeforeEach
    public void init() {
        product.setId(null);
        product.setName("Shoe");
        product.setDescription("Formal footware");
    }

    @Test //Favi
    public void given_user_provides_a_product_when_saving_product_then_should_pass() throws Exception {

        when(service.save(any(Product.class))).thenReturn(Long.getLong("1"));

        mvc.perform(post(url.getSaveProduct())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void given_product_has_id_when_saving_then_should_fail() throws Exception {
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
    public void given_product_name_hasnt_min_value_when_saving_then_should_fail() throws Exception {
        product.setName("a");
        when(service.save(any(Product.class))).thenThrow(InvalidEntityException.class);

        mvc.perform(post(url.getSaveProduct())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void given_product_name_hasnt_max_value_when_saving_then_should_fail() throws Exception {
        String name = "";
        //given
        for (int i = 0; i < 52; i++) {
            name += "a";
        }
        product.setName(name);
        when(service.save(any(Product.class))).thenThrow(InvalidEntityException.class);
        //then

        mvc.perform(post(url.getSaveProduct())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void given_product_description_hasnt_max_value_when_saving_then_should_fail() throws Exception {
        String description = "";
        //given
        for (int i = 0; i < 102; i++) {
            description += "a";
        }
        product.setDescription(description);
        when(service.save(any(Product.class))).thenThrow(InvalidEntityException.class);
        //then
        mvc.perform(post(url.getSaveProduct())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void given_user_provides_an_id_when_request_product_by_id_then_should_pass() throws Exception {
        Long id = Long.parseLong("1");

        when(service.getProductById(id)).thenReturn(any(Product.class));

        mvc.perform(get(url.getFindProductById(id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void given_user_provides_no_id_when_request_product_by_id_then_should_fail() throws Exception {
        //given
        Long id = null;
        when(service.getProductById(id)).thenThrow(EntityNotFoundException.class);

        mvc.perform(get(url.getFindProductById(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void given_user_provides_non_existent_id_when_request_product_by_id_then_should_fail() throws Exception {
        //given
        Long id = Long.getLong("100");
        when(service.getProductById(id)).thenThrow(EntityNotFoundException.class);

        mvc.perform(get(url.getFindProductById(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void given_user_provides_an_id_when_request_product_by_category_id_then_should_pass() throws Exception {
        Long id = Long.parseLong("1");

        when(service.findByCategory(id)).thenReturn((anyList()));
        
        mvc.perform(get(url.getFindProductByCategory(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void given_user_provides_no_id_when_request_product_by_category_then_should_fail() throws Exception {
        //given
        Long id = null;

        when(service.findByCategory(id)).thenThrow(EntityNotFoundException.class);

        mvc.perform(get(url.getFindProductByCategory(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void given_user_provides_non_existent_id_when_request_product_by_category_then_should_fail() throws Exception {
        //given
        Long id = Long.getLong("100");

        when(service.findByCategory(id)).thenThrow(EntityNotFoundException.class);

        mvc.perform(get(url.getFindProductByCategory(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void given_user_provides_an_id_when_request_product_by_characteristic_id_then_should_pass() throws Exception {
        List<Integer> ids = new ArrayList<>();
        List<String> values = new ArrayList<>();
        ids.add(1);
        values.add("blue");
        
        //when(service.findByCharacteristic(ids, values)).thenReturn(anyList(), eq(List<Product>));
        
        mvc.perform(get(url.getFindProductByCharacteristics(ids, values))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void given_user_provides_no_id_when_request_product_by_characteristic_then_should_fail() throws Exception {
        List<Integer> ids = new ArrayList<>();
        List<String> values = new ArrayList<>();
        
        when(service.findByCharacteristic(ids, values)).thenThrow(EntityNotFoundException.class);
        
        mvc.perform(get(url.getFindProductByCharacteristics(ids, values))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void given_user_provides_non_existent_id_when_request_product_by_characteristic_then_should_fail() throws Exception {
        List<Integer> ids = new ArrayList<>();
        List<String> values = new ArrayList<>();
        
        ids.add(100);
        ids.add(200);
        values.add("a");
        values.add("b");

        when(service.findByCharacteristic(ids, values)).thenThrow(EntityNotFoundException.class);

        mvc.perform(get(url.getFindProductByCharacteristics(ids, values))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void given_user_provides_an_id_when_delete_product_then_should_pass() throws Exception {
        Long id = Long.getLong("1");
        
        mvc.perform(delete(url.getDeleteProduct(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    public void given_user_provides_a_null_id_when_delete_product_then_should_fail() throws Exception {
        Long id = null;
        
        //when(service.delete(id)).thenThrow(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(service).delete(id);
        
        mvc.perform(delete(url.getDeleteProduct(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void given_user_provides_non_existent_id_when_delete_product_then_should_fail() throws Exception {
        Long id = Long.getLong("100");
        
        Mockito.doThrow(EntityNotFoundException.class).when(service).delete(id);
        
        mvc.perform(delete(url.getDeleteProduct(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void given_user_provides_a_product_when_update_product_then_should_pass() throws Exception {
        product.setId(Long.valueOf("1"));
        product.setName("No classic");
        product.setDescription("");
        
        when(service.update(product)).thenReturn(product);
        
        mvc.perform(put(url.getUpdateProduct())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    public void given_user_provides_a_null_product_when_update_product_then_should_fail() throws Exception {
        
        Mockito.doThrow(EntityNotFoundException.class).when(service).update(any(Product.class));
        
        mvc.perform(put(url.getUpdateProduct())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }    
    
    @Test
    public void given_user_provides_a_null_id_product_when_update_product_then_should_fail() throws Exception {
        
        Mockito.doThrow(EntityNotFoundException.class).when(service).update(any(Product.class));
        
        mvc.perform(put(url.getUpdateProduct())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
