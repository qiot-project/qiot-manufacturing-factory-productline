package io.qiot.manufacturing.factory.productline.service.productline.consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.qiot.manufacturing.datacenter.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.factory.productline.domain.event.NewGlobalProductLineEventDTO;

/*
 * TODO: the client should be forced to download only the last offset
 */
@ApplicationScoped
public class GlobalProductLineStreamConsumer {

    @Inject
    Logger LOGGER;

    @Inject
    ObjectMapper MAPPER;

    @Inject
    Event<NewGlobalProductLineEventDTO> event;

    //
    // @Incoming("productline")
    // public void process(String data) {
    // try {
    // GlobalProductLineDTO globalProductLineDTO = MAPPER.readValue(data,
    // GlobalProductLineDTO.class);
    // LOGGER.debug("Consumed message {} from the PRODUCT LINE stream",
    // globalProductLineDTO);
    // NewGlobalProductLineEventDTO newGlobalProductLineEventDTO = new
    // NewGlobalProductLineEventDTO();
    // newGlobalProductLineEventDTO.productLine = globalProductLineDTO;
    // event.fire(newGlobalProductLineEventDTO);
    // } catch (IOException e) {
    // LOGGER.error(
    // "an error occurred receiving the new product line from stream.",
    // e);
    // }
    // }
    @Incoming("productline")
    public void process(GlobalProductLineDTO globalProductLineDTO) {
        LOGGER.info("Consumed message {} from the GLOBAL PRODUCT LINE stream",
                globalProductLineDTO);
        NewGlobalProductLineEventDTO newGlobalProductLineEventDTO = new NewGlobalProductLineEventDTO();
        newGlobalProductLineEventDTO.productLine = globalProductLineDTO;
        event.fire(newGlobalProductLineEventDTO);
    }
}
