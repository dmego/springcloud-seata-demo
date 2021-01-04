package cn.dmego.seata.tcc.out.service.impl;


import cn.dmego.seata.tcc.out.entity.Transfer;
import cn.dmego.seata.tcc.out.proxy.InAccountService;
import cn.dmego.seata.tcc.out.service.IOutAccountService;
import cn.dmego.seata.tcc.out.service.ITransService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @className: TransServiceImpl
 *
 * @description: 转账业务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:35
 **/
@Service
public class TransServiceImpl implements ITransService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransServiceImpl.class);


    @Autowired
    private IOutAccountService outAccountService;

    @Autowired
    private InAccountService inAccountService;

    @Override
    @GlobalTransactional
    public boolean transferAmount(Transfer transfer){
        String xid = RootContext.getXID();

        BusinessActionContext actionContext = new BusinessActionContext();
        actionContext.setXid(xid);
        // 执行转钱方 Try
        boolean result = outAccountService.outTry(actionContext, transfer.getOutId(), transfer.getAmount());
        if(!result){
            throw new RuntimeException("转账方转钱失败");
        }
        // 执行收钱方 Try

        result = inAccountService.inTry(actionContext, transfer.getInId(), transfer.getAmount());
        if(!result){
            throw new RuntimeException("收钱方收钱失败");
        }
        return true;

    }
}
