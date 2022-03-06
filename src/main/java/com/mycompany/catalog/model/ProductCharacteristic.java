package com.mycompany.catalog.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "prototype")
@Entity
@Component
@Table(name = "assinged_characteristic_product")
public class ProductCharacteristic implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private Product product;
    
    @Id
    @ManyToOne
    @JoinColumn
    private Characteristic characteristic;
    
    @Column(name = "_value", length = 50, nullable = false)
    private String assocValue;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public String getAssocValue() {
        return assocValue;
    }

    public void setAssocValue(String assocValue) {
        this.assocValue = assocValue;
    }
}
