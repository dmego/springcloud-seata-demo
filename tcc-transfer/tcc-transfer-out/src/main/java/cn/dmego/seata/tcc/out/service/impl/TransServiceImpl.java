package cn.dmego.seata.tcc.out.service.impl;


import cn.dmego.seata.tcc.out.entity.Transfer;
import cn.dmego.seata.tcc.out.proxy.InAccountService;
import cn.dmego.seata.tcc.out.service.IOutAccountService;
import cn.dmego.seata.tcc.out.service.ITransService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @className: TransServiceImpl
 *
 * @description: 转账业务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:35
 **/
@Service
public class TransServiceImpl implements ITransService {

    private static final Logger log = LoggerFactory.getLogger(TransServiceImpl.class);


    @Autowired
    private IOutAccountService outAccountService;

    @Autowired
    private InAccountService inAccountService;

    @Override
    @GlobalTransactional
    public boolean transferAmount(Transfer transfer) {
        long s = System.currentTimeMillis();
        // 执行转钱方 Try
        boolean result = outAccountService.outTry(transfer.getOutId(), transfer.getAmount());
        if(!result){
            throw new RuntimeException("转账方转钱失败");
        }
        // 执行收钱方 Try
        result = inAccountService.inTry(transfer.getInId(), transfer.getAmount());
        if(!result){
            throw new RuntimeException("收钱方收钱失败");
        }

        long e = System.currentTimeMillis();
        log.info("transferAmount used time :" +  (e - s) + "ms");
        return true;
    }

    @Override
    @GlobalTransactional
    public boolean transferAmount2(Transfer transfer) {
        long s = System.currentTimeMillis();
        // 执行转钱方 Try2
        boolean result = outAccountService.outTry2(transfer.getOutId(), transfer.getAmount());
        if(!result){
            throw new RuntimeException("转账方转钱失败");
        }
        // 执行收钱方 Try2
        result = inAccountService.inTry2(transfer.getInId(), transfer.getAmount());
        if(!result){
            throw new RuntimeException("收钱方收钱失败");
        }

        long e = System.currentTimeMillis();
        log.info("transferAmount2 used time :" +  (e - s) + "ms");
        return true;
    }
}
