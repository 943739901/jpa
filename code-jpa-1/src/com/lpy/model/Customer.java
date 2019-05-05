package com.lpy.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NamedQuery(name="testNamedQuery", query="FROM Customer c WHERE c.id = ?")
@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
//    @JoinColumn(name = "customer_id")
    private Set<Order> orders = new HashSet<>();

    public Customer(String lastName, int age) {
        super();
        this.lastName = lastName;
        this.age = age;
    }
}
