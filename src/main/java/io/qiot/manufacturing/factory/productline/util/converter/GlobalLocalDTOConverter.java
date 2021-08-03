package io.qiot.manufacturing.factory.productline.util.converter;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.commons.util.converter.DataObjectConverter;

@ApplicationScoped
public class GlobalLocalDTOConverter
        implements DataObjectConverter<GlobalProductLineDTO, ProductLineDTO> {

    @Override
    public ProductLineDTO sourceToDest(GlobalProductLineDTO src) {
        // TODO: APPLY MARGINS!!!
        ProductLineDTO dest = new ProductLineDTO();
        dest.id = src.id;
        dest.sizeChart = src.sizeChart;
        dest.color = src.color;
        dest.print = src.print;
        dest.packaging = src.packaging;
        return dest;
    }

    @Override
    public GlobalProductLineDTO destToSource(ProductLineDTO dest) {
        return null;
    }

    @Override
    public List<ProductLineDTO> allSourceToDest(
            List<GlobalProductLineDTO> src) {
        return null;
    }

    @Override
    public List<GlobalProductLineDTO> allDestToSource(
            List<ProductLineDTO> dest) {
        return null;
    }
}
