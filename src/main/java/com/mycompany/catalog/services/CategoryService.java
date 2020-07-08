package com.mycompany.catalog.services;

import com.mycompany.catalog.exceptions.EntityNotFoundException;
import com.mycompany.catalog.exceptions.EntityRemoveFailedException;
import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.exceptions.NoSingleResultException;
import com.mycompany.catalog.model.Category;
import com.mycompany.catalog.repository.CategoryRepository;
import com.mycompany.catalog.util.ServiceErrors;
import java.util.List;
import java.util.Optional;
import javax.persistence.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;
    
    public Long save(Category category) throws InvalidEntityException{
        Category persisted = repository.save(category);
        return persisted.getId();
    }
    
    public void delete(Long id) throws EntityRemoveFailedException{
        repository.delete(id);
    }
    
    public Category findById(Long id) throws EntityNotFoundException, NoSingleResultException{
        try{
            Optional<Category> result = repository.findById(id);
            if(result.isPresent()){
                return result.get();
            }
            else{
                throw new EntityNotFoundException(ServiceErrors.CATEGORY_NOT_FOUND + id);
            }
        }catch(NonUniqueResultException e){
            throw new NoSingleResultException(ServiceErrors.MORE_THAN_ONE_BY_ID + id);
        }
    }
    
    public List<Category> findAll(){
        return repository.findAll();
    }
    
    public Category findSubTreeById(Long id) throws EntityNotFoundException, NoSingleResultException{
        return repository.findSubTreeById(id);
    }
    
    public void update(Category category) throws InvalidEntityException{
        repository.update(category);
    }
}
