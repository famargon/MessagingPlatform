package org.fapps.famargon.communications.configuration;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;

@Configuration
@IntegrationComponentScan
public class MQTTConfiguration {

	@Autowired
	private CommunicationsSystemProperties properties;

	private String getMqttUri(){
		return "tcp://"+properties.mqttHost+":"+properties.mqttPort;
	}
	
	@Bean
	public MqttClient mqttClient() throws MqttException {
	    MqttClient mqttClient = new MqttClient(getMqttUri(), properties.mqttClientId);
	    MqttConnectOptions connOptions = new MqttConnectOptions();
	    connOptions.setUserName(properties.adminUsername);
	    connOptions.setPassword(properties.adminPassword.toCharArray());
	    mqttClient.connect(connOptions);
	    return mqttClient;
	}
}
