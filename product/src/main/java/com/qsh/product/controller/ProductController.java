package com.qsh.product.controller;

import com.qsh.product.dataobject.ProductCategory;
import com.qsh.product.dataobject.ProductDetail;
import com.qsh.product.dto.CartDTO;
import com.qsh.product.enums.ResultStatusEnum;
import com.qsh.product.service.CategoryService;
import com.qsh.product.service.ProductService;
import com.qsh.product.utils.ResultVOUtil;
import com.qsh.product.viewobject.ProductDetailVO;
import com.qsh.product.viewobject.ProductVO;
import com.qsh.product.viewobject.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    private CategoryService categoryService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 1、查询所有在架的商品
     * 2、获取类目列表
     * 3、查询类目
     * 4、构造数据
     */

    @GetMapping("/list")
    public ResultVO list() {
        //1、查询所有的上架商品
        List<ProductDetail> productDetailList = productService.findUpAll();
        //2、查询类目（一次性查询）
        List<Integer> categoryTypeList = productDetailList.stream()
                .map(ProductDetail::getCategoryType)
                .collect(Collectors.toList());
        //3、查询封装
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //4、构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductDetailVO> productDetailVOList = new ArrayList<>();
            for (ProductDetail productDetail : productDetailList) {
                if (productDetail.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductDetailVO productDetailVO = new ProductDetailVO();
                    BeanUtils.copyProperties(productDetail, productDetailVO);
                    productDetailVOList.add(productDetailVO);
                }
            }
            productVO.setProductDetailVOList(productDetailVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    /**
     * 获取商品列表（给订单服务用）
     */
    @PostMapping("/listForOrder")
    public List<ProductDetail> listForOrder(@RequestBody List<String> productIdList) {
        return productService.findList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList) {
        productService.decreaseStock(cartDTOList);
    }
}
