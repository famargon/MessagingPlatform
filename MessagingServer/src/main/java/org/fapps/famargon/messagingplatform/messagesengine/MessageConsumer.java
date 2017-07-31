package org.fapps.famargon.messagingplatform.messagesengine;

import java.io.IOException;

import org.fapps.famargon.messagingplatform.ServerInitialization;
import org.fapps.famargon.messagingplatform.businesslogic.communications.MessagingService;
import org.fapps.famargon.messagingplatform.datamodel.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.IQueue;

public class MessageConsumer implements Runnable{

    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);
	
    private IQueue<String> inbound;
    private MessagesConsumerStopper stopper;
    private MessagingService communicationsService;

    
    public MessageConsumer(IQueue<String> inbound, MessagesConsumerStopper stopper, MessagingService communicationsService){
        this.inbound = inbound;
        this.stopper = stopper;
        this.communicationsService = communicationsService;
    }
    
    @Override
    public void run() {
    	LOG.info("Starting thread"+this.hashCode());

        consume();
        
        LOG.info("Finishing thread"+this.hashCode());
        stopper.stopConsuming();
        
    }
    
    private void consume(){
    	try{
            while(!inbound.isEmpty()){
            	
                String jsonMessage = inbound.take();
                SystemMessage  message = ServerInitialization.JSON_MAPPER.readValue(jsonMessage,SystemMessage.class); 
                LOG.info("Received "+message);
            	/**
            	 * Un mensaje no se guarda en bbdd hasta que la cola no lo trata, 
            	 * sin embargo la parte nodejs si que debe asignar systemEntryInstant, 
            	 * para que quede reflejado el momento real de entrada en el sistema
            	 */
                communicationsService.send(message);
                
            }
        }catch(InterruptedException | IOException ie){
            LOG.error("",ie);
        }	
    }

}
