package io.qiot.manufacturing.factory.productline.domain.event;

import io.qiot.manufacturing.all.commons.domain.productline.ProductLineDTO;
import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * @author andreabattaglia
 *
 */
@RegisterForReflection
public class NewEdgeProductLineEventDTO {
    public String machineryId;
    public ProductLineDTO productLine;
}
