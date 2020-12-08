package cn.dmego.seata.tcc.product.service;

import io.seata.rm.tcc.api.BusinessActionContext;

/**
 * @className: ProductService
 *
 * @description: 仓库服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:30
 **/
public interface ProductService {

    boolean productTry(BusinessActionContext actionContext, Long productId, Integer count);

    boolean productConfirm(BusinessActionContext actionContext);

    boolean productCancel(BusinessActionContext actionContext);

    Integer getPriceById(Long productId);
}
