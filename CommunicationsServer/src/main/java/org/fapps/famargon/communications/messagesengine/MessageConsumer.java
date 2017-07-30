package org.fapps.famargon.communications.messagesengine;

import java.io.IOException;

import org.fapps.famargon.communications.ServerInitialization;
import org.fapps.famargon.communications.businesslogic.communications.CommunicationsService;
import org.fapps.famargon.communications.datamodel.SystemMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.IQueue;

public class MessageConsumer implements Runnable{

    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);
	
    private IQueue<String> inbound;
    private MessagesConsumerStopper stopper;
    private CommunicationsService communicationsService;

    
    public MessageConsumer(IQueue<String> inbound, MessagesConsumerStopper stopper, CommunicationsService communicationsService){
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
