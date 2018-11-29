package com.iss.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "sys_log")
@Data
public class SysLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private Date createdDate;

    private String ip;

    @Column(name = "method_name")
    private String methodName;

    private String operation;

    private String params;

    private String username;
}