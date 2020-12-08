package cn.dmego.seata.at.business.proxy;

import cn.dmego.seata.common.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: OrderService
 *
 * @description: at-order-service 服务的 Feign 客户端
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:40
 **/
@FeignClient("at-order-service")
@RequestMapping("/order")
public interface OrderService {

    @PostMapping("/create-order")
    boolean createOrder(@RequestBody OrderDTO orderDTO);

}
