package cn.dmego.seata.saga.business.service;

import cn.dmego.seata.common.dto.BusinessDTO;

/**
 * BusinessService
 *
 * @author dmego
 * @date 2021/3/31 10:48
 */
public interface BusinessService {

    String handlerBusiness(BusinessDTO businessDTO);
}
