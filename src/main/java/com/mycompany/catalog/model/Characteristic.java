package com.mycompany.catalog.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "prototype")
@Component
@Entity
@Table(name = "product_characteristic")
public class Characteristic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(message = "Id not assignable")
    private Integer id;
    
    @Column(length = 60, nullable = false)
    @Size(min = 3, max = 60, message = "")
    @NotNull
    @NotEmpty
    private String name;
    
    @Column(nullable = false)
    @Min(value = 1)
    @Max(value = 20)
    @NotNull
    private Integer type;
    
    @Column(length = 15, nullable = false)
    @Size(min = 4, max = 15, message = "")
    @NotNull
    @NotEmpty
    private String format;
    
    @Column(nullable = false)
    @Min(value = 1)
    @Max(value = 10)
    @NotNull
    private Integer valueInterpreter;
    
    @Column(length = 100, nullable = false)
    @Size(min = 5, max = 100, message = "")
    @NotNull
    @NotEmpty
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getValueInterpreter() {
        return valueInterpreter;
    }

    public void setValueInterpreter(Integer valueInterpreter) {
        this.valueInterpreter = valueInterpreter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }   
}
