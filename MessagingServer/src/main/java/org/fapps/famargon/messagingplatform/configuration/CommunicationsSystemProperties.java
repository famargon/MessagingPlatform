package org.fapps.famargon.messagingplatform.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommunicationsSystemProperties {

	@Value("${communications.admin.username:admin}")
	public String adminUsername;
	
	@Value("${communications.admin.username:admin}")
	public String adminPassword;
	
	@Value("${communications.mqtt.host:localhost}")
	public String mqttHost;

	@Value("${communications.mqtt.port:1883}")
	public String mqttPort;
	
	@Value("${communications.mqtt.clientId:communicationsServer}")
	public String mqttClientId;
}
