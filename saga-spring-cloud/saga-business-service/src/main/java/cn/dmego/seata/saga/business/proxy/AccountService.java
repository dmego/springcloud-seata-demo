package cn.dmego.seata.saga.business.proxy;

import cn.dmego.seata.saga.business.config.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AccountService
 *
 * @author dmego
 * @date 2021/3/31 10:48
 */
@FeignClient(value = "saga-account-service", configuration = {FeignErrorDecoder.class})
@RequestMapping("/account")
public interface AccountService {

    @RequestMapping("/reduceBalance")
    Boolean reduceBalance(@RequestParam("userId") Long userId, @RequestParam("amount") Integer amount) throws Exception;

    @RequestMapping("/compensateBalance")
    Boolean compensateBalance(@RequestParam("userId") Long userId, @RequestParam("amount") Integer amount) throws Exception;
}
