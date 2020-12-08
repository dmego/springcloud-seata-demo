package cn.dmego.seata.at.order.proxy;


import cn.dmego.seata.common.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: AccountService
 *
 * @description: at-account-service 服务的 Feign 客户端
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:41
 **/
@FeignClient(name = "at-account-service")
@RequestMapping("/account")
public interface AccountService {

    @PostMapping("/reduce-balance")
    boolean reduceBalance(@RequestBody AccountDTO accountDTO);

}
