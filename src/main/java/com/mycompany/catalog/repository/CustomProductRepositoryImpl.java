package com.mycompany.catalog.repository;

import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.model.Product;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomProductRepositoryImpl implements CustomProductRepository{
    @Autowired 
    private EntityManagerFactory factory;
    
    @Override
    public Product update(Product product) {
        EntityManager manager = factory.createEntityManager();
        Product updated = null;
        
        try{
            manager.getTransaction().begin();
            updated=manager.merge(product);
            manager.getTransaction().commit();
        }catch(ConstraintViolationException e){
            throw new InvalidEntityException(e.getMessage());
        }
        return updated;        
    }
    
}
