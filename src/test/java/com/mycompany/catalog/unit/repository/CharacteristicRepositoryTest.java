package com.mycompany.catalog.unit.repository;

import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.model.Characteristic;
import com.mycompany.catalog.repository.CharacteristicRepository;
import javax.validation.ConstraintViolationException;
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
public class CharacteristicRepositoryTest {
    @Autowired
    private Characteristic characteristic;
    
    @Autowired
    private CharacteristicRepository repository;
    
    @BeforeEach
    public void init(){
        characteristic.setId(null);
        characteristic.setName("Material");
        characteristic.setFormat("text");
        characteristic.setType(1);
        characteristic.setValueInterpreter(1);
        characteristic.setDescription("good shoe");
    }
    
    @Test
    public void given_characteristic_hasnt_name_when_saving_then_should_fail(){
        //given
        characteristic.setName(null);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
   
    @Test
    public void given_characteristic_name_is_out_of_range_when_saving_then_should_fail(){
        //given
        String name = "";
        
        for(int i = 0; i < 62; i++){
            name += "a";
        }
        characteristic.setName(name);
        //when
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_format_is_out_of_range_when_saving_then_should_fail(){
        //given
        String format = "";
        
        for(int i = 0; i < 17; i++){
            format += "a";
        }
        characteristic.setFormat(format);
        //when
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_hasnt_format_when_saving_then_should_fail(){
        //given
        characteristic.setFormat(null);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_hasnt_type_when_saving_then_should_fail(){
        //given
        characteristic.setType(null);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_hasnt_valueInterpreter_when_saving_then_should_fail(){
        //given
        characteristic.setValueInterpreter(null);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_hasnt_description_when_saving_then_should_fail(){
        //given
        characteristic.setDescription(null);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_description_is_out_of_range_when_saving_then_should_fail(){
        //given
        String description = "";
        
        for(int i = 0; i < 105; i++){
            description += "a";
        }
        characteristic.setDescription(description);
        //when
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_is_valid_when_saving_then_should_pass() throws InvalidEntityException{
        //given
        //when
        Characteristic result = repository.save(characteristic);
        //then
        Assertions.assertNotNull(result);
    }
}
