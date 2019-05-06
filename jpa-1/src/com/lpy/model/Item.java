package com.lpy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
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
//@Cacheable
@Table(name="JPA_ITEMS")
@Entity
public class Item {

	@GeneratedValue
	@Id
	private Integer id;

	@Column(name="ITEM_NAME")
	private String itemName;

	//ʹ�� @ManyToMany ע����ӳ���Զ������ϵ
	//ʹ�� @JoinTable ��ӳ���м��
	//1. name ָ���м�������
	//2. joinColumns ӳ�䵱ǰ�����ڵı����м���е����
	//2.1 name ָ������е�����
	//2.2 referencedColumnName ָ������й�����ǰ�����һ��
	//3. inverseJoinColumns ӳ��������������м������
	@JoinTable(name="ITEM_CATEGORY",
			joinColumns={@JoinColumn(name="ITEM_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="CATEGORY_ID", referencedColumnName="ID")})
	@ManyToMany
	private Set<Category> categories = new HashSet<>();
}
