package io.qiot.manufacturing.factory.productline.util.deserializer;

import io.qiot.manufacturing.datacenter.commons.domain.productline.GlobalProductLineDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

/**
 * There is an existing ObjectMapperSerializer that can be used to serialize all
 * data objects via Jackson. You may create an empty subclass if you want to use
 * Serializer/deserializer autodetection.<br>
 * <br>
 * The corresponding deserializer class needs to be subclassed.
 * 
 * @author andreabattaglia
 *
 */
public class GlobalProductLineDeserializer
        extends ObjectMapperDeserializer<GlobalProductLineDTO> {
    public GlobalProductLineDeserializer() {
     super(GlobalProductLineDTO.class);
    }
}