package cn.dmego.seata.tcc.account.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @className: AccountDao
 *
 * @description: 账户服务 Dao
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/5 17:23
 **/
@Mapper
@Repository
public interface AccountDao {

    /**
     * Account Try 冻结余额
     * @param userId 用户 ID
     * @param amount 冻结的余额
     * @return 影响的记录行
     */
    @Update("UPDATE account set frozen = frozen + #{amount} WHERE id = #{userId} AND balance >= frozen + #{amount}")
    int accountTry(@Param("userId") Long userId, @Param("amount") Integer amount);

    /**
     * Account Confirm 正式扣减余额，释放冻结余额
     * @param userId 用户 ID
     * @param amount 冻结的余额
     * @return 影响的记录行
     */
    @Update("UPDATE account set frozen = frozen - #{amount}, balance = balance - #{amount} WHERE id = #{userId}")
    int accountConfirm(@Param("userId") Long userId, @Param("amount") Integer amount);

    /**
     * Account Cancel 释放冻结余额
     * @param userId 用户 ID
     * @param amount 冻结的余额
     * @return 影响的记录行
     */
    @Update("UPDATE account set frozen = frozen - #{amount} WHERE id = #{userId}")
    int accountCancel(@Param("userId") Long userId, @Param("amount") Integer amount);

}
