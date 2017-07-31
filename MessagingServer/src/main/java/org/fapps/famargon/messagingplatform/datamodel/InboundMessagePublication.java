package org.fapps.famargon.messagingplatform.datamodel;

import java.io.Serializable;

public class InboundMessagePublication implements Serializable{

	private static final long serialVersionUID = -7630542625024131446L;

	private String user;
	
	private String callbackUrl;
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	
}
