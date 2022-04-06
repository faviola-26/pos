package com.mycompany.catalog.services;

import com.mycompany.catalog.exceptions.EntityNotFoundException;
import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.model.Product;
import com.mycompany.catalog.repository.ProductRepository;
import com.mycompany.catalog.util.ServiceErrors;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Long save(Product product) throws InvalidEntityException {
        try {
            return repository.save(product).getId();
        } catch (InvalidDataAccessApiUsageException e) {
            throw new InvalidEntityException(ServiceErrors.ID_NOT_ASSIGNABLE);
        }
    }

    public Product getProductById(Long id) throws EntityNotFoundException {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new EntityNotFoundException(ServiceErrors.PRODUCT_NOT_FOUND + id);
        }
    }

     public List<Product> findByCategory(Long idCategory) throws EntityNotFoundException{
        if (!repository.findByCategory(idCategory).isEmpty()) {
            return repository.findByCategory(idCategory);
        } else {
            throw new EntityNotFoundException(ServiceErrors.PRODUCT_NOT_FOUND + idCategory);
        }
        
    }

    public List<Product> findByCharacteristic(List<Integer> idCharacteristic, List<String> valuesCharacteristic) {
        return repository.findByCharacteristic(idCharacteristic, valuesCharacteristic);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
