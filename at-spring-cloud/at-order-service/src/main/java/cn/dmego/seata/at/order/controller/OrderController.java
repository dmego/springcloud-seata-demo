package cn.dmego.seata.at.order.controller;


import cn.dmego.seata.at.order.service.OrderService;

import cn.dmego.seata.common.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: OrderController
 *
 * @description: OrderController
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:41
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public boolean createOrder(@RequestBody OrderDTO orderDTO) throws Exception {
        logger.info("[createOrder] 收到下单请求, 用户:{}, 商品:{}, 数量:{}", orderDTO.getUserId(), orderDTO.getUserId(), orderDTO.getCount());
        return orderService.createOrder(orderDTO);
    }

}
