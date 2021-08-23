package cn.dmego.seata.tcc.out.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @className: InAccountService
 *
 * @description: tcc-transfer-in 服务的 Feign 客户端
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:34
 **/
@FeignClient(value = "tcc-transfer-in")
@RequestMapping("/inAccount")
public interface InAccountService {

    @PostMapping(value = "/try")
    boolean inTry(@RequestParam("inId") String inId, @RequestParam("amount") String amount);

    @PostMapping(value = "/try2")
    boolean inTry2(@RequestParam("inId") String inId, @RequestParam("amount") String amount);
}

