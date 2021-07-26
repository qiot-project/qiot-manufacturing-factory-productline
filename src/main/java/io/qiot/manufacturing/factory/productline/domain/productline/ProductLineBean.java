package io.qiot.manufacturing.factory.productline.domain.productline;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonId;

import io.qiot.manufacturing.commons.domain.productline.ColorRangesDTO;
import io.qiot.manufacturing.commons.domain.productline.PackagingRangesDTO;
import io.qiot.manufacturing.commons.domain.productline.PrintingRangesDTO;
import io.qiot.manufacturing.commons.domain.productline.SizeChartRangesDTO;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

@MongoEntity(collection="product_line")
@RegisterForReflection
public class ProductLineBean {
    @BsonId
    public UUID id;
    public Instant createdOn;
    boolean active;
    public SizeChartRangesDTO sizeChart;
    public ColorRangesDTO color;
    public PrintingRangesDTO print;
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
        ProductLineBean other = (ProductLineBean) obj;
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