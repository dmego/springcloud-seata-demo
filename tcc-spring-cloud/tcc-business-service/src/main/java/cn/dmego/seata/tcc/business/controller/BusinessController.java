package cn.dmego.seata.tcc.business.controller;

import cn.dmego.seata.common.dto.BusinessDTO;
import cn.dmego.seata.tcc.business.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: BusinessController
 *
 * @description: 业务服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/6 17:24
 **/
@RestController
@RequestMapping("/tcc")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @RequestMapping("/buy")
    public String handleBusiness(@RequestBody BusinessDTO businessDTO){
        return businessService.handleBusiness(businessDTO);
    }
}
