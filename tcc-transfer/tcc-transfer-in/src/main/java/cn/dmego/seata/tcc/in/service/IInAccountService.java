package cn.dmego.seata.tcc.in.service;

import io.seata.rm.tcc.api.BusinessActionContext;

/**
 * @className: IInAccountService
 *
 * @description: 收钱方
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:32
 **/
public interface IInAccountService {

    boolean inTry(BusinessActionContext actionContext, String id, double amount);

    boolean inConfirm( BusinessActionContext actionContext);

    boolean inCancel( BusinessActionContext actionContext);

    boolean reset();
}
