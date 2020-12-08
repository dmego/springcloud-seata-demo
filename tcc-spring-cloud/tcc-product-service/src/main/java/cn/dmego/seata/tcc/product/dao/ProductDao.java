package cn.dmego.seata.tcc.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @className: ProductDao
 *
 * @description: 仓库服务 Dao
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:29
 **/
@Mapper
@Repository
public interface ProductDao {

    /**
     * 根据商品 ID 查询单价
     * @param productId 商品ID
     * @return 商品单价
     */
    @Select("SELECT price FROM product where id = #{productId}")
    Integer selectPriceById(@Param("productId") Long productId);

    /**
     * product Try 冻结余额
     * @param productId 商品 ID
     * @param count 购买数量
     * @return 影响的记录行
     */
    @Update("UPDATE product set frozen = frozen + #{count} WHERE id = #{productId} AND stock >= frozen + #{count}")
    int productTry(@Param("productId") Long productId, @Param("count") Integer count);

    /**
     * product Confirm 正式扣减余额，释放冻结余额
     * @param productId 商品 ID
     * @param count 购买数量
     * @return 影响的记录行
     */
    @Update("UPDATE product set frozen = frozen - #{count}, stock = stock - #{count} WHERE id = #{productId}")
    int productConfirm(@Param("productId") Long productId, @Param("count") Integer count);

    /**
     * product Cancel 释放冻结余额
     * @param productId 商品 ID
     * @param count 购买数量
     * @return 影响的记录行
     */
    @Update("UPDATE product set frozen = frozen - #{count} WHERE id = #{productId}")
    int productCancel(@Param("productId") Long productId, @Param("count") Integer count);

}
