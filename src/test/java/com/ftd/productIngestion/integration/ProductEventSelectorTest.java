package com.ftd.productIngestion.integration;

import org.junit.Before;
import org.junit.Test;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by ${user} on 1/15/18
 */
public class ProductEventSelectorTest {

    ProductEventSelector productEventSelector;

    @Before
    public void setUp() throws Exception {
        productEventSelector = new ProductEventSelector();
    }

    @Test
    public void validateProductEvent() {

        Message<String> productEvent = MessageBuilder.withPayload("\"{\"productId\":\"1\", \"source\":\"PQUAD\"}\"")
                .setHeader("SOURCE", "APOLLOO")
                .build();

        assertThat(true).isEqualTo(productEventSelector.accept(productEvent));
    }
}