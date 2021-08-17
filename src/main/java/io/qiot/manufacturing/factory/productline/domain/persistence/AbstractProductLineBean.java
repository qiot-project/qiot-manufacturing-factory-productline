package io.qiot.manufacturing.factory.productline.domain.persistence;

import java.time.Instant;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import io.qiot.manufacturing.all.commons.domain.productline.ColorRangesDTO;
import io.qiot.manufacturing.all.commons.domain.productline.PackagingRangesDTO;
import io.qiot.manufacturing.all.commons.domain.productline.PrintingRangesDTO;
import io.qiot.manufacturing.all.commons.domain.productline.SizeChartRangesDTO;

/**
 * @author andreabattaglia
 *
 */
public abstract class AbstractProductLineBean {
    //TODO: add UUID codec and switch to UUID type for field id
    @BsonId
    public String id;
    @BsonProperty(value = "created_on")
    public Instant createdOn;
    @BsonProperty(value = "size_chart")
    public SizeChartRangesDTO sizeChart;
    @BsonProperty(value = "color")
    public ColorRangesDTO color;
    @BsonProperty(value = "print")
    public PrintingRangesDTO print;
    @BsonProperty(value = "packaging")
    public PackagingRangesDTO packaging;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EdgeProductLineBean other = (EdgeProductLineBean) obj;
        return Objects.equals(id, other.id);
    }

//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("ProductLineDTO [productLineId=");
//        builder.append(id);
//        builder.append(", sizeChart=");
//        builder.append(sizeChart);
//        builder.append(", color=");
//        builder.append(color);
//        builder.append(", print=");
//        builder.append(print);
//        builder.append(", packaging=");
//        builder.append(packaging);
//        builder.append("]");
//        return builder.toString();
//    }

}
