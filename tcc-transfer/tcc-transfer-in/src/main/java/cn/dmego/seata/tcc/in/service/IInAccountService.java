package cn.dmego.seata.tcc.in.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @className: IInAccountService
 *
 * @description: 收钱方
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:32
 **/
@LocalTCC
public interface IInAccountService {

    @TwoPhaseBusinessAction(name = "InAccountService", commitMethod = "inConfirm", rollbackMethod = "inCancel")
    boolean inTry(BusinessActionContext actionContext,
                  @BusinessActionContextParameter(paramName = "inId") String id,
                  @BusinessActionContextParameter(paramName = "amount") double amount);

    boolean inConfirm( BusinessActionContext actionContext);

    boolean inCancel( BusinessActionContext actionContext);

    boolean reset(int number);

}
