package cn.dmego.seata.tcc.account.controller;

import cn.dmego.seata.tcc.account.service.AccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: AccountController
 *
 * @description: 账户服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/5 17:21
 **/
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/try")
    public boolean accountTry(@RequestBody BusinessActionContext actionContext,
                              @RequestParam("userId") Long userId,
                              @RequestParam("price") Integer price){
        return accountService.accountTry(actionContext, userId, price);
    }

    @PostMapping("/confirm")
    public boolean accountConfirm(@RequestBody BusinessActionContext actionContext){
        return accountService.accountConfirm(actionContext);
    }

    @PostMapping("/cancel")
    public boolean accountCancel(@RequestBody BusinessActionContext actionContext){
        return accountService.accountCancel(actionContext);
    }

}
