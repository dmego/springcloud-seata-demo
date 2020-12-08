package cn.dmego.seata.at.business.controller;

import cn.dmego.seata.at.business.serivce.BusinessService;
import cn.dmego.seata.common.dto.BusinessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: BusinessController
 *
 * @description: BusinessController
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:40
 **/
@RestController
@RequestMapping("/at")
public class BusinessController {
    
    @Autowired
    BusinessService businessService;

    @RequestMapping("/buy")
    public String handleBusiness(@RequestBody BusinessDTO businessDTO){
        return businessService.handleBusiness(businessDTO);
    }
}
