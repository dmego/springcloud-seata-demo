package cn.dmego.seata.tcc.order.dao;

import cn.dmego.seata.common.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @className: OrderDao
 *
 * @description: 订单服务 Dao
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/7 17:27
 **/
@Mapper
@Repository
public interface OrderDao {

    /**
     * Order Try 订单创建中
     * @param order 订单
     * @return 影响的记录行
     */
    @Update("INSERT into orders (id, user_id, product_id, count, pay_amount, status) VALUES (#{id}, #{userId}, #{productId}, #{count}, #{payAmount}, 0)")
    int orderTry(OrderDTO order);

    /**
     * Order Confirm 完成订单
     * @param orderId 账户 ID
     * @return 影响的记录行
     */
    @Update("UPDATE orders set status = 1 WHERE id = #{orderId}")
    int orderConfirm(@Param("orderId") Long orderId);

    /**
     * Order Cancel 取消订单
     * @param orderId 账户 ID
     * @return 影响的记录行
     */
    @Update("UPDATE orders set status = -1 WHERE id = #{orderId}")
    int orderCancel(@Param("orderId") Long orderId);
}
