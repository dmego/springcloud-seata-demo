package cn.dmego.seata.tcc.order.controller;

import cn.dmego.seata.tcc.order.service.OrderService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: OrderController
 *
 * @description: 订单服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/7 17:27
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/try")
    public boolean orderTry(@RequestBody BusinessActionContext actionContext,
                            @RequestParam("orderId") Long orderId,
                            @RequestParam("userId") Long userId,
                            @RequestParam("productId") Long productId,
                            @RequestParam("count") Integer count,
                            @RequestParam("payAmount") Integer payAmount){
        return orderService.orderTry(actionContext, orderId, userId, productId, count, payAmount);

    }
    @PostMapping("/confirm")
    public boolean orderConfirm(@RequestBody BusinessActionContext actionContext){
        return orderService.orderConfirm(actionContext);
    }

    @PostMapping("/cancel")
    public boolean orderCancel(@RequestBody BusinessActionContext actionContext){
        return orderService.orderCancel(actionContext);
    }

}
