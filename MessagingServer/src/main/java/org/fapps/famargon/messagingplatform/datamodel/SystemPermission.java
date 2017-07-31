package org.fapps.famargon.messagingplatform.datamodel;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class SystemPermission implements Serializable, GrantedAuthority{

	private static final long serialVersionUID = -3041466106173002920L;
	private String type;
	private String channel;
	
	public SystemPermission(String type, String channel){
		this.type = type;
		this.channel = channel;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String getAuthority() {
		return this.type+"="+this.channel;
	}
	
}
