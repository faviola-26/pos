package com.mycompany.catalog.util;

import com.mycompany.catalog.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class URL {
    
    public URL(int port) {
        this.port = port;
    }
    
    private final int port;
    private final String HOST = "http://localhost:";
    private static final String CATALOG = "/catalog";
    private static final String PRODUCT = "/product";
    private static final String CATEGORY = "/category";
    private static final String CHARACTERISTIC = "/characteristic";
    private static final String FIND = "/find";
    private static final String PARAM_ID = "?id=";
    
    public String getSaveProduct() {
        return HOST + port + CATALOG + PRODUCT;
    }
    
    public String getFindProductById(Long id) {
        return HOST + port + CATALOG + FIND + PRODUCT + PARAM_ID + (id == null ? " " : Long.toString(id));
    }
    
    public String getFindProductByCategory(Long id){
        return HOST + port + CATALOG + FIND +  PRODUCT + CATEGORY + PARAM_ID + (id == null ? " " : Long.toString(id));
    }
    
    public String getFindProductByCharacteristics(List<Integer> ids, List<String> values){
        String stringValues = String.join(", ", values);
        String stringIds = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        
        return HOST + port + CATALOG + FIND +  PRODUCT + CHARACTERISTIC + "?idCharacteristic=" + (ids == null && ids.isEmpty() ? " " : stringIds) + 
                "&valuesCharacteristic=" + (values == null && values.isEmpty() ? " " : stringValues);
    }
    
    public String getUpdateProduct(){
        return HOST + port + CATALOG + PRODUCT;
    }
    
    public String getDeleteProduct(Long id){
        return HOST + port + CATALOG + PRODUCT + PARAM_ID + (id == null ? " " : Long.toString(id));
    }
    
    public String getSaveCategory(){
        return HOST + port + CATALOG + CATEGORY;
    }
    
    public String getFindCategoryById(Long id){
        return HOST + port + CATALOG + CATEGORY + FIND + PARAM_ID + (id == null ? " " : Long.toString(id));
    }
}
