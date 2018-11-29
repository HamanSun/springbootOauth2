package com.iss.base.config.oauth2.customer;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.iss.base.mapper.UserMapper;
import com.iss.base.model.Role;
import com.iss.base.model.User;

import lombok.extern.slf4j.Slf4j;

@Service("userDetailsService")
@Slf4j
public class CustomerUserDetailService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) {

		User user = userMapper.findByUserName(username);
		log.info("loadByUsername:{}", user.toString());

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.getEnabled(),
				user.getAccountNonExpired(), user.getCredentialsNonExpired(), user.getAccountNonLocked(),
				this.obtainGrantedAuthorities(user));
	}

	/**
	 * 获得登录者所有角色的权限集合.
	 *
	 * @param user
	 * @return
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(User user) {
		Set<Role> roles = user.getRoles();
		log.info("user:{},roles:{}", user.getUsername(), roles);
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());
	}
}
