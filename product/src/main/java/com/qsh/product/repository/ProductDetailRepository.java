package com.qsh.product.repository;

import com.qsh.product.dataobject.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, String> {

    List<ProductDetail> findByProductStatus(Integer productStatus);   //查询商品状态

    List<ProductDetail> findByProductIdIn(List<String> productIdList);
}
