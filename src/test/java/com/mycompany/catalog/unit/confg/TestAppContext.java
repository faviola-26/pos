/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.catalog.unit.confg;

import com.mycompany.catalog.model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author david.martinez
 */
//@Configuration
public class TestAppContext {
    
    @Bean
    @Scope("prototype")
    public Category category() {
        return new Category();
    }
}
