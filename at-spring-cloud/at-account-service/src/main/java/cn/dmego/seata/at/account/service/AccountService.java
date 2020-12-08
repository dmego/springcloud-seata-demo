package cn.dmego.seata.at.account.service;

/**
 * @className: AccountService
 *
 * @description: AccountService
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:39
 **/
public interface AccountService {

    /**
     * 扣除余额
     *
     * @param userId 用户编号
     * @param amount  扣减金额
     * @throws Exception 失败时抛出异常
     */
    boolean reduceBalance(Long userId, Integer amount) throws Exception;

}
