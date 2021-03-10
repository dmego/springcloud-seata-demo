package cn.dmego.seata.tcc.business.proxy;


import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @className: ProductService
 *
 * @description: tcc-product-service 服务的 Feign 客户端
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/6 17:25
 **/
@FeignClient("tcc-product-service")
@RequestMapping("/product")
public interface ProductService {

    @PostMapping("/try")

    boolean productTry(@RequestBody BusinessActionContext actionContext,
                       @RequestParam("productId") Long productId,
                       @RequestParam("count") Integer count);

    @PostMapping("/confirm")
    boolean productConfirm(@RequestBody BusinessActionContext actionContext);

    @PostMapping("/cancel")
    boolean productCancel(@RequestBody BusinessActionContext actionContext);

    @GetMapping("/getPrice")
    Integer getPrice(@RequestParam("productId") Long productId);
}
