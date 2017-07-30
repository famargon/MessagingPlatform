package org.fapps.famargon.communications.datamodel;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class CommunicationsUser implements Serializable, UserDetails{
	
	private static final long serialVersionUID = -7557577593333286511L;

	public String id;

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}
	
	private String fullname;
	
	private String communicationsUser;
	
	private String pwd;
	
	private String role;
	
	private List<CommunicationsPermission> inboundPermissions;
	
	private List<CommunicationsPermission> outboundPermissions;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCommunicationsUser() {
		return communicationsUser;
	}

	public void setCommunicationsUser(String communicationsUser) {
		this.communicationsUser = communicationsUser;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<CommunicationsPermission> getInboundPermissions() {
		return inboundPermissions;
	}

	public void setInboundPermissions(List<CommunicationsPermission> inboundPermissions) {
		this.inboundPermissions = inboundPermissions;
	}

	public List<CommunicationsPermission> getOutboundPermissions() {
		return outboundPermissions;
	}

	public void setOutboundPermissions(List<CommunicationsPermission> outboundPermissions) {
		this.outboundPermissions = outboundPermissions;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(this.role);
	}

	@Override
	public String getPassword() {
		return this.pwd;
	}

	@Override
	public String getUsername() {
		return this.communicationsUser;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
