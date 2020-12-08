package cn.dmego.seata.at.order.service;

import cn.dmego.seata.common.dto.OrderDTO;

/**
 * @className: OrderService
 *
 * @description: OrderService
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:41
 **/
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderDTO 订单DTO
     * @return 订单编号
     * @throws Exception 创建订单失败，抛出异常
     */
    boolean createOrder(OrderDTO orderDTO) throws Exception;

}
