package io.qiot.manufacturing.factory.productline.service.productline.consumer;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.qiot.manufacturing.factory.productline.domain.event.NewProductLineEvent;

//@ApplicationScoped
public class GlobalProductLineMessageConsumer implements Runnable {

    @Inject
    Logger LOGGER;

    @Inject
    ObjectMapper MAPPER;

    @Inject
    ConnectionFactory connectionFactory;

    String queueName;

    // @Inject
    // MachineryService machineryService;

    @Inject
    Event<NewProductLineEvent> newProductLineEvent;

    private JMSContext context;

    private JMSConsumer consumer;

    private Queue queue;

    private final ExecutorService scheduler = Executors
            .newSingleThreadExecutor();

    // void init(@Observes BootstrapCompletedEvent event) {
    // doInit();
    // scheduler.submit(this);
    // }

    @PreDestroy
    void destroy() {
        scheduler.shutdown();
        context.close();
    }

    private void doInit() {
        if (Objects.nonNull(context))
            context.close();
        context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);

        queue = context.createQueue(queueName);
        consumer = context.createConsumer(queue);
    }

    @Override
    public void run() {
        // while (true) {
        // try {
        // Message message = consumer.receive();
        // String messagePayload = message.getBody(String.class);
        // ValidationResponseDTO messageDTO = MAPPER
        // .readValue(messagePayload, ValidationResponseDTO.class);
        // LOGGER.info("Received validation result "
        // + "for STAGE {} on ITEM {} / PRODUCTLINE {}",
        // messageDTO.stage, messageDTO.itemId, messageDTO.productLineId);
        // if (messageDTO.valid) {
        // ValidationSuccessfullEvent event = new ValidationSuccessfullEvent();
        // event.productLineId = messageDTO.productLineId;
        // event.itemId = messageDTO.itemId;
        // event.stage = messageDTO.stage;
        // successEvent.fire(event);
        // } else {
        // ValidationFailedEvent event = new ValidationFailedEvent();
        // event.productLineId = messageDTO.productLineId;
        // event.itemId = messageDTO.itemId;
        // event.stage = messageDTO.stage;
        // failureEvent.fire(event);
        // }
        // } catch (JMSException e) {
        // LOGGER.error(
        // "The messaging client returned an error: {} and will be restarted.",
        // e);
        // doInit();
        // } catch (JsonProcessingException e) {
        // LOGGER.error(
        // "The message payload is malformed and the validation request will not
        // be sent: {}",
        // e);
        // } catch (Exception e) {
        // LOGGER.error(
        // "GENERIC ERROR",
        // e);
        // }
        // }
    }
}