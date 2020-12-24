package cn.dmego.seata.tcc.in.controller;



import cn.dmego.seata.tcc.in.service.IInAccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: InAccountController
 *
 * @description: 收钱方
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:30
 **/
@RestController
@RequestMapping("/inAccount")
public class InAccountController {

    @Autowired
    IInAccountService inAccountService;

    @PostMapping(value = "/try")
    public boolean inTry(@RequestBody BusinessActionContext actionContext,
                         @RequestParam("id") String id,
                         @RequestParam("amount") double amount){
        return inAccountService.inTry(actionContext, id, amount);
    }

    @PostMapping(value = "/confirm")
    public boolean inConfirm(@RequestBody BusinessActionContext actionContext){
        return inAccountService.inConfirm(actionContext);
    }

    @PostMapping(value = "/cancel")
    public boolean inCancel(@RequestBody BusinessActionContext actionContext){
        return inAccountService.inCancel(actionContext);
    }

    @PostMapping(value = "reset")
    public boolean reset(){
        return inAccountService.reset();
    }

}
