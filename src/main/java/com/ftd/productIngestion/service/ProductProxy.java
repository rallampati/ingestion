package com.ftd.productIngestion.service;

import com.ftd.productIngestion.domain.ApolloProduct;
import com.ftd.productIngestion.domain.PquadProduct;
import com.ftd.productIngestion.domain.Product;

/**
 * Created by rallampati on 1/9/2018
 */
public interface ProductProxy {

    public ApolloProduct getApolloProduct(String productId);

    public PquadProduct getPQuadProduct(String productId);

}
