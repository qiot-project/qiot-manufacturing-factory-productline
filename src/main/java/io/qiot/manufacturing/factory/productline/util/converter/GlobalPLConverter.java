package io.qiot.manufacturing.factory.productline.util.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.commons.util.converter.DataObjectConverter;
import io.qiot.manufacturing.factory.productline.domain.productline.ProductLineGlobalBean;

@ApplicationScoped
public class GlobalPLConverter implements
        DataObjectConverter<ProductLineGlobalBean, GlobalProductLineDTO> {

    @Override
    public GlobalProductLineDTO sourceToDest(ProductLineGlobalBean src) {
        GlobalProductLineDTO dest = new GlobalProductLineDTO();
        dest.id = src.id;
        dest.sizeChart = src.sizeChart;
        dest.color = src.color;
        dest.print = src.print;
        dest.packaging = src.packaging;
        return dest;
    }

    @Override
    public ProductLineGlobalBean destToSource(GlobalProductLineDTO dest) {
        ProductLineGlobalBean src = new ProductLineGlobalBean();
        src.id = dest.id;
        src.sizeChart = dest.sizeChart;
        src.color = dest.color;
        src.print = dest.print;
        src.packaging = dest.packaging;
        return src;
    }

    @Override
    public List<GlobalProductLineDTO> allSourceToDest(
            List<ProductLineGlobalBean> srcs) {
        if (Objects.isNull(srcs))
            return null;
        List<GlobalProductLineDTO> dests = new ArrayList<>();
        for (ProductLineGlobalBean bean : srcs)
            dests.add(sourceToDest(bean));
        return dests;
    }

    @Override
    public List<ProductLineGlobalBean> allDestToSource(
            List<GlobalProductLineDTO> dests) {
        if (Objects.isNull(dests))
            return null;
        List<ProductLineGlobalBean> srcs = new ArrayList<>();
        for (GlobalProductLineDTO dest : dests)
            srcs.add(destToSource(dest));
        return srcs;
    }
}
