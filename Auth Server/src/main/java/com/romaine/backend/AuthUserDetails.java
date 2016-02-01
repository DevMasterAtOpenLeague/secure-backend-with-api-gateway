package com.romaine.backend;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUserDetails implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8369413193500426188L;
	
	private User user;
	
	public AuthUserDetails(User user) {
		this.user = user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) user.getProperty("authorites");
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return (boolean) user.getProperty("account-not-expired");
	}

	@Override
	public boolean isAccountNonLocked() {
		return (boolean) user.getProperty("account-not-locked");
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return (boolean) user.getProperty("credentials-not-expired");
	}

	@Override
	public boolean isEnabled() {
		return (boolean) user.getProperty("account-enabled");
	}

}
