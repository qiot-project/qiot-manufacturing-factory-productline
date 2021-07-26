package io.qiot.manufacturing.factory.productline.service.productline;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.factory.productline.domain.event.NewProductLineEvent;
import io.qiot.manufacturing.factory.productline.persistence.productline.ProductLineRepository;
import io.qiot.manufacturing.factory.productline.service.productline.client.GlobalProductLineServiceClient;
import io.qiot.manufacturing.factory.productline.util.producer.GlobalLocalDTOConverter;
import io.qiot.manufacturing.factory.productline.util.producer.PersistenceDTOConverter;

@ApplicationScoped
public class ProductLineServiceImpl implements ProductLineService {
    
    @Inject
    Logger LOGGER;
    
    @Inject
    ProductLineRepository repository;
    
    @Inject
    PersistenceDTOConverter persistenceConverter;
    
    @Inject
    GlobalLocalDTOConverter productLineConverter;
    
    @Inject
    @RestClient
    GlobalProductLineServiceClient globalProductLineClient;
    
    void handleNewProductLine(@Observes NewProductLineEvent event) throws Exception {
        GlobalProductLineDTO globalProductLine = globalProductLineClient.getProductLine(event.productLineId);
        ProductLineDTO productLine=productLineConverter.globalToLocal(globalProductLine);
        repository.persist(productLine);
        
    }

    @Override
    public List<ProductLineDTO> getActiveProductLines() {
        return persistenceConverter.convertAll(repository.findActiveProductLines());
    }

    @Override
    public List<ProductLineDTO> getAllProductLines() {
        return persistenceConverter.convertAll(repository.findAllProductLines());
    }

    @Override
    public ProductLineDTO getLastProductLine() {
        return persistenceConverter.domainToDTO(repository.findLastProductLine());
    }

    @Override
    public ProductLineDTO getProductLineById(UUID id) {
        return persistenceConverter.domainToDTO(repository.findProductLineById(id));
    }

}
