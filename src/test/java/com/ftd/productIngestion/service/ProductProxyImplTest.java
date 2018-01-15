package com.ftd.productIngestion.service;

import com.ftd.productIngestion.domain.ApolloProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
/**
 * Created by rallampati on 1/10/2018
 */
public class ProductProxyImplTest {

    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    ProductProxyImpl productProxyImpl = new ProductProxyImpl(restTemplate);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(productProxyImpl, "getProductUrl", "http://localhost");
    }

    @Test
    public void getApolloProduct() {
        when(restTemplate.getForObject(Mockito.any(URI.class), Mockito.<Class<Object>>any())).thenReturn(new ApolloProduct("apolloProductId", "category"));
        ApolloProduct aPolloproduct = productProxyImpl.getApolloProduct("apolloProductId");
        assertThat("apolloProductId").isEqualTo(aPolloproduct.getApolloProductId());
    }

    @Test
    public void getPQuadProduct() {
    }
}