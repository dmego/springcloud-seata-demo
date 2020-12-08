package cn.dmego.seata.at.business.proxy;

import cn.dmego.seata.common.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @className: ProductService
 *
 * @description: at-product-service 服务的 Feign 客户端
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:40
 **/
@FeignClient("at-product-service")
@RequestMapping("/product")
public interface ProductService {

    @PostMapping("/reduce-stock")
    boolean reduceStock (@RequestBody ProductDTO productDTO);

    @GetMapping("/getPrice")
    Integer getPrice(@RequestParam("productId") Long productId);

}
