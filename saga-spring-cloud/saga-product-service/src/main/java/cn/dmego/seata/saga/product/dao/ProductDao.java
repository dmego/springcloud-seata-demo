package cn.dmego.seata.saga.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * ProductDao
 * 
 * @author dmego
 * @date 2021/3/31 10:55
 */
@Mapper
@Repository
public interface ProductDao {

    @Select("select stock from product where id = #{productId}")
    Integer getStock(@Param("productId") Long productId);

    @Select("select price from product where id = #{productId}")
    Integer selectPriceById(@Param("productId") Long productId);

    @Update("update product set stock = stock - #{count} where id = #{productId} and stock >= #{count}")
    int reduceStock(@Param("productId") Long productId, @Param("count") Integer count);

    @Update("update product set stock = stock + #{count} where id = #{productId}")
    int compensateStock(@Param("productId") Long productId, @Param("count") Integer count);
}
