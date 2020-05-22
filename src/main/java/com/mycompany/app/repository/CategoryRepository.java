package com.mycompany.app.repository;

import com.mycompany.app.exceptions.EntityNotFoundException;
import com.mycompany.app.exceptions.EntityRemoveFailedException;
import com.mycompany.app.exceptions.InvalidEntityException;
import com.mycompany.app.exceptions.NoSingleResultException;
import com.mycompany.app.model.Category;
import com.mycompany.app.util.ServiceErrors;
import com.mycompany.app.util.RepositoryErrors;
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
public class CategoryRepository{
    @Autowired
    private EntityManagerFactory factory;
    
    @Autowired
    private ApplicationContext context;
    /**
     * Category instance will be persisted if no schema constrains are violated,
     * the transaction will be handled by 
     * the method and if unsuccesful it will be rollbacked
     * @param category to be linked to a product
     * @return a new instance of Category if persistence succesful if fails an 
     * exception will be thrown
     * @throws InvalidEntityException when category already exists
     *         DataIntegrityViolationException when a constrain is violated
    */
    public Category save(Category category) throws InvalidEntityException{
        Category instance;
        EntityManager manager = factory.createEntityManager();
        
        try{
            manager.getTransaction().begin();
            manager.persist(category);
            manager.getTransaction().commit();
            instance = context.getBean(Category.class);
            instance.initialize(category);
            return instance;
        }catch(EntityExistsException e){
            manager.getTransaction().rollback();
            throw new InvalidEntityException(RepositoryErrors.ENTITY_EXISTS);
        }catch(ConstraintViolationException e){
            throw new InvalidEntityException(e.getMessage()); 
        }
        finally{
            manager.close();
        }
    }
    
    public Optional<Category> findById(Long id) throws NonUniqueResultException{
        EntityManager manager = factory.createEntityManager();
        
        var query = manager.createQuery("select C from Category C where C.id = :id");
        query.setParameter("id", id);
        try{
            Category found = (Category)query.getSingleResult();
            return Optional.of(found);
        }catch(NoResultException e){
            return Optional.empty();
        }
    }
    
    public List<Category> findAll() throws PersistenceException{
        EntityManager manager = factory.createEntityManager();
        
        var query = manager.createQuery("select C from Category C");
        List<Category> found = query.getResultList();
        return found;
    }
    
    public Category findSubTreeById(Long parentId) throws EntityNotFoundException, NoSingleResultException{
        try{
            Optional<Category> category = this.findById(parentId);
            if(category.isPresent()){
                Category result = category.get();
                initialize(result);
                return result;
            }else{
                throw new EntityNotFoundException(RepositoryErrors.ENTITY_NOT_FOUND_BY_ID + parentId);
            }
        }catch(NonUniqueResultException e){
            throw new NoSingleResultException(ServiceErrors.MORE_THAN_ONE_PARENT + parentId);
        }
    }
    
    public void initialize(Category parent){
        for(Category child : parent.getSubCategories()){
            if(child.getSubCategories() != null){
                initialize(child);
            }
        }
    }
    
    /**
    * Looks for the category by the id if found it will be removed else it will
    * throw a EntityRemoveFailedException
    * @param id of a Category instance
    * @throws EntityRemoveFailedException
    */
    public void delete(Long id) throws EntityRemoveFailedException{
        EntityManager manager = factory.createEntityManager();
        Category found;
        
        try{
            manager.getTransaction().begin();
            found = manager.find(Category.class, id);
            if(found == null){
                throw new EntityRemoveFailedException(RepositoryErrors.ENTITY_NOT_FOUND_BY_NAME);
            }else{
                manager.remove(found);
                manager.getTransaction().commit();
            }
        }catch(IllegalArgumentException e){
            manager.getTransaction().rollback();
            throw new EntityRemoveFailedException(RepositoryErrors.ENTITY_NOT_FOUND_BY_NAME);
        }finally{
            manager.close();
        }
    }
    
    public void update(Category category) throws InvalidEntityException{
        EntityManager manager = factory.createEntityManager();
        
        try{
            manager.getTransaction().begin();
            manager.merge(category);
            manager.getTransaction().commit();
        }catch(ConstraintViolationException e){
            throw new InvalidEntityException(e.getMessage());
        }
    }
}
