package com.bmw;

import com.bmw.pojo.MessageKafka;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class MessageKafkaProducer {

    @Inject
    @Channel("messages-out")
    Emitter<Record<Integer, String>> emitter;

    public void sendMessageToKafka(MessageKafka messageKafka) {
        emitter.send(Record.of(messageKafka.id, messageKafka.message));
    }
}
