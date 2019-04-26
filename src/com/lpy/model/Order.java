package com.lpy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name="JPA_ORDERS")
@Entity
public class Order {

	@Id
	@GeneratedValue
	private Integer id;
	private String orderName;

	/**
	 * 配置了@JoinColumn(name = "customer_id") 会自动生成外键列
	 *
	 */
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "customer_id")
	private Customer customer;
}
