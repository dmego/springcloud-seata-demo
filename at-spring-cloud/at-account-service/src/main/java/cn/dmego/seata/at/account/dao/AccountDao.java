package cn.dmego.seata.at.account.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @className: AccountDao
 *
 * @description: AccountDao
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:39
 **/
@Mapper
@Repository
public interface AccountDao {

    /**
     * 获取账户余额
     *
     * @param userId 用户 ID
     * @return 账户余额
     */
    @Select("SELECT balance FROM account WHERE id = #{userId}")
    Integer getBalance(@Param("userId") Long userId);

    /**
     * 扣减余额
     *
     * @param amount 需要扣减的金额
     * @return 影响记录行数
     */
    @Update("UPDATE account SET balance = balance - #{amount} WHERE id = #{userId} AND balance >= ${amount}")
    int reduceBalance(@Param("userId") Long userId, @Param("amount") Integer amount);

}
