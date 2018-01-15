package com.ftd.productIngestion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by rallampati on 1/8/2018
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent implements Serializable {

    private String productId;
    private ProductSource source;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("\"productId\"=\"").append(productId).append('\"');
        sb.append(", \"source\"=\"").append(source).append("\"");
        sb.append('}');
        return sb.toString();
    }
}
