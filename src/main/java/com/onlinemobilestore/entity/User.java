package com.onlinemobilestore.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlinemobilestore.dto.UserRegister;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "user")
public class User {
    public User() {
    }
    public User(UserRegister userRegister) {
        this.email = userRegister.getEmail();
        this.password = userRegister.getPassword();
        this.fullName = userRegister.getFullName();
        this.phoneNumber = userRegister.getPhoneNumber();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phoneNumber;
    private String address;
    private String fullName;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthday;
    private String email;
    private String password;
    private String avatar;
    private String roles = "USER";

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createDate;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(mappedBy = "user") @JsonIgnore

    private List<UserPayment> userPayments;

    @OneToMany(mappedBy = "user") @JsonIgnore
    private List<Order> orders;

    @OneToMany(mappedBy = "user") @JsonIgnore
    private List<Preview> previews;

    public User(int id, String fullName, String avatar, String address, String email, String password, String roles, String phoneNumber, List<Order> orders) {
    }
}
