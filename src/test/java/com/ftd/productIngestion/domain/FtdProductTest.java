package com.ftd.productIngestion.domain;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rallampati on 1/11/2018
 */
public class FtdProductTest {

    @Test
    public void getFtpProductId() {

        FtdProduct ftdProduct = new FtdProduct();
        ftdProduct.setFtpProductId("ftdProductId");
        assertThat("ftdProductId").isEqualTo(ftdProduct.getFtpProductId());
    }

    @Test
    public void getFtdProductDescription() {

        FtdProduct ftdProduct = new FtdProduct();
        ftdProduct.setFtdProductDescription("ftdProductDescription");
        assertThat("ftdProductDescription").isEqualTo(ftdProduct.getFtdProductDescription());
    }
}