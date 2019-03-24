package com.qsh.product.service.impl;

import com.qsh.product.ProductApplicationTests;
import com.qsh.product.dataobject.ProductDetail;
import com.qsh.product.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Component
public class ProductServiceImplTest extends ProductApplicationTests {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void findList() {
        List<ProductDetail> result = productService.findList(Arrays.asList("123456", "123457"));
        Assert.assertTrue(result.size() > 0);
    }
}