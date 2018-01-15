package com.ftd.productIngestion.config;

import com.ftd.productIngestion.domain.PquadProduct;
import com.ftd.productIngestion.domain.ProductEvent;
import com.ftd.productIngestion.integration.PquadProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;

/**
 * Created by rallampati on 1/10/2018
 */
@Configuration
public class PquadConfig {

    @Bean
    public MessageChannel pquadChannel(){
        return MessageChannels.direct().get();
    }

    /**
     * It will orchestrate the flow using integration components
     *
     * @param pquadProductHandler
     * @return
     */
    @Bean
    public IntegrationFlow pquadProductIntegrationFlow(PquadProductHandler pquadProductHandler) {

        return IntegrationFlows.from("pquadChannel")
                .log(LoggingHandler.Level.INFO, "com.ftd.productIngestion.config.apolloProductIntegrationFlow", m -> "Received Pquad event:" + m)
                .transform(Transformers.fromJson(ProductEvent.class))
                .transform(ProductEvent.class, pquadProductHandler::getPquadProduct)
                .transform(PquadProduct.class, pquadProductHandler::compareAndTransformToFtdProduct)
                .handle(pquadProductHandler)
                .get();
    }

}


