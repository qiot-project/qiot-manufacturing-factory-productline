package io.qiot.manufacturing.factory.productline.persistence.productline;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manufacturing.factory.productline.domain.productline.ProductLineEdgeBean;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;

@ApplicationScoped
public class ProductLineEdgeRepository
        implements PanacheMongoRepositoryBase<ProductLineEdgeBean, UUID> {

    @Inject
    Logger LOGGER;
}
