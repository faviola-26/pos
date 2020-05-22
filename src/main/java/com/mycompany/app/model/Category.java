package com.mycompany.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Category.class)
    @JsonBackReference(value = "subCategories")
    private List<Category> subCategories;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)    
    @JsonBackReference(value = "ancestor")
    private Category ancestor;
    
    @Column(length = 50, nullable = false)
    @NotNull
    @Size(min = 3, max = 50, message = "")
    private String name;
    
    public void initialize(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.subCategories = category.getSubCategories();
        this.ancestor = category.getAncestor();
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

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getAncestor() {
        return ancestor;
    }

    public void setAncestor(Category ancestor) {
        this.ancestor = ancestor;
    }
    
    
}
