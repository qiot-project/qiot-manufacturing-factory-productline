//package io.qiot.manufacturing.factory.productline.service.core;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.event.Event;
//import javax.enterprise.event.Observes;
//import javax.inject.Inject;
//
//import org.slf4j.Logger;
//
//import io.qiot.manufacturing.factory.commons.domain.event.BootstrapCompletedEventDTO;
//import io.qiot.manufacturing.factory.commons.domain.productline.GlobalProductLineDTO;
//import io.qiot.manufacturing.commons.util.producer.SampleGlobalProductLineProducer;
//import io.qiot.manufacturing.factory.productline.domain.event.NewGlobalProductLineEventDTO;
//import io.quarkus.runtime.StartupEvent;
//
///**
// * @author andreabattaglia
// *
// */
//@ApplicationScoped
//public class StartUpService {
//    @Inject
//    Logger LOGGER;
//
//    @Inject
//    SampleGlobalProductLineProducer sampleGlobalProductLineProducer;
//
//    @Inject
//    Event<NewGlobalProductLineEventDTO> newProductLineEvent;
//
//    @Inject
//    Event<BootstrapCompletedEventDTO> bootstrapCompletedEvent;
//
//    void onStart(@Observes StartupEvent ev) {
//        LOGGER.debug("The application is starting...");
//        GlobalProductLineDTO globalProductLine = sampleGlobalProductLineProducer
//                .generate();
//        NewGlobalProductLineEventDTO newProductLineEventDTO = new NewGlobalProductLineEventDTO();
//        newProductLineEventDTO.productLine = globalProductLine;
//        newProductLineEvent.fire(newProductLineEventDTO);
//        bootstrapCompletedEvent.fire(new BootstrapCompletedEventDTO());
//        LOGGER.debug("Aapplication bootstrap completed...");
//    }
//}
