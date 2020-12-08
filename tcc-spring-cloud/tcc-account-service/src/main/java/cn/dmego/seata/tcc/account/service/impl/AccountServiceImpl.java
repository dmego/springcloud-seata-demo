package cn.dmego.seata.tcc.account.service.impl;

import cn.dmego.seata.common.util.ResultHolder;
import cn.dmego.seata.tcc.account.dao.AccountDao;
import cn.dmego.seata.tcc.account.service.AccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: AccountServiceImpl
 *
 * @description: 账户服务 Service
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/5 17:23
 **/
@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean accountTry(BusinessActionContext actionContext, Long userId, Integer price) {
        String xId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        logger.info("[accountTry]: 当前 XID:{}, branchId:{}, 用户:{}， 金额:{}", xId, branchId, userId, price);
        int flag = accountDao.accountTry(userId, price);
        if(flag == 0){
            throw new RuntimeException("账户服务 Try 阶段失败.");
        }
        //事务成功，保存一个标识，供第二阶段进行判断
        ResultHolder.setResult(getClass(), actionContext.getXid(), "p");
        logger.info("[accountTry]: 冻结 {} 余额成功", price);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean accountConfirm(BusinessActionContext actionContext) {
        String xId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        Integer userId = ((Integer) actionContext.getActionContext("userId"));
        Integer price = ((Integer) actionContext.getActionContext("price"));
        logger.info("[accountConfirm]: 当前 XID:{}, branchId:{}, 用户:{}， 金额:{}", xId, branchId, userId, price);

        // 幂等控制，如果commit阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        int flag = accountDao.accountConfirm(userId.longValue(), price);
        if(flag == 0){
            throw new RuntimeException("账户服务 Confirm 阶段失败.");
        }
        // commit成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        logger.info("[accountConfirm]: 扣减 {} 余额成功", price);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean accountCancel(BusinessActionContext actionContext) {
        String xId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        Integer userId = ((Integer) actionContext.getActionContext("userId"));
        Integer price = ((Integer) actionContext.getActionContext("price"));
        logger.info("[accountCancel]: 当前 XID:{}, branchId:{}, 用户:{}， 金额:{}", xId, branchId, userId, price);

        // 幂等控制，如果 cancel 阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        int flag = accountDao.accountCancel(userId.longValue(), price);
        if(flag == 0){
            throw new RuntimeException("账户服务 Cancel 阶段失败.");
        }

        // cancel 成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        logger.info("[accountCancel]: 解除冻结 {} 余额成功", price);
        return true;
    }
}
