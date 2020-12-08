package cn.dmego.seata.at.business.serivce;

import cn.dmego.seata.common.dto.BusinessDTO;

/**
 * @className: BusinessService
 *
 * @description: BusinessService
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:41
 **/
public interface BusinessService {

    String handleBusiness(BusinessDTO businessDTO);
}
