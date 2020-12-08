package cn.dmego.seata.tcc.in.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @className: InAccountDao
 *
 * @description: 收钱方
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:31
 **/
@Component
public class InAccountDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public int inComingTry(String accountId, double amount) {
        String sql = "UPDATE ACCOUNT SET INCOMING = INCOMING + ? WHERE ID = ?";
        return jdbcTemplate.update(sql, amount, accountId);
    }

    public int inComingConfirm(String accountId, double amount) {
        String sql = "UPDATE ACCOUNT SET BALANCE = BALANCE + ?, INCOMING = INCOMING - ? WHERE ID = ?";
        return jdbcTemplate.update(sql, amount, amount, accountId);
    }

    public int inComingCancel(String accountId, double amount) {
        String sql = "UPDATE ACCOUNT SET INCOMING = INCOMING - ? WHERE ID = ?";
        return jdbcTemplate.update(sql, amount, accountId);
    }

    public boolean exception(String id, String data){
        String sql = "select * from TEMP where id = ? and data = ?";
        List<Map<String, Object>> map = jdbcTemplate.queryForList(sql, id, data);
        return map.size() != 0;
    }

}
