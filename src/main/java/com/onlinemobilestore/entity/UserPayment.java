package com.onlinemobilestore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "user_payment")
public class UserPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cardNumber;
    private String bankType;
    private Date expirationDate;
    private int cvv;
    private String cardHolderName;
    private boolean isDefault;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

