package cn.dmego.seata.saga.business.proxy;

import cn.dmego.seata.saga.business.config.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * OrderService
 *
 * @author dmego
 * @date 2021/3/31 10:48
 */
@FeignClient(value = "saga-order-service", configuration = {FeignErrorDecoder.class})
@RequestMapping("/order")
public interface OrderService {

    @RequestMapping("/createOrder")
    Boolean createOrder(@RequestParam("orderId") Long orderId,
                        @RequestParam("userId") Long userId,
                        @RequestParam("productId") Long productId,
                        @RequestParam("amount") Integer amount,
                        @RequestParam("count") Integer count) throws Exception;

    @RequestMapping("/revokeOrder")
    Boolean revokeOrder(@RequestParam("orderId") Long orderId) throws Exception;
}
