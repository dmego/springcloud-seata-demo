package cn.dmego.seata.saga.order.service.impl;

import cn.dmego.seata.common.dto.OrderDTO;
import cn.dmego.seata.saga.order.dao.OrderDao;
import cn.dmego.seata.saga.order.service.OrderService;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OrderServiceImpl
 *
 * @author dmego
 * @date 2021/3/31 10:51
 */
@Service
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    OrderDao orderDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean createOrder(Long orderId, Long userId, Long productId, Integer amount, Integer count) throws Exception {
        OrderDTO orderDTO = new OrderDTO(orderId, userId, productId, count, amount);
        logger.info("[createOrder] 开始创建订单: {}", orderDTO.toString());
        logger.info("[createOrder] XID: {}", RootContext.getXID());

        int result = orderDao.createOrder(orderDTO);
        if(result == 0){
            logger.warn("[createOrder] 创建订单 {} 失败", orderDTO.toString());
            return false;
        }
        logger.info("[createOrder] 保存订单成功: {}", orderDTO.getId());
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean revokeOrder(Long orderId) throws Exception {
        logger.info("[revokeOrder] 开始撤销订单, orderId: {}", orderId);
        logger.info("[revokeOrder] XID: {}", RootContext.getXID());

        int result = orderDao.revokeOrder(orderId);
        if(result == 0){
            logger.warn("[revokeOrder] 撤销订单 {} 失败",orderId);
            return false;
        }

        logger.info("[revokeOrder] 撤销订单成功: {}", orderId);
        return true;

    }
}
