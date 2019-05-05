package com.lpy.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 单项多对一是使用最多的
 *
 * @author lipengyu
 * @date 2019/4/28 9:58
 */
@Setter
@Getter
@Table(name = "JPA_ORDERS")
@Entity
public class Order {

    @Id
    @GeneratedValue
    private Integer id;
    private String orderName;

    /**
     * 配置了@JoinColumn(name = "customer_id") 会自动生成外键列
     *
     * 一对多或多对一的关系就比如习大大和我们之间的关系
     * fetch 的默认值合理之处在于
     * @ManyToOne 默认fetch是EAGER 当查询我们时，可直接查询习大大
     * @OneToMany 默认fetch是LAZY 当查询习大大时，则不能直接查询出我们所有人
     */
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}
