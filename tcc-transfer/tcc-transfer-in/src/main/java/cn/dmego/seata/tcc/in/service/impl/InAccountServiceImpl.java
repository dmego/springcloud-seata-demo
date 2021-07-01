package cn.dmego.seata.tcc.in.service.impl;

import cn.dmego.seata.tcc.in.dao.InAccountDao;
import cn.dmego.seata.tcc.in.entity.Account;
import cn.dmego.seata.tcc.in.service.IInAccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: InAccountServiceImpl
 *
 * @description: 收钱方
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:31
 **/
@Service
public class InAccountServiceImpl implements IInAccountService {

    private static final Logger log = LoggerFactory.getLogger(InAccountServiceImpl.class);

    @Autowired
    private InAccountDao inAccountDao;

    @Override
    public boolean inTry(BusinessActionContext actionContext, String id, String amount){
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        log.debug("[inTry]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, id, amount);
        // 执行收钱 try SQL
        int amountTry = inAccountDao.inComingTry(id, Double.parseDouble(amount));
        if(amountTry == 0){
            throw new RuntimeException("收钱方 Try 阶段失败.");
        }
        long e = System.currentTimeMillis();
        log.info("inTry used time:{} ms", (e - s));
        return true;
    }

    @Override
    public boolean inConfirm(BusinessActionContext actionContext){
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        String id = ((String) actionContext.getActionContext("id"));
        String amount = (String) actionContext.getActionContext("amount");
        log.debug("[inConfirm]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, id, amount);

        // 执行收钱 Confirm SQL
        int amountConfirm = inAccountDao.inComingConfirm(id, Double.parseDouble(amount));
        if(amountConfirm == 0){
            throw new RuntimeException("收钱方 Confirm 阶段失败.");
        }
        long e = System.currentTimeMillis();
        log.info("inConfirm used time:{} ms", (e - s));
        return true;
    }

    @Override
    public boolean inCancel(BusinessActionContext actionContext){
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        String id = ((String) actionContext.getActionContext("id"));
        String amount = (String) actionContext.getActionContext("amount");
        log.debug("[inCancel]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, id, amount);

        // 执行收钱 Cancel SQL
        int amountCancel = inAccountDao.inComingCancel(id, Double.parseDouble(amount));
        if(amountCancel == 0){
            throw new RuntimeException("收钱方 Cancel 阶段失败.");
        }

        long e = System.currentTimeMillis();
        log.info("inCancel used time:{} ms", (e - s));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reset(int number) {
        List<Account> accounts =new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            Account account = new Account(i+"", "0", "0", "0");
            accounts.add(account);
        }

        inAccountDao.delete();
        inAccountDao.init(accounts);
        return true;
    }
}
