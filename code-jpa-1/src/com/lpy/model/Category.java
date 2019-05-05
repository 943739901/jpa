package com.lpy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="JPA_CATEGORIES")
@Entity
public class Category {

	@GeneratedValue
	@Id
	private Integer id;

	@Column(name="CATEGORY_NAME")
	private String categoryName;

	@ManyToMany(mappedBy="categories")
	private Set<Item> items = new HashSet<>();
}
