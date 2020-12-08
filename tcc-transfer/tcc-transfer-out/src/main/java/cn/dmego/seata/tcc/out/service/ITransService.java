package cn.dmego.seata.tcc.out.service;


import cn.dmego.seata.tcc.out.entity.Transfer;

/**
 * @className: ITransService
 *
 * @description: 转账业务接口
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:35
 **/
public interface ITransService {

    boolean transferAmount(Transfer transfer);
}
