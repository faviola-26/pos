package com.mycompany.catalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "prototype")
@Component
@Entity
@Table(name = "product")
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(message = "Id not assignable")
    private Long id;
    
    @Column(length = 50, nullable = false)
    @Size(min = 2, max = 50,message = "Name should have 2 < n <= 50 char")
    private String name;
    
    @Column(length = 100, nullable = false)
    @Size(min = 0, max = 100,message = "Description should have  n <= 100 char")
    private String description;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Category category;
    
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductCharacteristic> characteristics;
    
    @JsonProperty("id")
    public void unpackNested(Integer id) {
        this.id = Integer.toUnsignedLong(id);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonSerialize(as = ArrayList.class)
    public List<ProductCharacteristic> getCharacteristics() {
        return characteristics;
    }

    @JsonDeserialize(as = ArrayList.class)
    public void setCharacteristics(List<ProductCharacteristic> characteristics) {
        this.characteristics = characteristics;
    }  
}
