package com.mycompany.catalog.unit.repository;

import com.mycompany.catalog.exceptions.EntityNotFoundException;
import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.exceptions.NoSingleResultException;
import com.mycompany.catalog.model.Category;
import com.mycompany.catalog.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@ActiveProfiles("test")
@TestPropertySource(locations="classpath:test_catalog.properties")
@SpringBootTest
public class CategoryRepositoryTest {
    
    @Autowired
    private Category root;
    
    @Autowired
    private CategoryRepository repository;
    
    private Category saved;
    
    @BeforeEach
    public void init(){
        root.setId(null);
        root.setName("footware2");
        root.setSubCategories(null);
    }
    
    @AfterEach
    public void clean() {
        if(saved != null) {
            repository.delete(saved.getId());
            this.saved = null;
        }
    }
    
    @Test
    public void given_category_hasnt_name_when_saving_then_should_fail(){
        //given
        root.setName(null);
        //when
        Assertions.assertThrows(InvalidEntityException.class, ()->{
            repository.save(root);
        });
    }
    
    @Test
    public void given_category_name_is_out_of_range_when_saving_then_should_fail(){
        String name = "";
        //given
        for(int i = 0; i < 52; i++){
            name += "a";
        }
        root.setName(name);
        var ex = Assertions.assertThrows(InvalidEntityException.class, ()->repository.save(root));
    }
    
    @Test
    public void given_id_exists_when_fetching_category_then_it_should_retrieve_only_category_table(){
        //given
        Long id = Long.valueOf("1");
        //when
        Category result = repository.findById(id).get();
        //then
         Assertions.assertEquals(id, result.getId());
    }
    
    @Test
    public void given_id_exists_when_fetching_subtree_then_it_should_retrieve_all_categories() throws EntityNotFoundException, NoSingleResultException{
        //given
        Long id = Long.valueOf("1");
        root.setId(id);
        //when
        Category result = repository.findSubTreeById(id);
                
        //then
        Assertions.assertAll(()-> Assertions.assertEquals(id, result.getId()),
                             ()-> Assertions.assertEquals("footware", result.getName()),
                             ()-> Assertions.assertEquals(3, result.getSubCategories().size()));
    }
    
    @Test
    public void given_category_has_name_when_saving_then_should_pass() throws InvalidEntityException{
        //given
        // default init values
        
        //when
        this.saved = repository.save(root);
        //then
        Assertions.assertNotNull(saved);
    }
}
