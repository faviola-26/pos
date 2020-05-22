package com.mycompany.app.unit.service;

import com.mycompany.app.exceptions.InvalidEntityException;
import com.mycompany.app.model.Characteristic;
import com.mycompany.app.repository.CharacteristicRepository;
import com.mycompany.app.services.CharacteristicService;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CharacteristicServiceTest {
    @Autowired
    private Characteristic characteristic;
    
    @Autowired
    private Characteristic saved;
    
    @Autowired
    private CharacteristicService service;
    
    @MockBean
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
    public void given_characteristic_hasnt_name_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        characteristic.setName(null);
        when(repository.save(characteristic)).thenThrow(InvalidEntityException.class);
        //then
        Assertions.assertThrows(InvalidEntityException.class, ()->{
            service.save(characteristic);
        });
    }
   
    @Test
    public void given_characteristic_name_is_out_of_range_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        String name = "";
        
        for(int i = 0; i < 62; i++){
            name += "a";
        }
        characteristic.setName(name);
        when(repository.save(characteristic)).thenThrow(InvalidEntityException.class);
        //when
        Assertions.assertThrows(InvalidEntityException.class, ()->{
            service.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_format_is_out_of_range_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        String format = "";
        
        for(int i = 0; i < 17; i++){
            format += "a";
        }
        characteristic.setFormat(format);
        when(repository.save(characteristic)).thenThrow(InvalidEntityException.class);
        //when
        Assertions.assertThrows(InvalidEntityException.class, ()->{
            service.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_hasnt_format_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        characteristic.setFormat(null);
        when(repository.save(characteristic)).thenThrow(InvalidEntityException.class);
        //then
        Assertions.assertThrows(InvalidEntityException.class, ()->{
            service.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_hasnt_type_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        characteristic.setType(null);
        when(repository.save(characteristic)).thenThrow(InvalidEntityException.class);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_hasnt_valueInterpreter_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        characteristic.setValueInterpreter(null);
        when(repository.save(characteristic)).thenThrow(InvalidEntityException.class);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_hasnt_description_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        characteristic.setDescription(null);
        when(repository.save(characteristic)).thenThrow(InvalidEntityException.class);
        //then
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_description_is_out_of_range_when_saving_then_should_fail() throws InvalidEntityException{
        //given
        String description = "";
        
        for(int i = 0; i < 105; i++){
            description += "a";
        }
        characteristic.setDescription(description);
        when(repository.save(characteristic)).thenThrow(InvalidEntityException.class);
        //when
        Assertions.assertThrows(ConstraintViolationException.class, ()->{
            repository.save(characteristic);
        });
    }
    
    @Test
    public void given_characteristic_is_valid_when_saving_then_should_pass() throws InvalidEntityException{
        //given
        //when
        when(repository.save(characteristic)).thenReturn(saved);
        Characteristic result = repository.save(characteristic);
        //then
        Assertions.assertNotNull(result);
    }
}
