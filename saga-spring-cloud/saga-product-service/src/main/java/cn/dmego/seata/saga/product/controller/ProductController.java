package cn.dmego.seata.saga.product.controller;

import cn.dmego.seata.saga.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController
 *
 * @author dmego
 * @date 2021/3/31 10:52
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/reduceStock")
    Boolean reduceStock(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) throws Exception {
        return productService.reduceStock(productId, count);
    }

    @RequestMapping("/compensateStock")
    Boolean compensateStock(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) throws Exception {
        return productService.compensateStock(productId, count);
    }

    @GetMapping("/getPrice")
    Integer getPrice(@RequestParam("productId") Long productId) {
        return productService.getPriceById(productId);
    }

}
