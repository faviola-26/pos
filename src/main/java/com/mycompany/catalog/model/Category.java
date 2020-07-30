package com.mycompany.catalog.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "prototype")
@Component
@Entity
@Table(name = "product_category")
public class Category implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(message = "Id not assignable")
    @Column(insertable = false, updatable = false)
    private Long id;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, targetEntity = Category.class)
    private List<Category> subCategories;
    
    @Column(length = 50, nullable = false)
    @NotNull
    @Size(min = 3, max = 50, message = "name out of range 3 <= name <= 50")
    private String name;
    
    public void initialize(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.subCategories = category.getSubCategories();
    }
    
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

    @JsonSerialize(as = ArrayList.class)
    public List<Category> getSubCategories() {
        return subCategories;
    }

    @JsonDeserialize(as = ArrayList.class)
    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }
}