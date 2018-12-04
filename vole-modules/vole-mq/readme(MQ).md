##MQ技术栈

### 一，RabbitMQ (mq-service)
官网：https://www.rabbitmq.com
   
    参考：http://blog.didispace.com/spring-boot-rabbitmq/
    RabbitMQ就是以AMQP协议实现的，支持多种操作系统，多种编程语言，几乎可以覆盖所有主流的企业级技术平台。
    AMQP是Advanced Message Queuing Protocol的简称，它是一个面向消息中间件的开放式标准应用层协议。

#### Spring Cloud Stream（消息驱动）

    微服务应用构建消息驱动能力的框架。整合了Spring Boot和Spring Integration，实现了一套轻量级的消息驱动的微服务框架
    通过使用Spring Integration来连接消息代理中间件以实现消息事件驱动的微服务应用
    支持消息中间件：RabbitMQ
    具备消费组 + 消息分片

#### 操作说明：
1，服务端安装版本：

    Erlang/OTP 20.3   http://www.erlang.org/downloads获取exe安装包
    RabbitMQ Server 3.7.5   https://www.rabbitmq.com/download.html获取exe安装包
    RabbitMQ Server安装完成之后，会自动的注册为服务：RabbitMQ，并以默认配置启动起来

2，启动mq-service：
    
    2.1 启动mq-service，访问 http://localhost:8500/send
    2.2 查看控制台消息发送/接受日志

3，开启Web管理后台：
    
    cd ..\rabbitmq_server-3.7.5\sbin
    执行命令：rabbitmq-plugins enable rabbitmq_management
    打开浏览器并访问：http://localhost:15672/，登录用户 guest，密码 guest

### 二，Kafka (mq-service)
官网：http://kafka.apache.org/

#### 操作说明：
1, 安装Zookeeper

    下载安装文件： http://zookeeper.apache.org/releases.html
    http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.12/
    
    ..\conf，把zoo_sample.cfg重命名成zoo.cfg
    运行Zookeeper,打开cmd然后执行: zkserver

2, 安装并运行Kafka

    下载安装文件： http://kafka.apache.org/downloads.html
    http://mirror.bit.edu.cn/apache/kafka/1.1.0/kafka_2.12-1.1.0.tgz 

3, (windows下)开启kafka

    cd ..\bin\windows\
    kafka-server-start.bat ‪D:\work\env\kafka_2.12-1.1.0\config\server.properties

4, 启动mq-service
    
    4.1 启用KafkaConsumer.java及KafkaSender.java相关注解；
    4.2 启动mq-service
    4.3 看到控制台，每30秒发送并接受一次消息日志

5，cmd方式测试kafka

    5.1 创建topics
        kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
    5.2 打开一个Producer
        kafka-console-producer.bat --broker-list localhost:9092 --topic test
    5.3 打开一个Consumer
        kafka-console-consumer.bat --zookeeper localhost:2181 --topic test

### 三，RocketMQ (mq-service)
  官网：http://rocketmq.apache.org/

#### 操作说明：
1，安装并运行rocketmq

      下载安装binary release文件： http://mirror.bit.edu.cn/apache/rocketmq/4.2.0/rocketmq-all-4.2.0-bin-release.zip 
      设置环境变量：
      ROCKETMQ_HOME=D:\work\env\apache-rocketmq
      NAMESRV_ADDR=localhost:9876
  
2, Start Name Server

    http://rocketmq.apache.org/docs/quick-start/
    运行：..\apache-rocketmq\bin>mqnamesrv

3, Start Broker

    运行：..\apache-rocketmq\bin>mqbroker.cmd -n localhost:9876

4, 启动mq-service

5，cmd方式测试rocketmq

    5.1 打开一个Producer
    运行：..apache-rocketmq\bin>tools.cmd org.apache.rocketmq.example.quickstart.Producer
    5.2 打开一个Consumer
    运行：..apache-rocketmq\bin>tools.cmd org.apache.rocketmq.example.quickstart.Consumer
    5.3 Shutdown Server
    运行：..apache-rocketmq\bin>mqshutdown broker
    运行：..apache-rocketmq\bin>mqshutdown namesrv
