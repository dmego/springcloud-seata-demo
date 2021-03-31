package cn.dmego.seata.saga.account.controller;

import cn.dmego.seata.saga.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AccountController
 * 
 * @author dmego
 * @date 2021/3/31 10:51
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping("/reduceBalance")
    Boolean reduceBalance(@RequestParam("userId") Long userId, @RequestParam("amount") Integer amount) throws Exception {
        return accountService.reduceBalance(userId, amount);
    }

    @RequestMapping("/compensateBalance")
    Boolean compensateBalance(@RequestParam("userId") Long userId, @RequestParam("amount") Integer amount) throws Exception {
        return accountService.compensateBalance(userId, amount);
    }
}
