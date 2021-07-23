package io.qiot.manifacturing.factory.core.service.productline;

import java.util.List;
import java.util.UUID;

import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;

public interface ProductLineService {
    List<ProductLineDTO> getActiveProductLines();
    List<ProductLineDTO> getAllProductLines();
    ProductLineDTO getLastProductLine();
    ProductLineDTO getProductLineById(UUID id);
}
