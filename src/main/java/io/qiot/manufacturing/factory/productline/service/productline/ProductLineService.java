package io.qiot.manufacturing.factory.productline.service.productline;

import java.util.List;
import java.util.UUID;

import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;

public interface ProductLineService {
    List<ProductLineDTO> getAllEdgeProductLines();

    ProductLineDTO getLatestEdgeProductLine();

    ProductLineDTO getEdgeProductLineById(UUID id);

    List<ProductLineDTO> getAllServiceProductLines();

    ProductLineDTO getLatestServiceProductLine();

    ProductLineDTO getServiceProductLineById(UUID id);
}
