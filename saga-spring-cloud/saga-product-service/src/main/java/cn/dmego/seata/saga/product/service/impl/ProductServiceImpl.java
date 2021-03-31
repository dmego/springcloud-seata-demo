package cn.dmego.seata.saga.product.service.impl;

import cn.dmego.seata.saga.product.dao.ProductDao;
import cn.dmego.seata.saga.product.service.ProductService;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProductServiceImpl
 *
 * @author dmego
 * @date 2021/3/31 10:53
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean reduceStock(Long productId, Integer count) throws Exception {
        logger.info("[reduceStock] 开始扣减库存, userId:{}, count: {}", productId, count);
        logger.info("[reduceStock] XID: {}", RootContext.getXID());

        // 检查库存
        checkStock(productId, count);

        int result = productDao.reduceStock(productId, count);
        if(result == 0){
            logger.warn("[reduceBalance] 扣减库存失败, productId:{}, count: {} ", productId, count);
            return false;
        }
        logger.info("[reduceBalance] 扣减库存成功, productId:{}, count: {}", productId, count);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean compensateStock(Long productId, Integer count) throws Exception {
        logger.info("[compensateStock] 开始回滚库存, userId:{}, count: {}", productId, count);
        logger.info("[compensateStock] XID: {}", RootContext.getXID());

        int result = productDao.compensateStock(productId, count);
        if(result == 0){
            logger.warn("[compensateStock] 回滚库存失败, productId:{}, count: {} ", productId, count);
            return false;
        }
        logger.info("[compensateStock] 回滚库存成功, productId:{}, count: {}", productId, count);
        return true;
    }

    @Override
    public Integer getPriceById(Long productId) {
        return productDao.selectPriceById(productId);
    }

    private void checkStock(Long productId, Integer count) throws Exception {
        logger.info("[checkStock] 检查商品 {} 库存", productId);
        Integer stock = productDao.getStock(productId);
        if (stock < count) {
            logger.warn("[checkStock] 商品 {} 库存不足，当前库存: {}", productId, stock);
            throw new Exception("库存不足");
        }
    }
}
