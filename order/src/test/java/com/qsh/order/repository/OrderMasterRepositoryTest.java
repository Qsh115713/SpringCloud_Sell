package com.qsh.order.repository;

import com.qsh.order.OrderApplicationTests;
import com.qsh.order.dataobject.OrderMaster;
import com.qsh.order.enums.OrderStatusEnum;
import com.qsh.order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testSave() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123459");
        orderMaster.setBuyerName("师姐");
        orderMaster.setBuyerPhone("18861312412");
        orderMaster.setBuyerAddress("广州市");
        orderMaster.setBuyerOpenid("110120");
        orderMaster.setOrderAmount(new BigDecimal(3.0));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }
}