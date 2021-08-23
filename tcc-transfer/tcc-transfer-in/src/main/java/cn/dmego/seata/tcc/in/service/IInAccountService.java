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

    @TwoPhaseBusinessAction(name = "IInAccountService", commitMethod = "inConfirm", rollbackMethod = "inCancel", useTCCFence = true)
    boolean inTry(@BusinessActionContextParameter(paramName = "inId") String inId,
                  @BusinessActionContextParameter(paramName = "amount") String amount);

    @TwoPhaseBusinessAction(name = "IInAccountService", commitMethod = "inConfirm", rollbackMethod = "inCancel", useTCCFence = true, isDelayReport = true)
    boolean inTry2(String inId, String amount);

    boolean inConfirm( BusinessActionContext actionContext);

    boolean inCancel( BusinessActionContext actionContext);

    boolean reset(int number);

}
