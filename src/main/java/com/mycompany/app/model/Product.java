package com.mycompany.app.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "product", catalog = "POS")
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(message = "Id not assignable")
    private Long id;
    
    @Column(length = 50, nullable = false)
    @Min(value = 2, message = "Name should have at least 2 characters")
    @Max(value = 50, message = "Schema can't save this name, it's too long")
    private String name;
    
    @Column(length = 500, nullable = false)
    @Max(value = 500, message = "Schema can't save this description, it's too long")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
