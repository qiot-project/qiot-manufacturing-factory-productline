package io.qiot.manufacturing.factory.productline.util.converter;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.qiot.manufacturing.all.commons.domain.productline.ColorRangesDTO;
import io.qiot.manufacturing.all.commons.domain.productline.PackagingRangesDTO;
import io.qiot.manufacturing.all.commons.domain.productline.PrintingRangesDTO;
import io.qiot.manufacturing.all.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.all.commons.domain.productline.SizeChartRangesDTO;
import io.qiot.manufacturing.datacenter.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.ubi.all.utilities.converter.DataObjectConverter;

/**
 * @author andreabattaglia
 *
 */
@ApplicationScoped
public class GlobalEdgeDTOConverter
        implements DataObjectConverter<GlobalProductLineDTO, ProductLineDTO> {

    @Override
    public ProductLineDTO sourceToDest(GlobalProductLineDTO src) {
        ProductLineDTO dest = new ProductLineDTO();
        dest.id = src.id;

        /*
         * SIZE
         */
        SizeChartRangesDTO sizeChart = new SizeChartRangesDTO();
        sizeChart.chestMin = src.sizeChart.chestMin - src.margins.weaving;
        sizeChart.chestMax = src.sizeChart.chestMax + src.margins.weaving;
        sizeChart.shoulderMin = src.sizeChart.shoulderMin - src.margins.weaving;
        sizeChart.shoulderMax = src.sizeChart.shoulderMax + src.margins.weaving;
        sizeChart.backMin = src.sizeChart.backMin - src.margins.weaving;
        sizeChart.backMax = src.sizeChart.backMax + src.margins.weaving;
        sizeChart.waistMin = src.sizeChart.waistMin - src.margins.weaving;
        sizeChart.waistMax = src.sizeChart.waistMax + src.margins.weaving;
        sizeChart.hipMin = src.sizeChart.hipMin - src.margins.weaving;
        sizeChart.hipMax = src.sizeChart.hipMax + src.margins.weaving;
        dest.sizeChart = sizeChart;

        /*
         * COLOR
         */
        ColorRangesDTO color = new ColorRangesDTO();
        if (src.color.redMin < src.margins.coloring)
            color.redMin = 0;
        else
            color.redMin = src.color.redMin - src.margins.coloring;
        color.redMax = src.color.redMax + src.margins.coloring;
        if (color.redMax > 255)
            color.redMax = 255;

        if (src.color.greenMin < src.margins.coloring)
            color.greenMin = 0;
        else
            color.greenMin = src.color.greenMin - src.margins.coloring;
        color.greenMax = src.color.greenMax + src.margins.coloring;
        if (color.greenMax > 255)
            color.greenMax = 255;

        if (src.color.blueMin < src.margins.coloring)
            color.blueMin = 0;
        else
            color.blueMin = src.color.blueMin - src.margins.coloring;
        color.blueMax = src.color.blueMax + src.margins.coloring;
        if (color.blueMax > 255)
            color.blueMax = 255;
        dest.color = color;

        /*
         * PRINTING
         */
        PrintingRangesDTO print = new PrintingRangesDTO();
        print.min = src.print.min - src.margins.printing;
        print.max = src.print.max + src.margins.printing;
        dest.print = print;

        /*
         * PACKAGING
         */
        PackagingRangesDTO packaging = new PackagingRangesDTO();
        packaging.min = src.packaging.min - src.margins.packaging;
        packaging.max = src.packaging.max + src.margins.packaging;
        dest.packaging = packaging;

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
