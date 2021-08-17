package io.qiot.manufacturing.factory.productline.util.converter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import io.qiot.manufacturing.all.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.all.commons.util.converter.DataObjectConverter;
import io.qiot.manufacturing.factory.productline.domain.persistence.EdgeProductLineBean;

/**
 * @author andreabattaglia
 *
 */
@ApplicationScoped
public class EdgePLConverter
        implements DataObjectConverter<EdgeProductLineBean, ProductLineDTO> {

    @Override
    public ProductLineDTO sourceToDest(EdgeProductLineBean src) {
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
    public EdgeProductLineBean destToSource(ProductLineDTO dest) {
        EdgeProductLineBean src = new EdgeProductLineBean();
        // TODO: add UUID codec and switch to UUID type for field id
        src.id = dest.id.toString();
        src.createdOn=Instant.now();
        src.sizeChart = dest.sizeChart;
        src.color = dest.color;
        src.print = dest.print;
        src.packaging = dest.packaging;
        return src;
    }

    @Override
    public List<ProductLineDTO> allSourceToDest(
            List<EdgeProductLineBean> srcs) {
        if (Objects.isNull(srcs))
            return null;
        List<ProductLineDTO> dests = new ArrayList<>();
        for (EdgeProductLineBean bean : srcs)
            dests.add(sourceToDest(bean));
        return dests;
    }

    @Override
    public List<EdgeProductLineBean> allDestToSource(
            List<ProductLineDTO> dests) {
        if (Objects.isNull(dests))
            return null;
        List<EdgeProductLineBean> srcs = new ArrayList<>();
        for (ProductLineDTO dest : dests)
            srcs.add(destToSource(dest));
        return srcs;
    }
}
