package com.ftd.productIngestion.config;

import com.ftd.productIngestion.domain.ProductEvent;
import com.ftd.productIngestion.integration.ProductEventSelector;
import com.ftd.productIngestion.integration.ApolloProductHandler;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.PubSubAdmin;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageProducers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.gcp.pubsub.AckMode;
import org.springframework.integration.gcp.pubsub.inbound.PubSubInboundChannelAdapter;
import org.springframework.integration.gcp.pubsub.outbound.PubSubMessageHandler;
import org.springframework.integration.handler.AbstractMessageProducingHandler;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Created by rallampati on 1/08/2018
 */
@Configuration
public class GcpPubsubConfig {

    private static final Log LOGGER = LogFactory.getLog(GcpPubsubConfig.class);

    @Value("${productIngestion.topic.subscriptionName}")
    private String subscriptionName;

    @Value("${productIngestion.error.topic}")
    private String errorTopicName;

    @Value("${productIngestion.error.topic.subscriptionName}")
    private String errorSubscriptionName;

    /* TODO - This is just a temporary thing to manage pub sub components*/
    private PubSubAdmin pubSubAdmin;

    public GcpPubsubConfig(PubSubAdmin pubSubAdmin) {
        this.pubSubAdmin = pubSubAdmin;
    }

    @Bean
    public MessageChannel productInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel productErrorChannel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter productEventInboundChannelAdapter(
            @Qualifier("productInputChannel") MessageChannel inputChannel,
            @Qualifier("productErrorChannel") MessageChannel errorChannel,
            PubSubOperations pubSubTemplate) {

        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, subscriptionName);
        adapter.setOutputChannel(inputChannel);
        adapter.setErrorChannel(errorChannel);
        //adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public IntegrationFlow productEventRouteIntegrationFlow(ProductEventSelector productEventSelector, @Qualifier("apolloProductHandler") ApolloProductHandler apolloProductHandler) {

      return IntegrationFlows.from("productInputChannel")
                .log(LoggingHandler.Level.INFO, "com.ftd.productIngestion.config.productEventRouteIntegrationFlow", m -> "Received product event:" + m )
                .enrichHeaders(headerEnricherSpec -> headerEnricherSpec.header("errorChannel", "productErrorChannel"))
                .filter(productEventSelector,   endpointSpec -> endpointSpec
                        .id("productEventSelctor")
                        .discardFlow(df -> df
                                .channel("productErrorChannel")))
                .route("routeProductEvent", "routeMessage")
                .get();
    }

    @Bean
    public IntegrationFlow testFeed() {
        return IntegrationFlows
                .from(() -> new GenericMessage<>("{\"productId\":\"1\", \"source\":\"PQUAD\"}", ImmutableMap.of("SOURCE", "PQUAD")),
                        e -> e.poller(p -> p.fixedDelay(60000)))
                .channel("productInputChannel")
                .get();
    }

    @Bean
    public Object routeProductEvent(){
        return new Object() {
                public String routeMessage(Message<?> message) {
                    MessageHeaders messageHeaders = message.getHeaders();
                    Object value = messageHeaders.get("SOURCE");
                    String headerValue = String.class.cast(value);
                    return headerValue.toLowerCase().concat("Channel");
            }
        };
    }

    @MessagingGateway
    public interface PubsubOutboundErrorGateway {

        @Gateway(requestChannel = "productErrorChannel")
        void sendToPubsubErrorTopic(String payLoad);
    }

    @Bean
    @ServiceActivator(inputChannel = "productErrorChannel")
    public MessageHandler messageSender(PubSubOperations pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, errorTopicName);
    }

    @Bean
    public PubSubInboundChannelAdapter messageErrorChannelAdapter(
            @Qualifier("productErrorChannel") MessageChannel inputChannel,
            PubSubOperations pubSubTemplate) {

        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, errorSubscriptionName);
        adapter.setOutputChannel(inputChannel);

        return adapter;
    }

    @ServiceActivator(inputChannel = "productErrorChannel")
    public void messageReceiver(Message<?> message) {
        LOGGER.info("Error Message arrived! Payload: " + message);
    }
}
