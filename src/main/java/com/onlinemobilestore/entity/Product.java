package com.onlinemobilestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;
    private int quantity;
    private String description;
    private boolean state;
    private Date createDate;
    private Date modifiedDate;
    private int percentDiscount;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "trademark_id")
    private Trademark trademark;

    @OneToMany(mappedBy = "product") @JsonIgnore
    private List<Image> images;

    @OneToMany(mappedBy = "product") @JsonIgnore
    private List<Preview> previews;

    @OneToMany(mappedBy = "product") @JsonIgnore
    private List<Discount> discounts;

}

