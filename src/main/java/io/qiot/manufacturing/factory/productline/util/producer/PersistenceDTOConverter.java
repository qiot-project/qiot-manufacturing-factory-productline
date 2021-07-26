package io.qiot.manufacturing.factory.productline.util.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.factory.productline.domain.productline.ProductLineBean;

@ApplicationScoped
public class PersistenceDTOConverter {

    public ProductLineDTO domainToDTO(ProductLineBean bean) {
        ProductLineDTO dto = new ProductLineDTO();
        dto.id = bean.id;
        dto.sizeChart = bean.sizeChart;
        dto.color = bean.color;
        dto.print = bean.print;
        dto.packaging = bean.packaging;
        return dto;
    }

    public List<ProductLineDTO> convertAll(List<ProductLineBean> beans) {
        if (Objects.isNull(beans))
            return null;
        List<ProductLineDTO> dtos = new ArrayList<>();
        for (ProductLineBean bean : beans)
            dtos.add(domainToDTO(bean));
        return dtos;
    }
}
