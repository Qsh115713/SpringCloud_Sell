package com.qsh.order.service.impl;

import com.qsh.order.controller.ClientController;
import com.qsh.order.converter.OrderMaster2OrderDTOConverter;
import com.qsh.order.dataobject.OrderDetail;
import com.qsh.order.dataobject.OrderMaster;
import com.qsh.order.dataobject.ProductDetail;
import com.qsh.order.dto.CartDTO;
import com.qsh.order.dto.OrderDTO;
import com.qsh.order.enums.OrderStatusEnum;
import com.qsh.order.enums.PayStatusEnum;
import com.qsh.order.enums.ResultEnum;
import com.qsh.order.exception.SellException;
import com.qsh.order.repository.OrderDetailRepository;
import com.qsh.order.repository.OrderMasterRepository;
import com.qsh.order.service.OrderService;
import com.qsh.order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private ClientController clientController;

    private OrderDetailRepository orderDetailRepository;

    private OrderMasterRepository orderMasterRepository;

    @Autowired
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Autowired
    public void setOrderDetailRepository(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Autowired
    public void setOrderMasterRepository(OrderMasterRepository orderMasterRepository) {
        this.orderMasterRepository = orderMasterRepository;
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //查询商品信息（调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductDetail> productDetailList = clientController.getProductList(productIdList);

        //计算总价
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);   //总价
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductDetail productDetail : productDetailList) {
                if (!productDetail.getProductId().equals(orderDetail.getProductId())) continue;
                //单价*数量
                orderAmount = productDetail.getProductPrice()
                        .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                        .add(orderAmount);

                //订单详情入库
                BeanUtils.copyProperties(productDetail, orderDetail);
                orderDetail.setDetailId(KeyUtil.genUniqueKey());
                orderDetail.setOrderId(orderId);
                orderDetailRepository.save(orderDetail);
            }
        }

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);    //属性值是null也会被拷贝，就算之前有值也会被覆盖
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //扣库存（调用商品服务）
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        clientController.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findByOrderId(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findByOpenid(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        return null;
    }
}
