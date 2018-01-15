package com.ftd.productIngestion.integration;

import com.ftd.productIngestion.domain.ApolloProduct;
import com.ftd.productIngestion.domain.FtdProduct;
import com.ftd.productIngestion.domain.ProductEvent;
import com.ftd.productIngestion.service.ProductProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Qualifier("apolloProductHandler")
public class ApolloProductHandler {

	private static final Log LOGGER = LogFactory.getLog(ApolloProductHandler.class);
	ProductProxy productProxy;

	public ApolloProductHandler(ProductProxy productProxy) {
		this.productProxy = productProxy;
	}

	/**
	 * Retrieves Apollo product from Apollo source. It will make a HTTP request to Apollo by product Id.
	 *
	 * @param productEvent
	 * @return
	 */
	public ApolloProduct getApolloProduct(ProductEvent productEvent) {

		LOGGER.info("Get apollo product details for this event: " + productEvent);
		ApolloProduct apolloProduct = productProxy.getApolloProduct(productEvent.getProductId());
		LOGGER.info(apolloProduct);
		if(Objects.isNull(apolloProduct))
			throw new MessagingException("Apollo product is null");

		return apolloProduct;

	}

	/**
	 * Transform it into FTD product and compare it with the Product if it already exists. Based on the outcome of the
     * comparison, make an appropriate call to FTD product platform.
	 *
	 * @param apolloProduct
	 * @return
	 */
	public FtdProduct compareAndTransformToFtdProduct(ApolloProduct apolloProduct) {

		LOGGER.info("Compare and transform: " + apolloProduct);
		return new FtdProduct("1", "ftd product description");
	}

	/**
	 * It will post FtdProduct to Product platform api.
	 * @param ftdProduct
	 */
	@ServiceActivator
	public void postFtpProductToProductApi(FtdProduct ftdProduct) {
		LOGGER.info("Post ftd product to product platform");
	}
}
