package com.mycompany.app.services;

import com.mycompany.app.exceptions.EntityNotFoundException;
import com.mycompany.app.exceptions.EntityRemoveFailedException;
import com.mycompany.app.exceptions.InvalidEntityException;
import com.mycompany.app.exceptions.NoSingleResultException;
import com.mycompany.app.model.Characteristic;
import com.mycompany.app.repository.CharacteristicRepository;
import java.util.Optional;
import com.mycompany.app.util.ServiceErrors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacteristicService {
    @Autowired
    private CharacteristicRepository repository;
    
    public Integer save(Characteristic characteristic) throws InvalidEntityException{
        Characteristic result = repository.save(characteristic);
        return result.getId();
    }
    
    public Characteristic findByName(String name) throws EntityNotFoundException, NoSingleResultException{
        Optional<Characteristic> result = repository.findByName(name);
        if(result.isPresent()){
            return result.get();
        }else{
            throw new EntityNotFoundException(ServiceErrors.CHARACTERISTIC_NOT_FOUND + name);
        }
    }
    
    public List<Characteristic> findAll(){
        return repository.findAll();
    }
    
    public void delete(Integer id) throws EntityRemoveFailedException{
        repository.delete(id);
    }
    
    public void update(Characteristic characteristic) throws InvalidEntityException{
        repository.update(characteristic);
    }
}
