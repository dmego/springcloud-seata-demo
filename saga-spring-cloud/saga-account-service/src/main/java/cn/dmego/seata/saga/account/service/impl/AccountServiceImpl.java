package cn.dmego.seata.saga.account.service.impl;

import cn.dmego.seata.saga.account.dao.AccountDao;
import cn.dmego.seata.saga.account.service.AccountService;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AccountServiceImpl
 *
 * @author dmego
 * @date 2021/3/31 10:46
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AccountDao accountDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean reduceBalance(Long userId, Integer amount) throws Exception {
        logger.info("[reduceBalance] 开始扣减余额, userId:{}, amount: {}", userId, amount);
        logger.info("[reduceBalance] XID: {}", RootContext.getXID());

        // 检查余额
        checkBalance(userId, amount);

        int result = accountDao.reduceBalance(userId, amount);
        if(result == 0){
            logger.warn("[reduceBalance] 扣减余额失败, userId:{}, amount: {} ", userId, amount);
            return false;
        }
        logger.info("[reduceBalance] 扣减余额成功, userId:{}, amount: {}", userId, amount);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean compensateBalance(Long userId, Integer amount) throws Exception {
        logger.info("[compensateBalance] 开始回滚余额, userId:{}, amount: {}", userId, amount);
        logger.info("[compensateBalance] XID: {}", RootContext.getXID());

        int result = accountDao.compensateBalance(userId, amount);
        if(result == 0){
            logger.warn("[compensateBalance] 回滚余额失败, userId:{}, amount: {}", userId, amount);
            return false;
        }
        logger.info("[compensateBalance] 回滚余额成功, userId:{}, amount: {}", userId, amount);
        return true;
    }

    private void checkBalance(Long userId, Integer price) throws Exception {
        logger.info("[checkBalance] 检查用户 {} 余额", userId);
        Integer balance = accountDao.getBalance(userId);
        if (balance < price) {
            logger.warn("[checkBalance] 用户 {} 余额不足，当前余额:{}", userId, balance);
            throw new Exception("余额不足");
        }
    }
}
