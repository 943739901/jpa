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

    //���ڲ�ά��������ϵ, û�������һ��, ʹ�� @OneToOne ������ӳ��, �������� mappedBy=true
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "mgr")
    private Department dept;
}
