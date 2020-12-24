package cn.dmego.seata.tcc.in.dao;

import cn.dmego.seata.tcc.in.entity.Account;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        String sql = "UPDATE account SET incoming = incoming + ? WHERE id = ?";
        return jdbcTemplate.update(sql, amount, accountId);
    }

    public int inComingConfirm(String accountId, double amount) {
        String sql = "UPDATE account SET balance = balance + ?, incoming = incoming - ? WHERE id = ?";
        return jdbcTemplate.update(sql, amount, amount, accountId);
    }

    public int inComingCancel(String accountId, double amount) {
        String sql = "UPDATE account SET incoming = incoming - ? WHERE id = ?";
        return jdbcTemplate.update(sql, amount, accountId);
    }

    public boolean exception(String id, String data){
        String sql = "select * from temp where id = ? and data = ?";
        List<Map<String, Object>> map = jdbcTemplate.queryForList(sql, id, data);
        return map.size() != 0;
    }

    public int[] init(List<Account> accounts){
        String sql = "insert into account(id, balance, freezed, incoming) VALUES (?, ?, ?, ?)";
        return this.jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, accounts.get(i).getId());
                        ps.setString(2, accounts.get(i).getBalance());
                        ps.setString(3, accounts.get(i).getFreezed());
                        ps.setString(4, accounts.get(i).getIncoming());
                    }
                    public int getBatchSize() {
                        return accounts.size();
                    }
                });
    }

    public int[] delete(List<String> ids){
        String sql = "delete from account where id = ?";
        return this.jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, ids.get(i));
                    }
                    public int getBatchSize() {
                        return ids.size();
                    }
                });
    }



}
