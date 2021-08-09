package io.qiot.manufacturing.factory.productline.util.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.commons.util.converter.DataObjectConverter;
import io.qiot.manufacturing.factory.productline.domain.productline.ProductLineEdgeBean;

@ApplicationScoped
public class EdgePLConverter
        implements DataObjectConverter<ProductLineEdgeBean, ProductLineDTO> {

    @Override
    public ProductLineDTO sourceToDest(ProductLineEdgeBean src) {
        ProductLineDTO dest = new ProductLineDTO();
        // TODO: add UUID codec and switch to UUID type for field id
        dest.id = UUID.fromString(src.id);
        dest.sizeChart = src.sizeChart;
        dest.color = src.color;
        dest.print = src.print;
        dest.packaging = src.packaging;
        return dest;
    }

    @Override
    public ProductLineEdgeBean destToSource(ProductLineDTO dest) {
        ProductLineEdgeBean src = new ProductLineEdgeBean();
        // TODO: add UUID codec and switch to UUID type for field id
        src.id = dest.id.toString();
        src.sizeChart = dest.sizeChart;
        src.color = dest.color;
        src.print = dest.print;
        src.packaging = dest.packaging;
        return src;
    }

    @Override
    public List<ProductLineDTO> allSourceToDest(
            List<ProductLineEdgeBean> srcs) {
        if (Objects.isNull(srcs))
            return null;
        List<ProductLineDTO> dests = new ArrayList<>();
        for (ProductLineEdgeBean bean : srcs)
            dests.add(sourceToDest(bean));
        return dests;
    }

    @Override
    public List<ProductLineEdgeBean> allDestToSource(
            List<ProductLineDTO> dests) {
        if (Objects.isNull(dests))
            return null;
        List<ProductLineEdgeBean> srcs = new ArrayList<>();
        for (ProductLineDTO dest : dests)
            srcs.add(destToSource(dest));
        return srcs;
    }
}
