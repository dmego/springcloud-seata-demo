package cn.dmego.seata.tcc.in.service.impl;


import cn.dmego.seata.common.util.ResultHolder;
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
    @Transactional(rollbackFor = Exception.class)
    public boolean inTry(BusinessActionContext actionContext, String id, double amount){
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        log.info("[inTry]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, id, amount);
        // 执行收钱 try SQL
        int amountTry = inAccountDao.inComingTry(id, amount);
        if(amountTry == 0){
            throw new RuntimeException("收钱方 Try 阶段失败.");
        }
        //事务成功，保存一个标识，供第二阶段进行判断
        ResultHolder.setResult(getClass(), actionContext.getXid(), "p");
        long e = System.currentTimeMillis();
        log.info("[inTry]: 冻结应收 {} 余额成功，耗时:{}ms", amount,(e - s));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean inConfirm(BusinessActionContext actionContext){
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        String id = ((String) actionContext.getActionContext("inId"));
        double amount = ((double) actionContext.getActionContext("amount"));
        log.info("[inConfirm]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, id, amount);

        // 幂等控制，如果commit阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        // 执行收钱 Confirm SQL
        int amountConfirm = inAccountDao.inComingConfirm(id,  amount);
        if(amountConfirm == 0){
            throw new RuntimeException("收钱方 Confirm 阶段失败.");
        }
        // commit成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        long e = System.currentTimeMillis();
        log.info("[inConfirm]: 收入 {} 余额成功，耗时:{}ms", amount,(e - s));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean inCancel(BusinessActionContext actionContext){
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        String id = ((String) actionContext.getActionContext("inId"));
        double amount = ((double) actionContext.getActionContext("amount"));
        log.info("[inCancel]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, id, amount);

        // 幂等控制，如果 cancel 阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        // 执行收钱 Cancel SQL
        int amountCancel = inAccountDao.inComingCancel(id, amount);
        if(amountCancel == 0){
            throw new RuntimeException("收钱方 Cancel 阶段失败.");
        }

        // cancel 成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        long e = System.currentTimeMillis();
        log.info("[inCancel]: 取消应收 {} 余额成功，耗时:{}ms", amount,(e - s));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reset() {
        List<Account> accounts =new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            Account account = new Account(i+"", "0", "0", "0");
            ids.add(i+"");
            accounts.add(account);
        }

        inAccountDao.delete(ids);
        inAccountDao.init(accounts);
        return true;
    }
}
