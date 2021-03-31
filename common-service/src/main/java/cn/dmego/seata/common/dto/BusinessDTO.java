package cn.dmego.seata.common.dto;

/**
 * @className: BusinessDTO
 *
 * @description: 业务服务DTO
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:38
 **/
public class BusinessDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 购买商品数量
     */
    private Integer count;

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

    @Override
    public String toString() {
        return "BusinessDTO{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", count=" + count +
                '}';
    }
}