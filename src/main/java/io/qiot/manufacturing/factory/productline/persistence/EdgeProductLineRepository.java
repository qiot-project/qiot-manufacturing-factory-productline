package io.qiot.manufacturing.factory.productline.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manufacturing.factory.productline.domain.persistence.EdgeProductLineBean;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;

/**
 * @author andreabattaglia
 *
 */
@ApplicationScoped
public class EdgeProductLineRepository
        implements PanacheMongoRepositoryBase<EdgeProductLineBean, String> {

    @Inject
    Logger LOGGER;
}
