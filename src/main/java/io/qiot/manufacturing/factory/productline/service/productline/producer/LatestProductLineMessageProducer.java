/**
 * 
 */
package io.qiot.manufacturing.factory.productline.service.productline.producer;

import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.qiot.manufacturing.commons.domain.productionvalidation.ValidationResponseDTO;
import io.qiot.manufacturing.commons.util.producer.ProductLineReplyToQueueNameProducer;
import io.qiot.manufacturing.factory.productline.domain.event.SendLatestProductLineEventDTO;
import io.quarkus.runtime.StartupEvent;

/**
 * @author andreabattaglia
 *
 */
@ApplicationScoped
public class LatestProductLineMessageProducer {

    @Inject
    Logger LOGGER;

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    ObjectMapper MAPPER;

    @Inject
    ProductLineReplyToQueueNameProducer replyToQueueNameProducer;

    private JMSContext context;

    private JMSProducer producer;

    // @PostConstruct
    // void init() {
    void init(@Observes StartupEvent ev) {
        LOGGER.info("Bootstrapping validation event producer...");
        if (Objects.nonNull(context))
            context.close();
        context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);

        producer = context.createProducer();
        LOGGER.info("Bootstrap completed");

    }

    void produce(@Observes SendLatestProductLineEventDTO event) {
        LOGGER.info(
                "Sending out latest product line info to requesting machinery {}",
                event.machineryId);
        Queue replyToQueue = context.createQueue(replyToQueueNameProducer
                .getReplyToQueueName(event.machineryId));

        try {
            String messagePayload = MAPPER
                    .writeValueAsString(event.productLine);

            producer.send(replyToQueue, messagePayload);
        } catch (JsonProcessingException e) {
            LOGGER.error(
                    "The message payload is malformed and the validation request will not be sent: {}",
                    e);
        }

    }
}
