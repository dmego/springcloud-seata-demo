# springcloud-seata-demo

SpringCloud 下的 Seata 分布式事务， AT 模式和 TCC 模式

## 场景说明

用户购买商品的业务逻辑。整个业务逻辑由3个微服务提供支持：

- 仓储服务：对给定的商品扣除仓储数量。
- 订单服务：根据采购需求创建订单。
- 帐户服务：从用户帐户中扣除余额。

### 架构图

<img src="https://cdn.jsdelivr.net/gh/dmego/images@master/img/20201209085813.png" alt="architecture" style="zoom:67%;" />

### SEATA 的分布式交易解决方案

<img src="https://cdn.jsdelivr.net/gh/dmego/images@master/img/20201209085923.png" alt="solution" style="zoom:67%;" />

## 环境说明
```yaml
mysql: 5.7
consul: 1.9.2
seata: 1.4.0
spring-boot: 2.3.2.RELEASE
spring-cloud: Hoxton.SR8
spring-cloud-alibaba: 2.2.3.RELEASE
```

## 工程目录结构

```
springcloud-seata-demo
├── at-spring-cloud            // AT 模式工程示例
│  ├── at-account-service      // AT-账户服务
│  ├── at-business-service     // AT-业务服务
│  ├── at-order-service        // AT-订单服务
│  └── at-product-service      // AT-仓库服务
├── common-parent              // 父POM工程
├── common-service             // 公共服务
├── tcc-spring-cloud           // TCC 模式工程示例 
│  ├── tcc-account-service     // TCC-账户服务
│  ├── tcc-business-service    // TCC-业务服务
│  ├── tcc-order-service       // TCC-订单服务
│  └── tcc-product-service     // TCC-仓库服务
├── tcc-transfer               // TCC 模式转账工程示例
│  ├── sql                     // 转账示例 sql 脚本
│  ├── tcc-transfer-in         // 收钱方服务 
│  └── tcc-trnasfer-out        // 转账方服务
└── zsql                       // AT 和 TCC 订单库存示例 sql
```
