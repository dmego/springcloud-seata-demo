package cn.dmego.seata.tcc.out.service.impl;


import cn.dmego.seata.tcc.out.dao.OutAccountDao;
import cn.dmego.seata.tcc.out.entity.Account;
import cn.dmego.seata.tcc.out.service.IOutAccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextUtil;
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
    public boolean outTry(String outId, String amount) {
        long s = System.currentTimeMillis();
        BusinessActionContext actionContext = BusinessActionContextUtil.getContext();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        log.info("[outTry]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, outId, amount);

        // 执行转钱 try SQL
        int amountTry = outAccountDao.amountTry(outId, Double.parseDouble(amount));
        if(amountTry == 0){
            throw new RuntimeException("转钱方 Try 阶段失败.");
        }

        long e = System.currentTimeMillis();
        log.info("outTry used time {}ms", (e - s));
        return true;
    }

    @Override
    public boolean outTry2(String outId, String amount) {
        long s = System.currentTimeMillis();
        BusinessActionContext actionContext = BusinessActionContextUtil.getContext();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        log.info("[outTry2]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, outId, amount);

        // 执行转钱 try SQL
        int amountTry = outAccountDao.amountTry(outId, Double.parseDouble(amount));
        if(amountTry == 0){
            throw new RuntimeException("转钱方 Try 阶段失败.");
        }
        BusinessActionContextUtil.addContext("outId", outId);
        BusinessActionContextUtil.addContext("amount", amount);
        long e = System.currentTimeMillis();

        log.info("outTry2 used time {}ms", (e - s));
        return true;
    }

    @Override
    public boolean outConfirm(BusinessActionContext actionContext) {
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        String outId = String.valueOf(actionContext.getActionContext("outId"));
        double amount = Double.parseDouble(String.valueOf(actionContext.getActionContext("amount")));
        log.info("[outConfirm]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, outId, amount);

        // 执行转钱 Confirm SQL
        int amountConfirm = outAccountDao.amountConfirm(outId, amount);
        if(amountConfirm == 0){
            throw new RuntimeException("转钱方 Confirm 阶段失败.");
        }

        long e = System.currentTimeMillis();
        log.info("outConfirm used time {}ms", (e - s));
        return true;
    }

    @Override
    public boolean outCancel(BusinessActionContext actionContext) {
        long s = System.currentTimeMillis();
        String txId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        String outId = String.valueOf(actionContext.getActionContext("outId"));
        double amount =Double.parseDouble(String.valueOf(actionContext.getActionContext("amount")));
        log.debug("[outCancel]: 当前 XID:{}, branchId:{}, 用户:{}, 金额:{}", txId, branchId, outId, amount);

        // 执行转钱 Cancel SQL
        int amountCancel = outAccountDao.amountCancel(outId, amount);
        if(amountCancel == 0){
            throw new RuntimeException("转钱方 Cancel 阶段失败.");
        }

        long e = System.currentTimeMillis();
        log.info("outCancel used time {}ms", (e - s));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reset(int number) {
        List<Account> accounts =new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            Account account = new Account(i+"", "100000000", "0", "0");
            accounts.add(account);
        }

        outAccountDao.delete();
        outAccountDao.init(accounts);
        return true;
    }
}
