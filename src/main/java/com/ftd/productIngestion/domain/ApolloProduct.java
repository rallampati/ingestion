package com.ftd.productIngestion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rallampati on 1/10/2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApolloProduct extends Product {

    private String apolloProductId;
    private String category;
}
