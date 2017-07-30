package org.fapps.famargon.communications.rest.users;

public class RequestObjectUser {

	private String fullname;
	
	private String communicationsUser;
	
	private String pwd;

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
}
