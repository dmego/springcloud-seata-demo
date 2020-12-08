package cn.dmego.seata.tcc.out.entity;

import java.io.Serializable;

/**
 * @className: Transfer
 *
 * @description: 转账 DTO
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:33
 **/
public class Transfer implements Serializable {

    /**
     * 收钱账户ID
     */
    private String inId;

    /**
     * 转账账户ID
     */
    private String outId;

    /**
     * 转账金额
     */
    private double amount;

    public String getInId() {
        return inId;
    }

    public void setInId(String inId) {
        this.inId = inId;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
