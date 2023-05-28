package com.bmw;

import com.bmw.pojo.MessageKafka;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;


@ApplicationScoped
public class MessageServerConsumer {

    @Inject
    MessageKafkaProducer producer;


    /**
     * Consume messages from bootstrap server
     *
     * @param record
     */

    @Incoming("server-in")
    public void receive(Record<Integer, String> record)  {

        int numbers = extractNumbers(record.value());
        if(evenNumber(numbers)){
            Log.info("Extracted even number :" + extractNumbers(record.value()));
//            forwardMessage(record.value());
            MessageKafka messageKafka = new MessageKafka(numbers, record.value());
            forwardMessageToKafka(messageKafka);
        }
        else {
            System.out.println("Discarded: " + numbers);
        }
    }

    /**
     * Extracts numbers the second last block of the incoming message
     * @param messageVal
     * @return
     */
    public int extractNumbers(String messageVal){
        String[] blockArr = messageVal.split("-");
        String secondToLastBlock = blockArr[blockArr.length - 2];
        String strippedBlock = secondToLastBlock.replaceAll("[^\\d.]", "");
        try {
            return Integer.parseInt(strippedBlock);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + strippedBlock);
            return -1;
        }
    }

    /**
     * Just logs the forwarded message
     * @param message
     */

    public void forwardMessage(String message) {
        System.out.println("Forwarding message: " + message);
    }

    /**
     * Check if num is even
     * @param num
     * @return
     */
    public boolean evenNumber(int num){
        return num % 2 == 0;
    }

    /**
     * forwards message to another topic
     *
     * @param messageKafka
     */
    public void forwardMessageToKafka(MessageKafka messageKafka) {
        producer.sendMessageToKafka(messageKafka);
    }
}
