package org.fapps.famargon.communications.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hazelcast.config.Config;
import com.hazelcast.config.QueueConfig;

@Configuration
@EnableScheduling
public class HazelcastConfiguration {

	public static final String INBOUND_MESSAGES_QUEUE = "inboundMessages";

	@Bean
	public Config configHazelcast(){
		Config config = new Config("communications");
		QueueConfig queueConfig = new QueueConfig(INBOUND_MESSAGES_QUEUE);
		queueConfig.setStatisticsEnabled(true);
		config.addQueueConfig(queueConfig);
		return config;
	}
	
}
