package com.ftd.productIngestion.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftd.productIngestion.config.GcpPubsubConfig;
import com.ftd.productIngestion.domain.*;
import com.ftd.productIngestion.service.ProductProxy;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.support.GcpHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Qualifier("pquadProductHandler")
public class PquadProductHandler {

    private static final Log LOGGER = LogFactory.getLog(PquadProductHandler.class);
    ProductProxy productProxy;

    public PquadProductHandler(ProductProxy productProxy) {
        this.productProxy = productProxy;
    }

    /**
     * Retrieves Pquad product from Apollo source. It will make a HTTP request to Apollo by product Id.
     * @param productEvent
     * @return
     */
    public PquadProduct getPquadProduct(ProductEvent productEvent) {

        LOGGER.info("Get pquad product details for this event: " + productEvent);
        PquadProduct pquadProduct = productProxy.getPQuadProduct(productEvent.getProductId());
        if(Objects.isNull(pquadProduct))
            throw new MessagingException("Apollo product is null");
        return pquadProduct;
    }

    /**
     *
     * @param pquadProduct
     * @return
     */
    public FtdProduct compareAndTransformToFtdProduct(PquadProduct pquadProduct){

        LOGGER.info("Compare and transform: " + pquadProduct);
        return new FtdProduct("1", "ftd product description");
    }

    /**
     *
     * @param ftdProduct
     */
    @ServiceActivator
    public void postFtpProductToProductApi(FtdProduct ftdProduct){
        LOGGER.info("Post ftd product to product platform");
    }
}
