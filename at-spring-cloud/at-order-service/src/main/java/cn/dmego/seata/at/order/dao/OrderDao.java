package cn.dmego.seata.at.order.dao;


import cn.dmego.seata.common.dto.OrderDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @className: OrderDao
 *
 * @description: OrderDao
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:41
 **/
@Mapper
@Repository
public interface OrderDao {

    /**
     * 插入订单记录
     *
     * @param order 订单
     * @return 影响记录数量
     */
    @Insert("INSERT INTO orders (id, user_id, product_id, count, pay_amount, status) VALUES (#{id}, #{userId}, #{productId}, #{count}, #{payAmount}, 1)")
    int saveOrder(OrderDTO order);

}
