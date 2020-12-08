package cn.dmego.seata.common.dto;

/**
 * @className: ProductDTO
 *
 * @description: 仓库服务DTO
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:38
 **/
public class ProductDTO {

    /**
     * 商品编号
     */
    private Long productId;

    /**
     * 扣减数量
     */
    private Integer count;

    public ProductDTO() {
    }

    public ProductDTO(Long productId, Integer count) {
        this.productId = productId;
        this.count = count;
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
}
