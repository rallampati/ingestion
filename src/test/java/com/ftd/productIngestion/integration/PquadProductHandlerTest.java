package com.ftd.productIngestion.integration;

import com.ftd.productIngestion.domain.*;
import com.ftd.productIngestion.service.ProductProxy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by rallampati on 1/10/2018
 */
public class PquadProductHandlerTest {

    @Mock
    ProductProxy productProxy;
    @InjectMocks
    PquadProductHandler pquadProductHandler = new PquadProductHandler(productProxy);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPquadProduct() {
        ProductEvent productEvent = new ProductEvent("aProductId", ProductSource.APOLLO);
        PquadProduct pquadProduct = new PquadProduct("pquadProductId");
        when(productProxy.getPQuadProduct(productEvent.getProductId())).thenReturn(pquadProduct);
        pquadProductHandler.getPquadProduct(productEvent);
        assertThat(pquadProduct).isEqualTo(pquadProduct);
    }

    @Test
    public void checkAndTransformToFtdProduct() {
        PquadProduct pquadProduct = new PquadProduct("pquadProductId");
        FtdProduct ftdProduct = pquadProductHandler.compareAndTransformToFtdProduct(pquadProduct);
        assertNotNull(ftdProduct);
    }


    @Test
    public void postFtpProductToProductApi() {
    }
}