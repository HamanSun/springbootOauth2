package com.iss.base.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	private String name;

	private String mobile;

	private Boolean enabled;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "updated_time")
	private Date updatedTime;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Transient
	private Set<Role> roles;

}