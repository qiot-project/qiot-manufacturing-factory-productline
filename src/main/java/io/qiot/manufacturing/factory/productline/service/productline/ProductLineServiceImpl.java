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

import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.factory.productline.domain.event.LatestProductLineRequestedEventDTO;
import io.qiot.manufacturing.factory.productline.domain.event.NewEdgeProductLineEventDTO;
import io.qiot.manufacturing.factory.productline.domain.event.NewGlobalProductLineEventDTO;
import io.qiot.manufacturing.factory.productline.domain.event.SendLatestProductLineEventDTO;
import io.qiot.manufacturing.factory.productline.domain.productline.ProductLineEdgeBean;
import io.qiot.manufacturing.factory.productline.domain.productline.ProductLineGlobalBean;
import io.qiot.manufacturing.factory.productline.persistence.ProductLineEdgeRepository;
import io.qiot.manufacturing.factory.productline.persistence.ProductLineGlobalRepository;
import io.qiot.manufacturing.factory.productline.util.converter.EdgePLConverter;
import io.qiot.manufacturing.factory.productline.util.converter.GlobalLocalDTOConverter;
import io.qiot.manufacturing.factory.productline.util.converter.GlobalPLConverter;

@ApplicationScoped
public class ProductLineServiceImpl implements ProductLineService {

    @Inject
    Logger LOGGER;

    @Inject
    ProductLineEdgeRepository edgePLRepository;

    @Inject
    ProductLineGlobalRepository globalPLRepository;

    @Inject
    EdgePLConverter edgePLConverter;

    @Inject
    GlobalPLConverter globalPLConverter;

    @Inject
    GlobalLocalDTOConverter productLineConverter;

    private ProductLineDTO edgeProductLineDTO;

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
        writeLock.lock();
        try {
            // convert and save global product line
            ProductLineGlobalBean globalProductLineBean = globalPLConverter
                    .destToSource(globalProductLineDTO);
            globalPLRepository.persist(globalProductLineBean);

            // create edge product line
            edgeProductLineDTO = productLineConverter
                    .sourceToDest(globalProductLineDTO);

            // convert and save edge product line
            ProductLineEdgeBean productLineEdgeBean = edgePLConverter
                    .destToSource(edgeProductLineDTO);
            edgePLRepository.persist(productLineEdgeBean);

            NewEdgeProductLineEventDTO notifyMachineriesEventDTO = new NewEdgeProductLineEventDTO();
            notifyMachineriesEventDTO.productLine = edgeProductLineDTO;
            newEdgeProductLineEvent.fire(notifyMachineriesEventDTO);
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public List<ProductLineDTO> getActiveProductLines() {
        readLock.lock();
        try {
            return edgePLConverter.allSourceToDest(
                    edgePLRepository.find("active", "true").list());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<ProductLineDTO> getAllProductLines() {
        readLock.lock();
        try {
            return edgePLConverter
                    .allSourceToDest(edgePLRepository.findAll().list());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ProductLineDTO getLatestProductLine() {
        readLock.lock();
        try {
            return edgeProductLineDTO;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ProductLineDTO getProductLineById(UUID id) {
        readLock.lock();
        try {
            return edgePLConverter.sourceToDest(edgePLRepository.findById(id));
        } finally {
            readLock.unlock();
        }
    }
    
    void onLastestProductlineRequest(@Observes LatestProductLineRequestedEventDTO event) {
        SendLatestProductLineEventDTO eventDTO=new SendLatestProductLineEventDTO();
        eventDTO.machineryId=event.machineryId;
        eventDTO.productLine=getLatestProductLine();
        sendLatestProductLineEvent.fire(eventDTO);
    }

}
