package cn.dmego.seata.tcc.order.proxy;


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
 * @className: AccountService
 *
 * @description: tcc-account-service 服务的 Feign 客户端
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/6 17:27
 **/
@FeignClient("tcc-account-service")
@RequestMapping("/account")
@LocalTCC
public interface AccountService {

    @PostMapping("/try")
    @TwoPhaseBusinessAction(name = "accountService", commitMethod = "accountConfirm", rollbackMethod = "accountCancel")
    boolean accountTry(@RequestBody BusinessActionContext actionContext,
                       @BusinessActionContextParameter(paramName = "userId") @RequestParam("userId") Long userId,
                       @BusinessActionContextParameter(paramName = "price") @RequestParam("price") Integer price);

    @PostMapping("/confirm")
    boolean accountConfirm(@RequestBody BusinessActionContext actionContext);

    @PostMapping("/cancel")
    boolean accountCancel(@RequestBody BusinessActionContext actionContext);

}
