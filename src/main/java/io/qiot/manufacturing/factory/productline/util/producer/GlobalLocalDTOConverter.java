package io.qiot.manufacturing.factory.productline.util.producer;

import javax.enterprise.context.ApplicationScoped;

import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;

@ApplicationScoped
public class GlobalLocalDTOConverter {

    public ProductLineDTO globalToLocal(GlobalProductLineDTO global) {
        //TODO: APPLY MARGINS!!!
        ProductLineDTO local = new ProductLineDTO();
        local.id = global.id;
        local.sizeChart = global.sizeChart;
        local.color = global.color;
        local.print = global.print;
        local.packaging = global.packaging;
        return local;
    }
}
