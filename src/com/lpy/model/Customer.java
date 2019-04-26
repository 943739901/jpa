package com.lpy.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "JPA_CUTOMERS")
public class Customer {

    @Id
    @GeneratedValue
    private Integer id;

    private String lastName;
    private String email;
    private int age;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Temporal(TemporalType.DATE)
    private Date birth;

    @OneToMany(mappedBy = "customer")
    @JoinColumn(name = "id")
    private Set<Order> orders = new HashSet<>();
}
