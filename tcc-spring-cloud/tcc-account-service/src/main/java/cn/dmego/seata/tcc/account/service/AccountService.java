package cn.dmego.seata.tcc.account.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @className: AccountService
 *
 * @description: 账户服务 Interface
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/5 17:23
 **/
@LocalTCC
public interface AccountService {

    @TwoPhaseBusinessAction(name = "accountService", commitMethod = "accountConfirm", rollbackMethod = "accountCancel")
    boolean accountTry(BusinessActionContext actionContext,
                       @BusinessActionContextParameter(paramName = "userId") Long userId,
                       @BusinessActionContextParameter(paramName = "price") Integer price);

    boolean accountConfirm(BusinessActionContext actionContext);

    boolean accountCancel(BusinessActionContext actionContext);
}
