package cn.dmego.seata.common.dto;

/**
 * @className: OrderDTO
 *
 * @description: 订单服务DTO
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:38
 **/
public class OrderDTO {

    private Long id;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 购买的商品数量
     */
    private Integer count;

    /**
     * 订单总金额
     */
    private Integer payAmount;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Long userId, Long productId, Integer count, Integer payAmount) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
        this.payAmount = payAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", count=" + count +
                ", payAmount=" + payAmount +
                '}';
    }
}
