package com.mycompany.catalog.unit.repository;

import com.mycompany.catalog.exceptions.EntityNotFoundException;
import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.exceptions.NoSingleResultException;
import com.mycompany.catalog.model.Category;
import com.mycompany.catalog.repository.CategoryRepository;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    private Category root;
    
    @Autowired
    private CategoryRepository repository;
    
    @BeforeEach
    public void init(){
        root.setId(null);
        root.setName("footware");
        root.setAncestor(null);
        root.setSubCategories(null);
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
    public void given_category_has_name_when_saving_then_should_pass() throws InvalidEntityException{
        //given
        root.setName("old man");
        //when
        Category result = repository.save(root);
        //then
        Assertions.assertNotNull(result);
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
    public void given_category_name_is_in_range_when_saving_then_should_pass() throws InvalidEntityException{
        String name = "";
        //given
        for(int i = 0; i < 50; i++){
            name += "a";
        }
        root.setName(name);
        //when
        Category result = repository.save(root);
        //then
        Assertions.assertNotNull(result);
    }
    
    @Test
    public void given_id_exists_when_fetching_category_then_it_should_retrieve_only_category_table(){
        //given
        Long id = Long.valueOf("1");
        //when
        Category result = repository.findById(id).get();
        //then
        Assertions.assertAll(()-> Assertions.assertEquals(1, result.getId()),
                             ()-> Assertions.assertEquals("footware", result.getName()),
                             ()-> Assertions.assertEquals(null, result.getAncestor()));   
    }
    
    @Test
    public void given_id_exists_when_fetching_subtree_then_it_should_retrieve_all_categories() throws EntityNotFoundException, NoSingleResultException{
        //given
        Long id = Long.valueOf("1");
        root.setId(id);
        //when
        Category result = repository.findSubTreeById(id);
        Logger.getGlobal().info(result.getName());
                
        //then
        Assertions.assertAll(()-> Assertions.assertEquals(root.getId(), result.getId()),
                             ()-> Assertions.assertEquals(root.getName(), result.getName()),
                             ()-> Assertions.assertEquals(root.getAncestor(), result.getAncestor()),
                             ()-> Assertions.assertEquals(3, result.getSubCategories().size()));
    }
}
