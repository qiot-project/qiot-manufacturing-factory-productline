package io.qiot.manufacturing.factory.productline.service.productline;

import java.util.List;
import java.util.UUID;

import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.factory.productline.domain.event.NewGlobalProductLineEventDTO;

public interface ProductLineService {
    List<ProductLineDTO> getActiveProductLines();

    List<ProductLineDTO> getAllProductLines();

    ProductLineDTO getLatestProductLine();

    ProductLineDTO getProductLineById(UUID id);
}
