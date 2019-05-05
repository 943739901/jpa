package com.lpy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="JPA_ITEMS")
@Entity
public class Item {

	@GeneratedValue
	@Id
	private Integer id;

	@Column(name="ITEM_NAME")
	private String itemName;

	//使用 @ManyToMany 注解来映射多对多关联关系
	//使用 @JoinTable 来映射中间表
	//1. name 指向中间表的名字
	//2. joinColumns 映射当前类所在的表在中间表中的外键
	//2.1 name 指定外键列的列名
	//2.2 referencedColumnName 指定外键列关联当前表的哪一列
	//3. inverseJoinColumns 映射关联的类所在中间表的外键
	@JoinTable(name="ITEM_CATEGORY",
			joinColumns={@JoinColumn(name="ITEM_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="CATEGORY_ID", referencedColumnName="ID")})
	@ManyToMany
	private Set<Category> categories = new HashSet<>();
}
