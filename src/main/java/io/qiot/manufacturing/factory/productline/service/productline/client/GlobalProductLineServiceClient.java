/**
 * 
 */

package io.qiot.manufacturing.factory.productline.service.productline.client;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.qiot.manufacturing.commons.domain.productline.GlobalProductLineDTO;

/**
 * @author andreabattaglia
 *
 */
@Path("/v1")
@RegisterRestClient(configKey = "global-productline-service")
public interface GlobalProductLineServiceClient {

    @GET
    @Path("/id/{productLineId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    GlobalProductLineDTO getProductLine(
            @PathParam("productLineId") UUID productLineId) throws Exception;

}
