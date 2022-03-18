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
    private static final String FIND = "/find";
    private static final String PARAM_ID = "?id=";
    
    public String getSaveProduct() {
        return HOST + port + CATALOG + PRODUCT;
    }
    
    public String getProductById(Long id) {
        return HOST + port + CATALOG + PRODUCT + FIND + PARAM_ID + (id == null ? " " : Long.toString(id));
    }
    
    public String getProductByCategory(long id){
        return HOST + port + CATALOG + PRODUCT + FIND + PARAM_ID + String.valueOf(id);
    }
}
