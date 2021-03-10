package cn.dmego.seata.tcc.out.proxy;


import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    boolean inTry(@RequestBody BusinessActionContext actionContext,
                  @RequestParam("id") String id,
                  @RequestParam("amount") double amount);


    /**
     * Commit boolean.
     *
     * @param actionContext save xid
     * @return the boolean
     */
    @PostMapping(value = "/confirm")
    boolean inConfirm(@RequestBody BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext save xid
     * @return the boolean
     */
    @PostMapping(value = "/cancel")
    boolean inCancel(@RequestBody BusinessActionContext actionContext);

}

