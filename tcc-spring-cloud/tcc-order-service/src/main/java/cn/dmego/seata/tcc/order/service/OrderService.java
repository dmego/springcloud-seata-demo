package cn.dmego.seata.tcc.order.service;

import io.seata.rm.tcc.api.BusinessActionContext;

/**
 * @className: OrderService
 *
 * @description: 订单服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/6 17:28
 **/
public interface OrderService {

    boolean orderTry(BusinessActionContext actionContext,Long orderId, Long userId, Long productId, Integer count, Integer payAmount);

    boolean orderConfirm(BusinessActionContext actionContext);

    boolean orderCancel(BusinessActionContext actionContext);
}
