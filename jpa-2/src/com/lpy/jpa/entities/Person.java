package com.lpy.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="JPA_PERSONS")
@Entity
public class Person {

	@GeneratedValue
	@Id
	private Integer id;

	@Column(name="LAST_NAME")
	private String lastName;

	private String email;
	private int age;
}
