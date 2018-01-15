package com.ftd.productIngestion.config;

import com.ftd.productIngestion.domain.ApolloProduct;
import com.ftd.productIngestion.domain.ProductEvent;
import com.ftd.productIngestion.integration.ApolloProductHandler;
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
public class ApolloConfig {

    @Bean
    public MessageChannel apolloChannel(){
        return MessageChannels.direct().get();
    }

    /**
     * It will orchestrate the flow using integration components
     *
     * @param apolloProductHandler
     * @return
     */
    @Bean
    public IntegrationFlow apolloProductIntegrationFlow(ApolloProductHandler apolloProductHandler) {

        return IntegrationFlows.from("apolloChannel")
                .log(LoggingHandler.Level.INFO, "com.ftd.productIngestion.config.apolloProductIntegrationFlow", m -> "Received Apollo event:" + m)
                .transform(Transformers.fromJson(ProductEvent.class))
                .transform(ProductEvent.class, apolloProductHandler::getApolloProduct)
                .transform(ApolloProduct.class, apolloProductHandler::compareAndTransformToFtdProduct)
                .handle(apolloProductHandler)
                .get();
    }
}
