package io.qiot.manufacturing.factory.productline.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manufacturing.factory.productline.domain.persistence.ServiceProductLineBean;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;

@ApplicationScoped
public class ServiceProductLineRepository
        implements PanacheMongoRepositoryBase<ServiceProductLineBean, String> {

    @Inject
    Logger LOGGER;
}
