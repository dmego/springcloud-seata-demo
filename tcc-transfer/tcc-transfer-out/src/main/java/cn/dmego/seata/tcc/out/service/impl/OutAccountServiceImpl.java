package cn.dmego.seata.tcc.out.service.impl;


import cn.dmego.seata.common.util.ResultHolder;
import cn.dmego.seata.tcc.out.dao.OutAccountDao;
import cn.dmego.seata.tcc.out.entity.Account;
import cn.dmego.seata.tcc.out.service.IOutAccountService;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: OutAccountServiceImpl
 *
 * @description: 转账服务实现
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:35
 **/
@Service
public class OutAccountServiceImpl implements IOutAccountService {

    private static final Logger log = LoggerFactory.getLogger(OutAccountServiceImpl.class);

    @Autowired
    OutAccountDao outAccountDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean outTry(BusinessActionContext actionContext, String id, double amount) {
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        log.info("[outTry]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, id, amount);

        // 执行转钱 try SQL
        int amountTry = outAccountDao.amountTry(id, amount);
        if(amountTry == 0){
            throw new RuntimeException("转钱方 Try 阶段失败.");
        }
        //事务成功，保存一个标识，供第二阶段进行判断
        ResultHolder.setResult(getClass(), actionContext.getXid(), "p");
        long e = System.currentTimeMillis();
        log.info("[outTry]: 冻结 {} 余额成功, 耗时:{}ms", amount,(e - s));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean outConfirm(BusinessActionContext actionContext) {
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        String id = String.valueOf(actionContext.getActionContext("outId"));
        double amount =Double.parseDouble(String.valueOf(actionContext.getActionContext("amount")));
        log.info("[outConfirm]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, id, amount);

        // 幂等控制，如果commit阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        // 执行转钱 Confirm SQL
        int amountConfirm = outAccountDao.amountConfirm(id, amount);
        if(amountConfirm == 0){
            throw new RuntimeException("转钱方 Confirm 阶段失败.");
        }
        // commit成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        long e = System.currentTimeMillis();
        log.info("[outConfirm]: 扣减 {} 余额成功，耗时:{}ms", amount,(e - s));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean outCancel(BusinessActionContext actionContext) {
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        String id = String.valueOf(actionContext.getActionContext("outId"));
        double amount =Double.parseDouble(String.valueOf(actionContext.getActionContext("amount")));
        log.info("[outCancel]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, id, amount);

        // 幂等控制，如果 cancel 阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        // 执行转钱 Cancel SQL
        int amountCancel = outAccountDao.amountCancel(id, amount);
        if(amountCancel == 0){
            throw new RuntimeException("转钱方 Cancel 阶段失败.");
        }
        // cancel 成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        long e = System.currentTimeMillis();
        log.info("[outCancel]: 解除冻结 {} 余额成功，耗时:{}ms", amount,(e - s));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reset() {
        List<Account> accounts =new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            Account account = new Account(i+"", "100000000", "0", "0");
            ids.add(i+"");
            accounts.add(account);
        }

        outAccountDao.delete(ids);
        outAccountDao.init(accounts);
        return true;
    }
}
