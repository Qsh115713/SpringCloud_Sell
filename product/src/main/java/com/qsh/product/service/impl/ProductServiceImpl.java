package com.qsh.product.service.impl;

import com.qsh.product.dataobject.ProductDetail;
import com.qsh.product.dto.CartDTO;
import com.qsh.product.enums.ProductStatusEnum;
import com.qsh.product.enums.ResultEnum;
import com.qsh.product.exception.SellException;
import com.qsh.product.repository.ProductDetailRepository;
import com.qsh.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductDetailRepository repository;

    @Autowired
    public void setRepository(ProductDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDetail findByProductId(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductDetail> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductDetail> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        return repository.save(productDetail);
    }

    @Override
    public List<ProductDetail> findList(List<String> productIdList) {
        return repository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductDetail productDetail = repository.findById(cartDTO.getProductId()).orElse(null);
            if (productDetail == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer remain = productDetail.getProductStock() + cartDTO.getProductQuantity();
            productDetail.setProductStock(remain);
            repository.save(productDetail);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductDetail productDetail = repository.findById(cartDTO.getProductId()).orElse(null);
            if (productDetail == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer remain = productDetail.getProductStock() - cartDTO.getProductQuantity();
            if (remain < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productDetail.setProductStock(remain);
            repository.save(productDetail);
        }
    }
}
