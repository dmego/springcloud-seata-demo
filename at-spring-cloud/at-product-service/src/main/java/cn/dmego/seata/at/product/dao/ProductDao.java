package cn.dmego.seata.at.product.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @className: ProductDao
 *
 * @description: ProductDao
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:42
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
     * 获取库存
     *
     * @param productId 商品编号
     * @return 库存
     */
    @Select("SELECT stock FROM product WHERE id = #{productId}")
    Integer getStock(@Param("productId") Long productId);

    /**
     * 扣减库存
     *
     * @param productId 商品编号
     * @param count    扣减数量
     * @return 影响记录行数
     */
    @Update("UPDATE product SET stock = stock - #{count} WHERE id = #{productId} AND stock >= #{count}")
    int reduceStock(@Param("productId") Long productId, @Param("count") Integer count);

}
