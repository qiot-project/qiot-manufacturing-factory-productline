//package io.qiot.manufacturing.factory.productline.service.productline.consumer;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.event.Event;
//import javax.inject.Inject;
//
//import org.eclipse.microprofile.reactive.messaging.Incoming;
//import org.slf4j.Logger;
//
//import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;
//import io.qiot.manufacturing.factory.productline.domain.event.NewProductLineEventDTO;
//
///*
// * TODO: the client should be forced to download only the last offset
// */
//@ApplicationScoped
//public class GlobalProductLineStreamConsumer {
//
//    @Inject
//    Logger LOGGER;
//
//    @Inject
//    Event<NewProductLineEventDTO> event;
//
//    @Incoming("pollution")
//    public void process(GlobalProductLineDTO data) {
//        LOGGER.debug("Consumed message {} from the PRODUCT LINE stream", data);
//        NewProductLineEventDTO newProductLineEvent = new NewProductLineEventDTO();
//        newProductLineEvent.productLine = data;
//        event.fire(newProductLineEvent);
//    }
//}
