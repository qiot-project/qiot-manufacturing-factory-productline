package io.qiot.manufacturing.factory.productline.service.productline;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.qiot.manufacturing.all.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.datacenter.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.factory.productline.domain.event.LatestProductLineRequestedEventDTO;
import io.qiot.manufacturing.factory.productline.domain.event.NewEdgeProductLineEventDTO;
import io.qiot.manufacturing.factory.productline.domain.event.NewGlobalProductLineEventDTO;
import io.qiot.manufacturing.factory.productline.domain.event.SendLatestProductLineEventDTO;
import io.qiot.manufacturing.factory.productline.domain.persistence.EdgeProductLineBean;
import io.qiot.manufacturing.factory.productline.domain.persistence.ServiceProductLineBean;
import io.qiot.manufacturing.factory.productline.persistence.EdgeProductLineRepository;
import io.qiot.manufacturing.factory.productline.persistence.ServiceProductLineRepository;
import io.qiot.manufacturing.factory.productline.util.converter.EdgePLConverter;
import io.qiot.manufacturing.factory.productline.util.converter.GlobalEdgeDTOConverter;
import io.qiot.manufacturing.factory.productline.util.converter.GlobalServiceDTOConverter;
import io.qiot.manufacturing.factory.productline.util.converter.ServicePLConverter;

/**
 * @author andreabattaglia
 *
 */
@ApplicationScoped
public class ProductLineServiceImpl implements ProductLineService {

    @Inject
    Logger LOGGER;

    @Inject
    ObjectMapper MAPPER;

    @Inject
    EdgeProductLineRepository edgePLRepository;

    @Inject
    ServiceProductLineRepository servicePLRepository;

    @Inject
    EdgePLConverter edgePLConverter;

    @Inject
    ServicePLConverter servicePLConverter;

    @Inject
    GlobalEdgeDTOConverter globalLocalDTOConverter;

    @Inject
    GlobalServiceDTOConverter globalServiceDTOConverter;

    private ProductLineDTO edgeProductLineDTO;
    private ProductLineDTO serviceProductLineDTO;

    private ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;

    @Inject
    Event<NewEdgeProductLineEventDTO> newEdgeProductLineEvent;

    @Inject
    Event<SendLatestProductLineEventDTO> sendLatestProductLineEvent;

    public ProductLineServiceImpl() {
        this.readWriteLock = new ReentrantReadWriteLock();
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
    }

    void onNewProductLine(@Observes NewGlobalProductLineEventDTO event)
            throws Exception {
        GlobalProductLineDTO globalProductLineDTO = event.productLine;
        handleNewProductLine(globalProductLineDTO);
    }

    void handleNewProductLine(GlobalProductLineDTO globalProductLineDTO)
            throws Exception {
        //TODO: switch to jackson ObjectMapper to log
        LOGGER.debug("Handling a new product line\n{}", globalProductLineDTO);
        writeLock.lock();
        try {

            // create service product line (do not apply margins)
            serviceProductLineDTO = globalServiceDTOConverter
                    .sourceToDest(globalProductLineDTO);
            LOGGER.info("Converted new SERVICE Product Line:\n{}",
                    serviceProductLineDTO);

            // create edge product line
            edgeProductLineDTO = globalLocalDTOConverter
                    .sourceToDest(globalProductLineDTO);
            LOGGER.info("Converted new EDGE Product Line:\n{}",
                    edgeProductLineDTO);

            // save service product line
            ServiceProductLineBean serviceProductLineBean = servicePLConverter
                    .destToSource(serviceProductLineDTO);
            LOGGER.debug("PERSISTED SERVICE Product Line:\n{}",
                    serviceProductLineBean);
            servicePLRepository.persist(serviceProductLineBean);

            // save edge product line
            EdgeProductLineBean edgeProductLineBean = edgePLConverter
                    .destToSource(edgeProductLineDTO);
            LOGGER.debug("PERSISTED EDGE Product Line:\n{}",
                    edgeProductLineBean);
            edgePLRepository.persist(edgeProductLineBean);

            NewEdgeProductLineEventDTO notifyMachineriesEventDTO = new NewEdgeProductLineEventDTO();
            notifyMachineriesEventDTO.productLine = edgeProductLineDTO;
            newEdgeProductLineEvent.fire(notifyMachineriesEventDTO);
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public List<ProductLineDTO> getAllEdgeProductLines() {
        readLock.lock();
        try {
            return edgePLConverter
                    .allSourceToDest(edgePLRepository.findAll().list());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ProductLineDTO getLatestEdgeProductLine() {
        readLock.lock();
        try {
            return edgeProductLineDTO;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ProductLineDTO getEdgeProductLineById(UUID id) {
        readLock.lock();
        try {
            return edgePLConverter
                    .sourceToDest(edgePLRepository.findById(id.toString()));
        } finally {
            readLock.unlock();
        }
    }

    void onLastestProductlineRequest(
            @Observes LatestProductLineRequestedEventDTO event) {
        SendLatestProductLineEventDTO eventDTO = new SendLatestProductLineEventDTO();
        eventDTO.machineryId = event.machineryId;
        eventDTO.productLine = getLatestEdgeProductLine();
        sendLatestProductLineEvent.fire(eventDTO);
    }

    @Override
    public List<ProductLineDTO> getAllServiceProductLines() {
        readLock.lock();
        try {
            return servicePLConverter
                    .allSourceToDest(servicePLRepository.findAll().list());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ProductLineDTO getLatestServiceProductLine() {
        readLock.lock();
        try {
            return serviceProductLineDTO;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ProductLineDTO getServiceProductLineById(UUID id) {
        readLock.lock();
        try {
            return servicePLConverter
                    .sourceToDest(servicePLRepository.findById(id.toString()));
        } finally {
            readLock.unlock();
        }
    }

}
