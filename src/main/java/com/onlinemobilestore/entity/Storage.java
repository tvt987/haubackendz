package com.onlinemobilestore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "storage")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int randomAccessMemoryValue;
    private String randomAccessMemoryUnit;
    private int readOnlyMemoryValue;
    private String readOnlyMemoryUnit;

}

