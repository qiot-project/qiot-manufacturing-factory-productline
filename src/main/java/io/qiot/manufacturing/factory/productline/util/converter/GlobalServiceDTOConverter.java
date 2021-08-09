package io.qiot.manufacturing.factory.productline.util.converter;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.qiot.manufacturing.commons.domain.productline.ColorRangesDTO;
import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;
import io.qiot.manufacturing.commons.domain.productline.PackagingRangesDTO;
import io.qiot.manufacturing.commons.domain.productline.PrintingRangesDTO;
import io.qiot.manufacturing.commons.domain.productline.ProductLineDTO;
import io.qiot.manufacturing.commons.domain.productline.SizeChartRangesDTO;
import io.qiot.manufacturing.commons.util.converter.DataObjectConverter;

@ApplicationScoped
public class GlobalServiceDTOConverter
        implements DataObjectConverter<GlobalProductLineDTO, ProductLineDTO> {

    @Override
    public ProductLineDTO sourceToDest(GlobalProductLineDTO src) {
        ProductLineDTO dest = new ProductLineDTO();
        dest.id = src.id;

        /*
         * SIZE
         */
        SizeChartRangesDTO sizeChart = new SizeChartRangesDTO();
        sizeChart.chestMin = src.sizeChart.chestMin;
        sizeChart.chestMax = src.sizeChart.chestMax;
        sizeChart.shoulderMin = src.sizeChart.shoulderMin;
        sizeChart.shoulderMax = src.sizeChart.shoulderMax;
        sizeChart.backMin = src.sizeChart.backMin;
        sizeChart.backMax = src.sizeChart.backMax;
        sizeChart.waistMin = src.sizeChart.waistMin;
        sizeChart.waistMax = src.sizeChart.waistMax;
        sizeChart.hipMin = src.sizeChart.hipMin;
        sizeChart.hipMin = src.sizeChart.hipMin;
        dest.sizeChart = sizeChart;

        /*
         * COLOR
         */
        ColorRangesDTO color = new ColorRangesDTO();
        color.redMin = src.color.redMin;
        color.redMax = src.color.redMax;

        color.greenMin = src.color.greenMin;
        color.greenMax = src.color.greenMax;

        color.blueMin = src.color.blueMin;
        color.blueMax = src.color.blueMax;
        dest.color = color;

        /*
         * PRINTING
         */
        PrintingRangesDTO print = new PrintingRangesDTO();
        print.min = src.print.min;
        print.max = src.print.min;
        dest.print = print;

        /*
         * PACKAGING
         */
        PackagingRangesDTO packaging = new PackagingRangesDTO();
        packaging.min = src.packaging.min;
        packaging.max = src.packaging.min;
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
