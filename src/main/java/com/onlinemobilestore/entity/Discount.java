package com.onlinemobilestore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private float percent;
    private boolean isActive;
    private boolean isSpecial;
    private Date expirationDate;
    private Date createDate;
    private Date modifiedDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String description;

}

