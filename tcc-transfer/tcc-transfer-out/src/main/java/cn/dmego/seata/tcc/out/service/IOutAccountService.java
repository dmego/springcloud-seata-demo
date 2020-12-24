package cn.dmego.seata.tcc.out.service;


import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @className: IOutAccountService
 *
 * @description: 转账服务接口
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:34
 **/
@LocalTCC
public interface IOutAccountService {

    @TwoPhaseBusinessAction(name = "IOutAccountService", commitMethod = "outConfirm", rollbackMethod = "outCancel")
    boolean outTry(BusinessActionContext actionContext,
                   @BusinessActionContextParameter(paramName = "outId") String id,
                   @BusinessActionContextParameter(paramName = "amount") double amount);

    boolean outConfirm(BusinessActionContext actionContext);

    boolean outCancel(BusinessActionContext actionContext);

    boolean reset();
}
