package com.ftd.productIngestion.integration;

import com.ftd.productIngestion.domain.ApolloProduct;
import com.ftd.productIngestion.domain.FtdProduct;
import com.ftd.productIngestion.domain.ProductEvent;
import com.ftd.productIngestion.domain.ProductSource;
import com.ftd.productIngestion.service.ProductProxy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
/**
 * Created by rallampati on 1/10/2018
 */
public class ApolloProductHandlerTest {

    @Mock
    ProductProxy productProxy;
    @InjectMocks
    ApolloProductHandler apolloProductHandler = new ApolloProductHandler(productProxy);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getApolloProduct() {
        ProductEvent productEvent = new ProductEvent("aProductId", ProductSource.APOLLO);
        ApolloProduct apolloProduct = new ApolloProduct("aPolloProductId", "category" );
        when(productProxy.getApolloProduct(productEvent.getProductId())).thenReturn(apolloProduct);
        apolloProductHandler.getApolloProduct(productEvent);
        assertThat(apolloProduct).isEqualTo(apolloProduct);
    }

    @Test
    public void checkAndTransformToFtdProduct() {
        ApolloProduct apolloProduct = new ApolloProduct("aPolloProductId", "category" );
        FtdProduct ftdProduct = apolloProductHandler.compareAndTransformToFtdProduct(apolloProduct);
        assertNotNull(ftdProduct);
    }

    @Test
    public void postFtpProductToProductApi() {

    }
}