package com.mycompany.app.controller;

import com.mycompany.app.exceptions.EntityNotFoundException;
import com.mycompany.app.exceptions.EntityRemoveFailedException;
import com.mycompany.app.exceptions.InvalidEntityException;
import com.mycompany.app.exceptions.NoSingleResultException;
import com.mycompany.app.model.Category;
import com.mycompany.app.model.Product;
import com.mycompany.app.services.CategoryService;
import com.mycompany.app.services.CharacteristicService;
import com.mycompany.app.services.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired 
    private ProductService serviceProduct;
    
    @Autowired
    private CategoryService serviceCategory;
    
    @Autowired
    private CharacteristicService serviceCharacteristic;
     
    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Long saveProduct(@RequestBody Product product) throws InvalidEntityException{
        return serviceProduct.save(product);
    }
    
    @GetMapping("/product")
    public Product getProductById(@RequestParam Long id){
        return serviceProduct.getProductById(id);
    }
    
    @GetMapping("/product")
    public List<Product> findAllProducts(){
        return serviceProduct.findAll();
    }
    
    @DeleteMapping("/product")
    public void deleteProduct(Long id){// also RequestParam?
        serviceProduct.delete(id);
    }
    
    @PatchMapping("/product")
    public void updateProduct(Product product){// also RequestBody?
        serviceProduct.update(product);
    }
    
    @PostMapping(value = "/category", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long saveCategory(Category category) throws InvalidEntityException{
        return serviceCategory.save(category);
    }
    
    @GetMapping("/category")//arroja dos excepciones :'v
    public Category getCategoryById(Long id) throws EntityNotFoundException, NoSingleResultException{
        return serviceCategory.findById(id);
    }
    
    @DeleteMapping("/category")
    public void deleteCategory(Long id) throws EntityRemoveFailedException{// also RequestParam?
        serviceCategory.delete(id);
    }
    
    @PatchMapping("/category")
    public void updateCategory(Category category) throws InvalidEntityException{// also RequestBody?
        serviceCategory.update(category);
    }
}
