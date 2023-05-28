package com.bmw;

import com.bmw.pojo.MessageKafka;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageKafkaResource {

    @Inject
    MessageKafkaProducer producer;

    @POST
    public Response send(MessageKafka messageKafka) {
        producer.sendMessageToKafka(messageKafka);
        // Return an 202 - Accepted response.
        return Response.accepted().build();
    }
}
