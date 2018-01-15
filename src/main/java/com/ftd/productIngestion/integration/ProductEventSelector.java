package com.ftd.productIngestion.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * Created by rallampati on 1/9/2018
 */
@Component
public class ProductEventSelector implements MessageSelector {

    private static final Log LOGGER = LogFactory.getLog(ProductEventSelector.class);

    public boolean accept(Message<?> message) {
      LOGGER.info("Validate if this a valid product event");

      if(StringUtils.isEmpty( message.getPayload()) || StringUtils.isEmpty(message.getHeaders().get("SOURCE")))
         throw new MessagingException("Failed in message validation, check the message construct and headers");

      return true;
    }
}

