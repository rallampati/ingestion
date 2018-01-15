package com.ftd.productIngestion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;

/**
 * Created by rallampati on 1/10/2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtdProduct extends Product {

    private String ftpProductId;
    private String ftdProductDescription;
}

