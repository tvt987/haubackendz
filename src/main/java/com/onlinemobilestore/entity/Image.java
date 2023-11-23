package com.onlinemobilestore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
