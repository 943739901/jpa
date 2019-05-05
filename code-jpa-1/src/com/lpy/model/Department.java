package com.lpy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 使用tostring 由于对象间互相引用，会导致StackOverflowError
 */
@Getter
@Setter
//@ToString
@Table(name = "JPA_DEPARTMENTS")
@Entity
public class Department {

    @GeneratedValue
    @Id
    private Integer id;

    @Column(name = "DEPT_NAME")
    private String deptName;

    /**
     * fetch 默认为eager
     * 使用 @OneToOne 来映射 1-1 关联关系。
     * 若需要在当前数据表中添加主键则需要使用 @JoinColumn 来进行映射. 注意, 1-1 关联关系, 所以需要添加 unique=true
     */
    @JoinColumn(name = "MGR_ID", unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Manager mgr;
}
