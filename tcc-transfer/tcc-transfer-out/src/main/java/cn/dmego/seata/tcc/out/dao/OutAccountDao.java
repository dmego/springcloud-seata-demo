package cn.dmego.seata.tcc.out.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @className: OutAccountDao
 *
 * @description: 转账方Dao
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:33
 **/
@Component
public class OutAccountDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public int amountTry(String accountId, double amount){
        String sql = "UPDATE ACCOUNT SET FREEZED = FREEZED + ? WHERE ID = ? AND BALANCE >= FREEZED + ?";
        return jdbcTemplate.update(sql, amount, accountId, amount);
    }

    public int amountConfirm(String accountId, double amount){
        String sql = "UPDATE ACCOUNT SET BALANCE = BALANCE - ?, FREEZED = FREEZED - ? WHERE ID = ?";
        return jdbcTemplate.update(sql, amount, amount, accountId);
    }

    public int amountCancel(String accountId, double amount){
        String sql = "UPDATE ACCOUNT SET FREEZED = FREEZED - ? WHERE ID = ?";
        return jdbcTemplate.update(sql, amount, accountId);
    }

    public boolean exception(String id, String data){
        String sql = "select * from TEMP where id = ? and data = ?";
        List<Map<String, Object>> map = jdbcTemplate.queryForList(sql, id, data);
        return map.size() != 0;
    }


}
