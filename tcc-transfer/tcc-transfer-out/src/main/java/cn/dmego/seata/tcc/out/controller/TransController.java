package cn.dmego.seata.tcc.out.controller;

import cn.dmego.seata.tcc.out.entity.Transfer;
import cn.dmego.seata.tcc.out.service.IOutAccountService;
import cn.dmego.seata.tcc.out.service.ITransService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @className: TransController
 *
 * @description: 转账业务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:32
 **/
@RestController
public class TransController {

    private static final Logger log = LoggerFactory.getLogger(TransController.class);

    @Autowired
    ITransService transService;

    @Autowired
    IOutAccountService outAccountService;

    @RequestMapping(value = "/tcc", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String transferTcc(@RequestBody Transfer transfer){
        log.info("转账流程开始---->");
        transService.transferAmount(transfer);
        return "request succ!";
    }

    @PostMapping(value = "reset")
    public boolean reset(){
        return outAccountService.reset();
    }

}
