package org.fapps.famargon.communications.messagesengine;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.fapps.famargon.communications.businesslogic.communications.CommunicationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hazelcast.core.IQueue;

@Service
public class MessagesEngine implements MessagesConsumerStarter, MessagesConsumerStopper {

    private static final Logger LOG = LoggerFactory.getLogger(MessagesEngine.class);
	
    private static final int MAX_THREADS = 6;
    private static final int MAX_RECOMMENDED_QUEUE_SIZE = 10;

    @Autowired
    private InboundQueue queue;
    @Autowired
    private CommunicationsService communicationsService;
    
    private boolean isListenerActive;
    private String itemListenerId;
    
    private int threadInstances;
    private ThreadPoolExecutor executor;
    private Instant startingInstant;

	
    private IQueue<String> getQueue(){
    	return queue.getInboundQueue();
    }
    
    @PostConstruct
    public void init(){
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS);

        LOG.info("System initialized");
        
        isListenerActive = true;
        threadInstances = 0;
        itemListenerId = getQueue().addItemListener(new InboundMessageListener(this), false);
    
    }
    
    @Scheduled(fixedDelay=15000)
    public synchronized void checkLoad(){
        if(getQueue().size()>MAX_RECOMMENDED_QUEUE_SIZE && threadInstances<MAX_THREADS){
            LOG.info("Adding 1 thread via checkLoad");
            addConsumer();
            checkLoad();
        }
    }

    @Override
    public synchronized void startConsuming() {
        startingInstant = Instant.now();
        isListenerActive = false;
        getQueue().removeItemListener(itemListenerId);
        itemListenerId = "";
        LOG.info("method startConsuming");
        if(threadInstances==0){
            LOG.info("Starting loop");
            LOG.info("Adding first thread");
            addConsumer();
        }
        checkLoad();
    }

	private void addConsumer() {
		executor.execute(new MessageConsumer(getQueue(), this, communicationsService));
		threadInstances++;
	}

    @Override
    public synchronized void stopConsuming() {
        if(!isListenerActive){
            LOG.info("Stopping loop");
            isListenerActive = true;
            itemListenerId = getQueue().addItemListener(new InboundMessageListener(this), false);        
        }
        threadInstances--;
        long p = Instant.now().toEpochMilli()-startingInstant.toEpochMilli();
        LOG.info("Duration "+p+"ms");
    }
    
}
