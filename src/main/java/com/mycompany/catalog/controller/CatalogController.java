package com.mycompany.catalog.controller;

import com.mycompany.catalog.exceptions.*;
import com.mycompany.catalog.model.*;
import com.mycompany.catalog.services.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    
    @Autowired 
    private ProductService serviceProduct;
    
    @Autowired
    private CategoryService serviceCategory;
    
    @Autowired
    private CharacteristicService serviceCharacteristic;
     
    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, 
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Long saveProduct(@RequestBody Product product) throws InvalidEntityException {
        return serviceProduct.save(product);
    }
    
    @GetMapping("/find/product")
    public @ResponseBody Product getProductById(@RequestParam Long id){
        return serviceProduct.getProductById(id);
    }
    
    @GetMapping("/find/products")
    public @ResponseBody List<Product> findAllProducts(){
        return serviceProduct.findAll();
    }
    
    @GetMapping("/find/product/category")
    public @ResponseBody List<Product> findProductByCategory(@RequestParam Long idCategory){
        return serviceProduct.findByCategory(idCategory);
    }
    
    @GetMapping("/find/product/characteristic")
    public @ResponseBody List<Product> findProductByCharacteristic(@RequestParam List<Integer> idCharacteristic, @RequestParam List<String> valuesCharacteristic){
        return serviceProduct.findByCharacteristic(idCharacteristic, valuesCharacteristic);
    }
    
    @DeleteMapping("/product")
    public void deleteProduct(@RequestParam Long id){
        serviceProduct.delete(id);
    }
    
    @PutMapping("/product")
    public void updateProduct(@RequestBody Product product){
        serviceProduct.update(product);
    }
    
    @PostMapping(value = "/category", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long saveCategory(Category category) throws InvalidEntityException{
        return serviceCategory.save(category);
    }
    
    @GetMapping("/category")
    public @ResponseBody Category getCategoryById(@RequestParam Long id) throws EntityNotFoundException, NoSingleResultException{
        return serviceCategory.findById(id);
    }
    
    @GetMapping("/find/categories")
    public @ResponseBody List<Category> findAllCategories(){
        return serviceCategory.findAll();
    }
    
    @GetMapping("/find/category/subTree")
    public @ResponseBody Category findCategorySubTreeById(@RequestParam Long id) throws EntityNotFoundException, NoSingleResultException{
        return serviceCategory.findSubTreeById(id);
    }
    
    @DeleteMapping("/category")
    public void deleteCategory(@RequestParam Long id) throws EntityRemoveFailedException{
        serviceCategory.delete(id);
    }
    
    @PutMapping("/category")
    public void updateCategory(@RequestBody Category category) throws InvalidEntityException{
        serviceCategory.update(category);
    }
    
    @PostMapping(value = "/characteristic", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer save(@RequestBody Characteristic characteristic) throws InvalidEntityException{
        return serviceCharacteristic.save(characteristic);
    }
    
    @GetMapping("/find/characteristic")
    public @ResponseBody Characteristic findByName(@RequestParam String name) throws EntityNotFoundException, NoSingleResultException{
        return serviceCharacteristic.findByName(name);
    }
    
    @GetMapping("/find/characteristics")
    public @ResponseBody List<Characteristic> findAll(){
        return serviceCharacteristic.findAll();
    }
    
    @DeleteMapping("/characteristic")
    public void delete(@RequestParam Integer id) throws EntityRemoveFailedException{
        serviceCharacteristic.delete(id);
    }
    
    @PutMapping("/characteristic")
    public void update(@RequestBody Characteristic characteristic) throws InvalidEntityException{
        serviceCharacteristic.update(characteristic);
    }
}
