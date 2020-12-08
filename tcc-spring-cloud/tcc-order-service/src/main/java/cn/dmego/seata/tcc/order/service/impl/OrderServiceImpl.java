package cn.dmego.seata.tcc.order.service.impl;

import cn.dmego.seata.common.dto.OrderDTO;
import cn.dmego.seata.common.util.ResultHolder;
import cn.dmego.seata.tcc.order.dao.OrderDao;
import cn.dmego.seata.tcc.order.proxy.AccountService;
import cn.dmego.seata.tcc.order.service.OrderService;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: OrderServiceImpl
 *
 * @description: 订单服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/6 17:21
 **/
@Service
public class OrderServiceImpl implements OrderService {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AccountService accountService;

    @Autowired
    private OrderDao orderDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean orderTry(BusinessActionContext actionContext, Long orderId, Long userId, Long productId, Integer count, Integer payAmount) {
        String xId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        OrderDTO orderDTO = new OrderDTO(orderId, userId, productId, count, payAmount);
        logger.info("[orderTry]: 当前 XID:{}, branchId:{}, 订单:{}", xId, branchId, orderDTO.toString());
        // 扣减余额 Try
        boolean aTry = accountService.accountTry(actionContext, userId, payAmount);
        if(!aTry){
            throw new RuntimeException("账户服务 Try 阶段失败.");
        }

        //创建订单 Try
        int flag = orderDao.orderTry(orderDTO);
        if(flag == 0){
            throw new RuntimeException("订单服务 Try 阶段失败.");
        }
        //事务成功，保存一个标识，供第二阶段进行判断
        ResultHolder.setResult(getClass(), actionContext.getXid(), "p");

        logger.info("[orderTry]: 阶段成功");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean orderConfirm(BusinessActionContext actionContext) {
        String xId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        Long orderId = ((Long) actionContext.getActionContext("orderId"));
        logger.info("[orderConfirm]: 当前 XID:{}, branchId:{}, 订单ID:{}", xId, branchId, orderId);
        // 幂等控制，如果commit阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        int flag = orderDao.orderConfirm(orderId);
        if(flag == 0){
            throw new RuntimeException("订单服务 Confirm 阶段失败.");
        }
        // commit成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        logger.info("[orderConfirm]: 阶段成功");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean orderCancel(BusinessActionContext actionContext) {
        String xId = actionContext.getXid();
        long branchId = actionContext.getBranchId();
        Long orderId = ((Long) actionContext.getActionContext("orderId"));
        logger.info("[orderCancel]: 当前 XID:{}, branchId:{}, 订单ID:{}", xId, branchId, orderId);
        // 幂等控制，如果 cancel 阶段重复执行则直接返回
        if (ResultHolder.getResult(getClass(), actionContext.getXid()) == null) {
            return true;
        }

        int flag = orderDao.orderCancel(orderId);
        if(flag == 0){
            throw new RuntimeException("订单服务 Cancel 阶段失败.");
        }
        // cancel 成功删除标识
        ResultHolder.removeResult(getClass(), actionContext.getXid());
        logger.info("[orderCancel]: 阶段成功");
        return true;
    }
}
