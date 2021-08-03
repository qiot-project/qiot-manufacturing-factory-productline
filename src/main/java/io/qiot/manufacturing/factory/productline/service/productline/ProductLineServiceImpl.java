package io.qiot.manufacturing.factory.productline.service.productline;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.factory.productline.domain.event.NewProductLineEvent;
import io.qiot.manufacturing.factory.productline.domain.productline.ProductLineEdgeBean;
import io.qiot.manufacturing.factory.productline.domain.productline.ProductLineGlobalBean;
import io.qiot.manufacturing.factory.productline.persistence.productline.ProductLineEdgeRepository;
import io.qiot.manufacturing.factory.productline.persistence.productline.ProductLineGlobalRepository;
import io.qiot.manufacturing.factory.productline.service.productline.client.GlobalProductLineServiceClient;
import io.qiot.manufacturing.factory.productline.util.converter.EdgePLConverter;
import io.qiot.manufacturing.factory.productline.util.converter.GlobalLocalDTOConverter;
import io.qiot.manufacturing.factory.productline.util.converter.GlobalPLConverter;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class ProductLineServiceImpl implements ProductLineService {

    @Inject
    Logger LOGGER;
    
    private ProductLineDTO edgeProductLineDTO;

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

    @Inject
    @RestClient
    GlobalProductLineServiceClient globalProductLineClient;
    
    void onStartup(@Observes StartupEvent event) {
        //TODO: download latest productLine from Datacenter and handle it
    }

    void onNewProductLine(@Observes NewProductLineEvent event)
            throws Exception {

//        GlobalProductLineDTO globalProductLineDTO = globalProductLineClient
//                .getProductLine(event.productLineId);
//        handleNewProductLine(globalProductLineDTO);
    }

    void handleNewProductLine(GlobalProductLineDTO globalProductLineDTO)
            throws Exception {
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

        // TODO: notify machineries

    }

    @Override
    public List<ProductLineDTO> getActiveProductLines() {
        return edgePLConverter.allSourceToDest(
                edgePLRepository.find("active", "true").list());
    }

    @Override
    public List<ProductLineDTO> getAllProductLines() {
        return edgePLConverter.allSourceToDest(
                edgePLRepository.findAll().list());
    }

    @Override
    public ProductLineDTO getLastProductLine() {
        return edgeProductLineDTO;
    }

    @Override
    public ProductLineDTO getProductLineById(UUID id) {
        return edgePLConverter.sourceToDest(
                edgePLRepository.findById(id));
    }

}
