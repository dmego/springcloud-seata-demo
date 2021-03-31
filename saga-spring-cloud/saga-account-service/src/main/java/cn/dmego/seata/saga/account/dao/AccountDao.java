package cn.dmego.seata.saga.account.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * AccountDao
 *
 * @author dmego
 * @date 2021/3/31 10:46
 */
@Mapper
@Repository
public interface AccountDao {

    @Select("select balance from account where id = #{userId}")
    Integer getBalance(@Param("userId") Long userId);

    @Update("update account set balance = balance - #{amount} where id = #{userId} and balance >= #{amount}")
    int reduceBalance(@Param("userId") Long userId, @Param("amount") Integer amount);


    @Update("update account set balance = balance + #{amount} where id = #{userId}")
    int compensateBalance(@Param("userId") Long userId, @Param("amount") Integer amount);

}
