package cn.dmego.seata.saga.business.config;

import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.engine.config.DbStateMachineConfig;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.rm.StateMachineEngineHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * SeataSagaConfig
 *
 * @author dmego
 * @date 2021/3/31 10:47
 */
@Configuration
public class SeataSagaConfig {

    @Value("${spring.application.name}")
    private String applicationId;

    @Value("${seata.tx-service-group}")
    private String txServiceGroup;

    @Bean
    public StateMachineEngine getStateMachineEngine(DbStateMachineConfig dbStateMachineConfig) {
        ProcessCtrlStateMachineEngine stateMachineEngine = new ProcessCtrlStateMachineEngine();
        stateMachineEngine.setStateMachineConfig(dbStateMachineConfig);
        return stateMachineEngine;
    }

    /**
     *
     * 这里配置了json文件的路径，TM在初始化的时候，会把json文件解析成StateMachineImpl类，如果数据库没有保存这个状态机，
     * 则存入数据库seata_state_machine_def表，
     * 如果数据库有记录，则取最新的一条记录，并且注册到StateMachineRepositoryImpl，
     * 注册的Map有2个，一个是stateMachineMapByNameAndTenant，key格式是(stateMachineName + "_" + tenantId),
     * 一个是stateMachineMapById，key是stateMachine.getId()
     * 具体代码见StateMachineRepositoryImpl类registryStateMachine方法
     * 这个注册的触发方法在DefaultStateMachineConfig的初始化方法init()，这个类是DbStateMachineConfig的父类
     *
     * @param dataSource dateSource
     * @param threadPoolExecutor threadPoolExecutor
     * @return
     * @throws IOException
     */
    @Bean
    public DbStateMachineConfig getDbStateMachineConfig(DataSource dataSource, ThreadPoolExecutor threadPoolExecutor) throws IOException {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath:statelang/*.json");

        DbStateMachineConfig dbStateMachineConfig = new DbStateMachineConfig();
        dbStateMachineConfig.setDataSource(dataSource);
        dbStateMachineConfig.setResources(resources);
        dbStateMachineConfig.setEnableAsync(true);
        dbStateMachineConfig.setThreadPoolExecutor(threadPoolExecutor);
        dbStateMachineConfig.setApplicationId(applicationId);
        dbStateMachineConfig.setTxServiceGroup(txServiceGroup);
        return dbStateMachineConfig;
    }

    /**
     *  事件驱动执行时使用的线程池，如果所有状态机都同步执行可以不需要这个线程池
     * @return thead pool executor
     */
    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor() {
        ThreadPoolTaskExecutor threadExecutor = new ThreadPoolTaskExecutor();
        threadExecutor.initialize();
        threadExecutor.setThreadNamePrefix("SAGA_ASYNC_EXE_");
        threadExecutor.setCorePoolSize(1);
        threadExecutor.setMaxPoolSize(20);
        return threadExecutor.getThreadPoolExecutor();
    }

    /**
     * Seata Server 进行事务恢复时需要通过这个 Holder 拿到 stateMachineEngine 实例
     * @param stateMachineEngine stateMachineEngine
     * @return stateMachineEngineHolder
     */
    @Bean
    public StateMachineEngineHolder getStateMachineEngineHolder(StateMachineEngine stateMachineEngine){
        StateMachineEngineHolder stateMachineEngineHolder = new StateMachineEngineHolder();
        stateMachineEngineHolder.setStateMachineEngine(stateMachineEngine);
        return stateMachineEngineHolder;
    }



}
