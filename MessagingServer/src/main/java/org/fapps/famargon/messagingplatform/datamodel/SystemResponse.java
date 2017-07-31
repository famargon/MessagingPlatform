package org.fapps.famargon.messagingplatform.datamodel;

import java.time.Instant;
import java.util.List;

public class SystemResponse {

	private String connectionId;
	
	private Instant systemEntryInstant;
	
	private boolean correct;
	private boolean retryable;
	
	private List<String> errors;
	
	private Object entity;

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public Instant getSystemEntryInstant() {
		return systemEntryInstant;
	}

	public void setSystemEntryInstant(Instant systemEntryInstant) {
		this.systemEntryInstant = systemEntryInstant;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public boolean isRetryable() {
		return retryable;
	}

	public void setRetryable(boolean retryable) {
		this.retryable = retryable;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}
	
}
