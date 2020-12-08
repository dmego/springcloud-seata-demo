package cn.dmego.seata.tcc.product.service.impl;

import cn.dmego.seata.common.util.ResultHolder;
import cn.dmego.seata.tcc.product.dao.ProductDao;
import cn.dmego.seata.tcc.product.service.ProductService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: ProductServiceImpl
 *
 * @description: 仓库服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:30
 **/
@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean productTry(BusinessActionContext actionContext, Long productId, Integer count) {
        String xId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        logger.info("[productTry]: 当前 XID:{}, branchId:{}, 商品:{}， 数量:{}", xId, branchId, productId, count);
        int flag = productDao.productTry(productId, count);

        if(flag == 0){
            throw new RuntimeException("库存服务 Try 阶段失败.");
        }

        //事务成功，保存一个标识，供第二阶段进行判断
        ResultHolder.setResult(getClass(), actionContext.getXid(), "p");

        logger.info("[productTry]: 冻结 {} 库存成功", count);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean productConfirm(BusinessActionContext actionContext) {
        String xId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        Integer productId = (Integer) actionContext.getActionContext("productId");
        Integer count = ((Integer) actionContext.getActionContext("count"));
        logger.info("[productConfirm]: 当前 XID:{}, branchId:{}, 商品:{}， 数量:{}", xId, branchId, productId, count);

        // 幂等控制，如果commit阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        int flag = productDao.productConfirm(productId.longValue(), count);
        if(flag == 0){
            throw new RuntimeException("库存服务 Confirm 阶段失败.");
        }
        // commit成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        logger.info("[productConfirm]: 扣除 {} 库存成功", count);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean productCancel(BusinessActionContext actionContext) {
        String xId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        Integer productId = ((Integer) actionContext.getActionContext("productId"));
        Integer count = ((Integer) actionContext.getActionContext("count"));
        logger.info("[productCancel]: 当前 XID:{}, branchId:{}, 商品:{}， 数量:{}", xId, branchId, productId, count);
        // 幂等控制，如果 cancel 阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        int flag = productDao.productCancel(productId.longValue(), count);
        if(flag == 0){
            throw new RuntimeException("库存服务 Cancel 阶段失败.");
        }
        // cancel 成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        logger.info("[productCancel]: 解除冻结 {} 库存成功", count);
        return true;
    }

    @Override
    public Integer getPriceById(Long productId) {
        return productDao.selectPriceById(productId);
    }
}
