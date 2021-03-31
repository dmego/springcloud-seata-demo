package cn.dmego.seata.saga.account.service;

/**
 * AccountService
 *
 * @author dmego
 * @date 2021/3/31 10:46
 */
public interface AccountService {

    Boolean reduceBalance(Long userId, Integer amount) throws Exception;

    Boolean compensateBalance(Long userId, Integer amount) throws Exception;
}
