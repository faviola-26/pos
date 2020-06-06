/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.catalog.util;

/**
 *
 * @author david.martinez
 */
public class URL {
    
    public URL(int port) {
        this.port = port;
    }
    
    private final int port;
    private final String HOST = "http://localhost:";
    private static final String CATALOG = "/catalog";
    private static final String INVENTORY = "/inventory";
    private static final String PRODUCT = "/product";
    
    
    public String getSaveProduct() {
        return HOST + port + CATALOG + PRODUCT;
    }
   
}
