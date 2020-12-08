package cn.dmego.seata.at.business.serivce.impl;


import cn.dmego.seata.at.business.proxy.OrderService;
import cn.dmego.seata.at.business.proxy.ProductService;
import cn.dmego.seata.at.business.serivce.BusinessService;
import cn.dmego.seata.common.dto.BusinessDTO;
import cn.dmego.seata.common.dto.OrderDTO;
import cn.dmego.seata.common.dto.ProductDTO;
import cn.dmego.seata.common.util.IDUtils;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: BusinessServiceImpl
 *
 * @description: BusinessServiceImpl
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:40
 **/
@Service
public class BusinessServiceImpl implements BusinessService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Override
    @GlobalTransactional
    public String handleBusiness(BusinessDTO businessDTO) {

        logger.info("[handleBusiness] 开始下单");
        logger.info("[handleBusiness] 当前 XID: {}", RootContext.getXID());

        // 扣减库存
        boolean reduceStock = productService.reduceStock(new ProductDTO(businessDTO.getProductId(), businessDTO.getCount()));

        // 查询 商品单价
        Integer price = productService.getPrice(businessDTO.getProductId());
        Integer payAmount = price * businessDTO.getCount();

        // 生成订单 ID
        Long orderId = IDUtils.nextId();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);
        orderDTO.setUserId(businessDTO.getUserId());
        orderDTO.setProductId(businessDTO.getProductId());
        orderDTO.setCount(businessDTO.getCount());
        orderDTO.setPayAmount(payAmount);
        // 创建订单
        boolean createOrder = orderService.createOrder(orderDTO);

        if(!reduceStock || !createOrder){
            throw new RuntimeException("下单失败");
        }

        logger.info("[handleBusiness] 下单成功, 订单Id: "+ orderId);
        return "Place Order Success";
    }
}
