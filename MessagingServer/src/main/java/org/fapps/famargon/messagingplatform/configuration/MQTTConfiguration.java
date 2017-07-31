package org.fapps.famargon.messagingplatform.configuration;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.fapps.famargon.messagingplatform.ServerInitialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.integration.annotation.IntegrationComponentScan;

@Configuration
public class MQTTConfiguration {

	@Autowired
	private CommunicationsSystemProperties properties;

	private String getMqttUri(){
		return "tcp://"+properties.mqttHost+":"+properties.mqttPort;
	}
	
	@Bean
	public ServerInitialization ServerInitialization(){
		return new ServerInitialization();
	}
	
	@Bean
	@DependsOn("ServerInitialization")
	public MqttClient mqttClient() throws MqttException {
	    MqttClient mqttClient = new MqttClient(getMqttUri(), properties.mqttClientId);
	    MqttConnectOptions connOptions = new MqttConnectOptions();
	    connOptions.setUserName(properties.adminUsername);
	    connOptions.setPassword(properties.adminPassword.toCharArray());
	    mqttClient.connect(connOptions);
	    return mqttClient;
	}
}
