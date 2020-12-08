package cn.dmego.seata.tcc.product.controller;

import cn.dmego.seata.tcc.product.service.ProductService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: ProductController
 *
 * @description: 仓库服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:29
 **/
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getPrice")
    public Integer getPrice(@RequestParam("productId") Long productId){
        return productService.getPriceById(productId);
    }

    @PostMapping("/try")
    public boolean productTry(@RequestBody BusinessActionContext actionContext,
                              @RequestParam("productId") Long productId,
                              @RequestParam("count") Integer count){
        return productService.productTry(actionContext, productId, count);
    }

    @PostMapping("/confirm")
    public boolean productConfirm(@RequestBody BusinessActionContext actionContext){
        return productService.productConfirm(actionContext);
    }

    @PostMapping("/cancel")
    public boolean productCancel(@RequestBody BusinessActionContext actionContext){
        return productService.productCancel(actionContext);
    }

}
