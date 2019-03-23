package com.qsh.order.service.impl;

import com.qsh.order.converter.OrderMaster2OrderDTOConverter;
import com.qsh.order.dataobject.OrderDetail;
import com.qsh.order.dataobject.OrderMaster;
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
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        return null;
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
