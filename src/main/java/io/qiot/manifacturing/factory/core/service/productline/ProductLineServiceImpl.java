package io.qiot.manifacturing.factory.core.service.productline;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manifacturing.factory.core.persistence.productline.ProductLineRepository;
import io.qiot.manifacturing.factory.core.util.producer.DomainObjectsConverter;
import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;

@ApplicationScoped
public class ProductLineServiceImpl implements ProductLineService {
    
    @Inject
    Logger LOGGER;
    
    @Inject
    ProductLineRepository repository;
    
    @Inject
    DomainObjectsConverter converter;

    @Override
    public List<ProductLineDTO> getActiveProductLines() {
        return converter.convertAll(repository.findActiveProductLines());
    }

    @Override
    public List<ProductLineDTO> getAllProductLines() {
        return converter.convertAll(repository.findAllProductLines());
    }

    @Override
    public ProductLineDTO getLastProductLine() {
        return converter.domainToDTO(repository.findLastProductLine());
    }

    @Override
    public ProductLineDTO getProductLineById(UUID id) {
        return converter.domainToDTO(repository.findProductLineById(id));
    }

}
