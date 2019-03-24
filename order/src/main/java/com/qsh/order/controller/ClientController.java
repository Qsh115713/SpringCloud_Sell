package com.qsh.order.controller;

import com.qsh.order.dataobject.ProductDetail;
import com.qsh.order.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class ClientController {

    /**
     * 1、第一种方式（直接使用RestTemplate，url写死）
     * RestTemplate restTemplate = new RestTemplate();
     * String response = restTemplate.getForObject("http://localhost:8080/msg", String.class);
     * <p>
     * 2、第二种方式（利用LoadBalancerClient，通过应用名获取url，然后再使用RestTemplate）
     * RestTemplate restTemplate = new RestTemplate();
     * ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
     * String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()) + "/msg";
     * String response = restTemplate.getForObject(url, String.class);
     * <p>
     * 3、第三种方式（利用@LoadBalanced，可在RestTemplate里使用服务名称）
     * String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
     */

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/getProductList")
    public List<ProductDetail> getProductList(List<String> productIdList) {
        ProductDetail[] productDetails = restTemplate.postForObject("http://product/product/listForOrder", productIdList, ProductDetail[].class);
        return productDetails == null ? null : Arrays.asList(productDetails);
    }

    @PostMapping("/productDecreaseStock")
    public void decreaseStock(List<CartDTO> cartDTOList) {
        restTemplate.postForObject("http://product/product/decreaseStock", cartDTOList, Object.class);
    }
}
