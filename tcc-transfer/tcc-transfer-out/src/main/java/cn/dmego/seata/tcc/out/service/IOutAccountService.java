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

    @TwoPhaseBusinessAction(name = "IOutAccountService", commitMethod = "outConfirm", rollbackMethod = "outCancel", useTCCFence = true)
    boolean outTry(@BusinessActionContextParameter(paramName = "outId") String outId,
                   @BusinessActionContextParameter(paramName = "amount") String amount);

    @TwoPhaseBusinessAction(name = "IOutAccountService", commitMethod = "outConfirm", rollbackMethod = "outCancel", useTCCFence = true, isDelayReport = true)
    boolean outTry2(String outId, String amount);

    boolean outConfirm(BusinessActionContext actionContext);

    boolean outCancel(BusinessActionContext actionContext);

    boolean reset(int number);
}
