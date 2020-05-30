package com.mycompany.catalog.repository;

import com.mycompany.catalog.exceptions.EntityRemoveFailedException;
import com.mycompany.catalog.exceptions.InvalidEntityException;
import com.mycompany.catalog.exceptions.NoSingleResultException;
import com.mycompany.catalog.model.Characteristic;
import com.mycompany.catalog.util.ServiceErrors;
import com.mycompany.catalog.util.RepositoryErrors;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class CharacteristicRepository {
    @Autowired
    private EntityManagerFactory factory;
    
    @Autowired
    private ApplicationContext context;
    
    public Characteristic save(Characteristic characteristic) throws InvalidEntityException{
        EntityManager manager = factory.createEntityManager();
        
        try{
            manager.getTransaction().begin();
            manager.persist(characteristic);
            manager.getTransaction().commit();
            return characteristic;
        }catch(EntityExistsException e){
            manager.getTransaction().rollback();
            throw new InvalidEntityException(RepositoryErrors.ENTITY_EXISTS);
        }finally{
            manager.close();
        } 
    }
    public Optional<Characteristic> 
    findByName(String name) throws NoSingleResultException{
        EntityManager manager = factory.createEntityManager();
        
        var query = manager.createQuery("select C from Characteristic C where C.name = :name");
        query.setParameter("name", name);
        try{
            Characteristic found = (Characteristic)query.getSingleResult();
            return Optional.of(found);
        }catch(NoResultException e){
            return Optional.empty();
        }catch(NonUniqueResultException e){
            throw new NoSingleResultException(ServiceErrors.MORE_THAN_ONE_BY_NAME + name);
        }
        
    }
    
    public List<Characteristic> findAll(){
        EntityManager manager = factory.createEntityManager();
        
        var query = manager.createQuery("select C from Characteristic C");
        List<Characteristic> found = query.getResultList();
        return found;
    }
    
    public void delete(Integer id) throws EntityRemoveFailedException{
        EntityManager manager = factory.createEntityManager();
        
        try{
            manager.getTransaction().begin();
            manager.remove(manager.find(Characteristic.class, id));
            manager.getTransaction().commit();
        }catch(IllegalArgumentException e){
            throw new EntityRemoveFailedException(RepositoryErrors.ENTITY_REMOVE_FAILED + id);
        }
    }
    
    public void update(Characteristic characteristic) throws InvalidEntityException{
        EntityManager manager = factory.createEntityManager();
        
        try{
            manager.getTransaction().begin();
            manager.merge(characteristic);
            manager.getTransaction().commit();
        }catch(ConstraintViolationException e){
            throw new InvalidEntityException(e.getMessage());
        }
    }
}
