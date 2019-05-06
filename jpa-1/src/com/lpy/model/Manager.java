package com.lpy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "JPA_MANAGERS")
@Entity
public class Manager {

    @GeneratedValue
    @Id
    private Integer id;

    @Column(name = "MGR_NAME")
    private String mgrName;

    //对于不维护关联关系, 没有外键的一方, 使用 @OneToOne 来进行映射, 建议设置 mappedBy=true
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "mgr")
    private Department dept;
}
