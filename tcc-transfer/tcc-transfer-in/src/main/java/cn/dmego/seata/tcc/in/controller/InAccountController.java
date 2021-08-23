package cn.dmego.seata.tcc.in.controller;



import cn.dmego.seata.tcc.in.service.IInAccountService;
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
    public boolean inTry(@RequestParam("inId") String inId, @RequestParam("amount") String amount){
        return inAccountService.inTry(inId, amount);
    }

    @PostMapping(value = "/try2")
    public boolean inTry2(@RequestParam("inId") String inId, @RequestParam("amount") String amount){
        return inAccountService.inTry2(inId, amount);
    }

    @PostMapping(value = "/reset/{number}")
    public boolean reset(@PathVariable("number") int number){
        return inAccountService.reset(number);
    }

}
