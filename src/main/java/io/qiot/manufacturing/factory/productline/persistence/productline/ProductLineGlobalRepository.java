package io.qiot.manufacturing.factory.productline.persistence.productline;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manufacturing.factory.productline.domain.productline.ProductLineGlobalBean;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;

@ApplicationScoped
public class ProductLineGlobalRepository
        implements PanacheMongoRepositoryBase<ProductLineGlobalBean, UUID> {

    @Inject
    Logger LOGGER;
}
