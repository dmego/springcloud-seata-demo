package cn.dmego.seata.at.product.service.impl;


import cn.dmego.seata.at.product.dao.ProductDao;
import cn.dmego.seata.at.product.service.ProductService;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: ProductServiceImpl
 *
 * @description: ProductServiceImpl
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:42
 **/
@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional // 开启新事物
    public boolean reduceStock(Long productId, Integer count) throws Exception {
        logger.info("[reduceStock] 当前 XID: {}", RootContext.getXID());

        // 检查库存
        checkStock(productId, count);

        logger.info("[reduceStock] 开始扣减 {} 库存", productId);
        // 扣减库存
        int updateCount = productDao.reduceStock(productId, count);
        // 扣除失败
        if (updateCount == 0) {
            logger.warn("[reduceStock] 扣除 {} 库存失败", productId);
            throw new Exception("库存不足");
        }
        // 扣除成功
        logger.info("[reduceStock] 扣除 {} 库存成功", productId);
        return true;
    }

    @Override
    public Integer getPriceById(Long productId) {
        return productDao.selectPriceById(productId);
    }

    private void checkStock(Long productId, Integer count) throws Exception {
        logger.info("[checkStock] 检查 {} 库存", productId);
        Integer stock = productDao.getStock(productId);
        if (stock < count) {
            logger.warn("[checkStock] {} 库存不足，当前库存: {}", productId, stock);
            throw new Exception("库存不足");
        }
    }

}
