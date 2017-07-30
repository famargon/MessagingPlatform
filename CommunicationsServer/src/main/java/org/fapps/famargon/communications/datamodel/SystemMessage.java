package org.fapps.famargon.communications.datamodel;

import java.io.Serializable;
import java.time.Instant;

import org.fapps.famargon.communications.datamodel.dto.SystemEntity;

public class SystemMessage extends SystemEntity implements Serializable {

	private static final long serialVersionUID = -3793347476757756459L;
	
	private String source;
	private String destination;
	
	private String contentType;
	private byte[] content;
	
	private Instant systemEntryInstant;
	private Instant sendingInstant;
	private Instant receivingInstant;
	
	private String connectionId;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public Instant getSystemEntryInstant() {
		return systemEntryInstant;
	}
	public void setSystemEntryInstant(Instant systemEntryInstant) {
		this.systemEntryInstant = systemEntryInstant;
	}
	public Instant getSendingInstant() {
		return sendingInstant;
	}
	public void setSendingInstant(Instant sendingInstant) {
		this.sendingInstant = sendingInstant;
	}
	public Instant getReceivingInstant() {
		return receivingInstant;
	}
	public void setReceivingInstant(Instant receivingInstant) {
		this.receivingInstant = receivingInstant;
	}
	public String getConnectionId() {
		return connectionId;
	}
	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
	
}

