package com.mycompany.catalog.util;

import java.util.Optional;

public class URL {
    
    public URL(int port) {
        this.port = port;
    }
    
    private final int port;
    private final String HOST = "http://localhost:";
    private static final String CATALOG = "/catalog";
    private static final String PRODUCT = "/product";
    private static final String CATEGORY = "/category";
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
    
    public String getUpdateProduct(){
        return HOST + port + CATALOG + PRODUCT;
    }
    
    public String getDeleteProduct(){
        return HOST + port + CATALOG + PRODUCT;
    }
    
    public String getSaveCategory(){
        return HOST + port + CATALOG + CATEGORY;
    }
    
    public String getFindCategoryById(Long id){
        return HOST + port + CATALOG + CATEGORY + FIND + PARAM_ID + (id == null ? " " : Long.toString(id));
    }
}
