package cn.dmego.seata.tcc.business.service;

import cn.dmego.seata.common.dto.BusinessDTO;

/**
 * @className: BusinessService
 *
 * @description: 业务服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/6 17:26
 **/
public interface BusinessService {

    String handleBusiness(BusinessDTO businessDTO);
}
