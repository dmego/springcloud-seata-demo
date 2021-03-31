package cn.dmego.seata.saga.product.service;


/**
 * ProductService
 *
 * @author dmego
 * @date 2021/3/31 10:53
 */
public interface ProductService {

    Boolean reduceStock(Long productId, Integer count) throws Exception;

    Boolean compensateStock(Long productId, Integer count) throws Exception;

    Integer getPriceById(Long productId);
}
