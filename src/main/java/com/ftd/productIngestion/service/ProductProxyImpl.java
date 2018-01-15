package com.ftd.productIngestion.service;

import com.ftd.productIngestion.domain.ApolloProduct;
import com.ftd.productIngestion.domain.PquadProduct;
import com.ftd.productIngestion.domain.Product;
import com.ftd.productIngestion.integration.ApolloProductHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by rallampati on 1/9/2018
 */
@Service
public class ProductProxyImpl implements ProductProxy {

    private static final Log LOGGER = LogFactory.getLog(ProductProxyImpl.class);
    RestTemplate restTemplate;
    @Value("${apollo.getProduct.url}")
    private String getProductUrl;

    public ProductProxyImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ApolloProduct getApolloProduct(String productId) {

        LOGGER.info("Get apollo product details for " + productId );
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getProductUrl)
                .queryParam("productId", productId);
        ApolloProduct apolloProduct = restTemplate.getForObject(builder.build().encode().toUri(), ApolloProduct.class);
        return apolloProduct;
    }

    @Override
    public PquadProduct getPQuadProduct(String productId) {

        LOGGER.info("Get Pquad product details for " +productId);
        PquadProduct pquadProduct = new PquadProduct("pquadProductId");
        return pquadProduct;
    }
}
