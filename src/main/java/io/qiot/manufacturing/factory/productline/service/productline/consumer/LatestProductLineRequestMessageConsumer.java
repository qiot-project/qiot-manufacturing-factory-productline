package io.qiot.manufacturing.factory.productline.service.productline.consumer;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.qiot.manufacturing.commons.domain.event.BootstrapCompletedEventDTO;
import io.qiot.manufacturing.factory.productline.domain.event.LatestProductLineRequestedEventDTO;

@ApplicationScoped
public class LatestProductLineRequestMessageConsumer implements Runnable {

    @Inject
    Logger LOGGER;

    @Inject
    ObjectMapper MAPPER;

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    Event<LatestProductLineRequestedEventDTO> latestProductLineRequestedEvent;

    private JMSContext context;

    private JMSConsumer consumer;

    @ConfigProperty(name = "qiot.productline.request.queue-prefix")
    String queueName;

    private Queue queue;

    private final ExecutorService scheduler = Executors
            .newSingleThreadExecutor();

    void init(@Observes BootstrapCompletedEventDTO event) {
        doInit();
        scheduler.submit(this);
    }

    private void doInit() {
        if (Objects.nonNull(context))
            context.close();
        context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);

        queue = context.createQueue(queueName);

        consumer = context.createConsumer(queue);
    }

    @PreDestroy
    void destroy() {
        scheduler.shutdown();
        context.close();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = consumer.receive();
                String messagePayload = message.getBody(String.class);
                LOGGER.info(
                        "Received a request for latest product line from machinery {}. Forwarding...",
                        messagePayload);
                LatestProductLineRequestedEventDTO eventDTO = new LatestProductLineRequestedEventDTO();
                eventDTO.machineryId = messagePayload;
                latestProductLineRequestedEvent.fire(eventDTO);
            } catch (JMSException e) {
                LOGGER.error(
                        "The messaging client returned an error: {} and will be restarted.",
                        e);
                doInit();
            } catch (Exception e) {
                LOGGER.error("GENERIC ERROR", e);
            }
        }
    }
}