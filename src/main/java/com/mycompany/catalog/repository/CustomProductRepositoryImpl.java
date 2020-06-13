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
    public void update(Product product) {
        EntityManager manager = factory.createEntityManager();
        
        try{
            manager.getTransaction().begin();
            manager.merge(product);
            manager.getTransaction().commit();
        }catch(ConstraintViolationException e){
            throw new InvalidEntityException(e.getMessage());
        }
    }
    
}
