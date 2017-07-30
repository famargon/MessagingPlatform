package org.fapps.famargon.communications.messagesengine;

import org.fapps.famargon.communications.configuration.HazelcastConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

@Service
public class InboundQueue {

	@Autowired
	HazelcastInstance hazelcast;
	
	public IQueue<String> getInboundQueue(){
		return hazelcast.getQueue(HazelcastConfiguration.INBOUND_MESSAGES_QUEUE);
	}
	
}
