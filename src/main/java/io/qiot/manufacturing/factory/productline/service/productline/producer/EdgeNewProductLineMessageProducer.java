/**
 * 
 */
package io.qiot.manufacturing.factory.productline.service.productline.producer;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.qiot.manufacturing.factory.productline.domain.event.NewEdgeProductLineEventDTO;

/**
 * @author andreabattaglia
 *
 */
@ApplicationScoped
public class EdgeNewProductLineMessageProducer {

    @Inject
    Logger LOGGER;

    @Inject
    ObjectMapper MAPPER;

    @Inject
    ConnectionFactory connectionFactory;

    @ConfigProperty(name = "qiot.productline.topic.name")
    String topicName;

    private JMSContext context;

    private JMSProducer producer;

    private Topic topic;

    @PostConstruct
    void init() {
        LOGGER.info("Bootstrapping validation broadcast event producer...");
        if (Objects.nonNull(context))
            context.close();
        context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);

        producer = context.createProducer();

        topic = context.createTopic(topicName);

        LOGGER.debug("Bootstrap completed");

    }

    @PreDestroy
    void destroy() {
        context.close();
    }

    void notifyMachineries(@Observes NewEdgeProductLineEventDTO event) {
        LOGGER.info("notifying machineries about the new Product Line {}",
                event.productLine);
        try {
            String data = MAPPER.writeValueAsString(event.productLine);
            producer.send(topic, data);

        } catch (JsonProcessingException e) {
            LOGGER.error(
                    "The message payload is malformed and the validation request will not be sent: {}",
                    e);
        } catch (Exception e) {
            LOGGER.error("GENERIC ERROR", e);
        }

    }
}
