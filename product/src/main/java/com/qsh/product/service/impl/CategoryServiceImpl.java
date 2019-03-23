package com.qsh.product.service.impl;

import com.qsh.product.dataobject.ProductCategory;
import com.qsh.product.repository.ProductCategoryRepository;
import com.qsh.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private ProductCategoryRepository repository;

    @Autowired
    public void setRepository(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductCategory findByCategoryId(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
