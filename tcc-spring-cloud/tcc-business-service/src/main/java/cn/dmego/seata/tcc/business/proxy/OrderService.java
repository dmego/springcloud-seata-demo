package cn.dmego.seata.tcc.business.proxy;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @className: OrderService
 *
 * @description: tcc-order-service 服务的 Feign 客户端
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/6 17:25
 **/
@FeignClient("tcc-order-service")
@RequestMapping("/order")
@LocalTCC
public interface OrderService {

    @PostMapping("/try")
    @TwoPhaseBusinessAction(name = "orderService", commitMethod = "orderConfirm", rollbackMethod = "orderCancel")
    boolean orderTry(@RequestBody BusinessActionContext actionContext,
                     @BusinessActionContextParameter(paramName = "orderId") @RequestParam("orderId") Long orderId,
                     @BusinessActionContextParameter(paramName = "userId") @RequestParam("userId") Long userId,
                     @BusinessActionContextParameter(paramName = "productId") @RequestParam("productId") Long productId,
                     @BusinessActionContextParameter(paramName = "count") @RequestParam("count") Integer count,
                     @BusinessActionContextParameter(paramName = "payAmount") @RequestParam("payAmount") Integer payAmount);

    @PostMapping("/confirm")
    boolean orderConfirm(@RequestBody BusinessActionContext actionContext);

    @PostMapping("/cancel")
    boolean orderCancel(@RequestBody BusinessActionContext actionContext);

}
