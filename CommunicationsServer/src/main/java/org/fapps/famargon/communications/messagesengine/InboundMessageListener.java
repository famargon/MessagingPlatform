package org.fapps.famargon.communications.messagesengine;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;

public class InboundMessageListener implements ItemListener<String>{

	private MessagesConsumerStarter starter;

	public InboundMessageListener(MessagesConsumerStarter starter) {
		this.starter = starter;
	}
	
	@Override
	public void itemAdded(ItemEvent<String> event) {
		starter.startConsuming();
	}

	@Override
	public void itemRemoved(ItemEvent<String> arg0) {
		//Empty
	}

}
