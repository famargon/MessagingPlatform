package org.fapps.famargon.communications;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SystemUtils.class);
	
	public static String objectToJson(Object object){
		try {
			return ServerInitialization.JSON_MAPPER.writeValueAsString(object);
		} catch (IOException e) {
			LOG.error("",e);
			return "";
		}
	}
	
	public static <T> T jsonToObject(String json, Class<T> clazz){
		try {
			return ServerInitialization.JSON_MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			LOG.error("",e);
			return null;
		}
	}
	
}
