package com.mycompany.catalog.services;

import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.model.Product;
import com.mycompany.catalog.repository.ProductRepository;
import com.mycompany.catalog.util.ServiceErrors;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

@Service
public class ProductService{
    @Autowired
    private ProductRepository repository;
    
    public Long save(Product product) throws InvalidEntityException{
        try{
            return repository.save(product).getId();
        }catch(InvalidDataAccessApiUsageException e){
            throw new InvalidEntityException(ServiceErrors.ID_NOT_ASSIGNABLE);
        }
    }
    
    public Product getProductById(Long id) throws NoSuchElementException{
        if(repository.findById(id).isPresent()){
            return repository.findById(id).get();
        }else{
            throw new NoSuchElementException(ServiceErrors.PRODUCT_NOT_FOUND + id);
        }
    }
    
    public List<Product> findByCategory(Long idCategory){
        return repository.findByCategory(idCategory);
    }
    
    public List<Product> findByCharacteristic(List<Integer> idCharacteristic, List<String> valuesCharacteristic){
        return repository.findByCharacteristic(idCharacteristic, valuesCharacteristic);
    }
    
    public List<Product> findAll(){
        return repository.findAll();
    }
    
    public void update(Product product){
        repository.getOne(product.getId()).setName(product.getName());
        repository.getOne(product.getId()).setDescription(product.getDescription());
        repository.getOne(product.getId()).setCategory(product.getCategory());
        repository.getOne(product.getId()).setCharacteristics(product.getCharacteristics());
    }
    
    public void delete(Long id){
        repository.deleteById(id);
    }
}
