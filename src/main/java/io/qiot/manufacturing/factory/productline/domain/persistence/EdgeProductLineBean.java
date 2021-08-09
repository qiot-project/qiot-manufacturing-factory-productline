package io.qiot.manufacturing.factory.productline.domain.persistence;

import javax.persistence.Cacheable;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

@MongoEntity(collection = "product_line_edge")
@Cacheable
@RegisterForReflection
public class EdgeProductLineBean extends AbstractProductLineBean {

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EdgeProductLineBean [toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }

}