package com.ftd.productIngestion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rallampati on 1/9/2018
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;
    private String productName;
    private String productDescription;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Product{");
        sb.append("id='").append(id).append('\'');
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", productDescription='").append(productDescription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}