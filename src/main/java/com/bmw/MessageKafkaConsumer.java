package com.bmw;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import io.smallrye.reactive.messaging.kafka.Record;

@ApplicationScoped
public class MessageKafkaConsumer {

    private final Logger logger = Logger.getLogger(MessageKafkaConsumer.class);

    @Incoming("messages-in")
    public void receive(Record<Integer, String> record) {
        logger.infof("Message Received : %d - %s", record.key(), record.value());
    }
}
