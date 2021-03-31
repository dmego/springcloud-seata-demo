package cn.dmego.seata.saga.business.controller;

import cn.dmego.seata.common.dto.BusinessDTO;
import cn.dmego.seata.saga.business.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BusinessController
 *
 * @author dmego
 * @date 2021/3/31 10:48
 */
@RestController
@RequestMapping("/saga")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @RequestMapping("/buy")
    public String handlerBusiness(@RequestBody BusinessDTO businessDTO) {
        return businessService.handlerBusiness(businessDTO);
    }
}
