package cn.dmego.seata.tcc.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;

/**
 * @className: AccountService
 *
 * @description: 账户服务 Interface
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/5 17:23
 **/
public interface AccountService {

    boolean accountTry(BusinessActionContext actionContext, Long userId, Integer price);

    boolean accountConfirm(BusinessActionContext actionContext);

    boolean accountCancel(BusinessActionContext actionContext);
}
