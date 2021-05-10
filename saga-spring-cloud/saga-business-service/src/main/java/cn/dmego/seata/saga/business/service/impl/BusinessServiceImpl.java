package cn.dmego.seata.saga.business.service.impl;

import cn.dmego.seata.common.dto.BusinessDTO;
import cn.dmego.seata.common.util.IDUtils;
import cn.dmego.seata.saga.business.proxy.ProductService;
import cn.dmego.seata.saga.business.service.BusinessService;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * BusinessServiceImpl
 *
 * @author dmego
 * @date 2021/3/31 10:48
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ProductService productService;

    @Autowired
    StateMachineEngine stateMachineEngine;

    @Override
    public String handlerBusiness(BusinessDTO businessDTO) {
        logger.info("[handleBusiness] 开始下单, 订单详情: {}", businessDTO.toString());

        // 查询 商品单价
        Integer price = productService.getPrice(businessDTO.getProductId());
        Integer payAmount = price * businessDTO.getCount();
        logger.info("[handleBusiness] 订单总价格: {}", payAmount);

        // 生成订单 ID
        Long orderId = IDUtils.nextId();

        Map<String, Object> businessParam = new HashMap<>();
        businessParam.put("orderId", orderId);
        businessParam.put("userId", businessDTO.getUserId());
        businessParam.put("productId", businessDTO.getProductId());
        businessParam.put("count", businessDTO.getCount());
        businessParam.put("amount", payAmount);

        StateMachineInstance instance = stateMachineEngine.start("BusinessOrder", null, businessParam);
        if(ExecutionStatus.SU.equals(instance.getStatus())) {
            logger.info("[handleBusiness] 下单成功, 响应结果: {} ", instance.getStatus().getStatusString());
        } else {
            logger.error("[handleBusiness] 下单失败, 响应结果: {} ", instance.getStatus().getStatusString());
        }
        return instance.getStatus().getStatusString();
    }
}
