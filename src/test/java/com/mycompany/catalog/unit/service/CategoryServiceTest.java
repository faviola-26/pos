package com.mycompany.catalog.unit.service;

import com.mycompany.catalog.exceptions.EntityNotFoundException;
import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.exceptions.NoSingleResultException;
import com.mycompany.catalog.model.Category;
import com.mycompany.catalog.repository.CategoryRepository;
import com.mycompany.catalog.services.CategoryService;
import java.util.Optional;
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

public class CategoryServiceTest {
    @Autowired
    private Category category;
    
    @Autowired
    private Category saved;
    
    @Autowired
    private CategoryService service;
    
    @MockBean
    private CategoryRepository repository;
    
    @BeforeEach
    public void init(){
        category.setId(null);
        category.setName("Footware");
        category.setSubCategories(null);
    }
    
    @Test
    public void given_category_hasnt_name_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        category.setName(null);
        //when
        when(repository.save(category)).thenThrow(InvalidEntityException.class);
        //then
        Assertions.assertThrows(InvalidEntityException.class, ()->{ 
            repository.save(category);
        });   
    }
    
    @Test
    public void given_category_name_is_out_of_range_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        String name = "";
        
        for(int i = 0; i < 52; i++){
            name += "a";
        }
        category.setName(name);
        //when
        when(repository.save(category)).thenThrow(InvalidEntityException.class);
        //then
        Assertions.assertThrows(InvalidEntityException.class, ()->{
           service.save(category);
        });
    }
    
    @Test
    public void given_id_exists_when_fetching_category_then_it_should_retrieve_only_category_table() throws EntityNotFoundException, NoSingleResultException{
        Category found = category;
        //given
        Long id = Long.valueOf("1");
        category.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(found));
        //when
        Category result = service.findById(id);
        //then
        Assertions.assertAll(()-> Assertions.assertEquals(found.getId(), result.getId()),
                             ()-> Assertions.assertEquals(found.getName(), result.getName()),
                             ()-> Assertions.assertEquals(found.getSubCategories(), result.getSubCategories()));
        
    }
    
    @Test
    public void given_id_exists_when_fetching_subtree_then_it_should_retrieve_all_categories() throws EntityNotFoundException, NoSingleResultException {
        Category found = category;
        //given
        Long id = Long.valueOf("1");
        category.setId(id);
        when(repository.findSubTreeById(id)).thenReturn(found);
        //when
        Category result = service.findSubTreeById(id);
        //then
        Assertions.assertAll(()-> Assertions.assertEquals(found.getId(), result.getId()),
                             ()-> Assertions.assertEquals(found.getName(), result.getName()),
                             ()-> Assertions.assertEquals(found.getSubCategories(), result.getSubCategories()));
    }
    
    @Test
    public void given_category_is_valid_when_saving_then_should_pass() throws InvalidEntityException{
        //given
        //when
        when(repository.save(category)).thenReturn(saved);
        Long expected = saved.getId();
        Long actual = service.save(category);
        //then
        Assertions.assertEquals(expected, actual);
    }
}
