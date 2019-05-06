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
 * ʹ��tostring ���ڶ���以�����ã��ᵼ��StackOverflowError
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
     * fetch Ĭ��Ϊeager
     * ʹ�� @OneToOne ��ӳ�� 1-1 ������ϵ��
     * ����Ҫ�ڵ�ǰ���ݱ��������������Ҫʹ�� @JoinColumn ������ӳ��. ע��, 1-1 ������ϵ, ������Ҫ��� unique=true
     */
    @JoinColumn(name = "MGR_ID", unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Manager mgr;
}
