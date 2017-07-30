package org.fapps.famargon.communications.messagesengine;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.fapps.famargon.communications.SystemUtils;
import org.fapps.famargon.communications.datamodel.InboundMessagePublication;
import org.fapps.famargon.communications.datamodel.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class OutboundSystem {

    private static final Logger LOG = LoggerFactory.getLogger(OutboundSystem.class);
	
	@Autowired
	MqttClient client;
	
	@Autowired
	Environment environment;
	
	public void pushMessage(SystemMessage message){
		
		String url = getCallbackUrl(message);
		
		InboundMessagePublication messagePublication = new InboundMessagePublication();
		messagePublication.setUser(message.getDestination());
		messagePublication.setCallbackUrl(url);
		try {
			client.publish("/inbound/"+message.getDestination(), 
								new MqttMessage(SystemUtils.objectToJson(messagePublication).getBytes()));
		} catch (MqttException e) {
			LOG.error("",e);
		}
	}

	private String getCallbackUrl(SystemMessage message) {
		String url = "";
	    String port = environment.getProperty("server.port");
	    try {
			String address = InetAddress.getLocalHost().getHostAddress();
			url = "http://"+address+":"+port+"/messages/receive/"+message.getConnectionId();
		} catch (UnknownHostException e1) {
			LOG.error("",e1);
		}
		return url;
	}
	
}
