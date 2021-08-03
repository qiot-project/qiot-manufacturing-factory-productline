package io.qiot.manufacturing.factory.productline.domain.productline;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import io.qiot.manufacturing.commons.domain.productline.ColorRangesDTO;
import io.qiot.manufacturing.commons.domain.productline.PackagingRangesDTO;
import io.qiot.manufacturing.commons.domain.productline.PrintingRangesDTO;
import io.qiot.manufacturing.commons.domain.productline.SizeChartRangesDTO;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

@MongoEntity(collection = "product_line_edge")
@RegisterForReflection
public class ProductLineEdgeBean {
    @BsonId
    public UUID id;
    @BsonProperty(value = "created_on")
    public Instant createdOn;
    @BsonProperty(value = "active")
    boolean active;
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
        ProductLineEdgeBean other = (ProductLineEdgeBean) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProductLineDTO [productLineId=");
        builder.append(id);
        builder.append(", sizeChart=");
        builder.append(sizeChart);
        builder.append(", color=");
        builder.append(color);
        builder.append(", print=");
        builder.append(print);
        builder.append(", packaging=");
        builder.append(packaging);
        builder.append("]");
        return builder.toString();
    }

}