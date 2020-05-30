package com.mycompany.catalog.repository;

import com.mycompany.catalog.model.Product;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
@Qualifier(value = "productRepository")
public interface ProductRepository extends JpaRepository<Product, Long>{
    @Query("select P from Product P "
            + "join fetch Category C on C.id = P.category.id "
            + "where C.id = ?1")
    public List<Product> findByCategory(Long categoryId);
    
    @Query("select P from Product P "
            + "join ProductCharacteristic PC on PC.product.id = P.id "
            + "where PC.characteristic.id in :ids "
            + "and PC.assocValue in :values ")
    public List<Product> findByCharacteristic(@Param("ids") List<Integer> ids, @Param("values") List<String> values);
}
