opt-sdk各版本升级日志：

*2016-08-24 2.2-SNAPSHOT 
1）升级iPaaS-SES组件为0.3版本。注：0.3版本的ses组件与0.2.x系列差距大 不兼容

*2016-07-04 2.1-SNAPSHOT 
1）opt-sdk统一升级为2.1-SNAPSHOT。
2）为满足中信项目的需要，CCS、MCS、MDS、DSS组件 以iPaaS SDK的方式进行调用，不采用ipaas申请服务的方式调用。其他项目仍按照ipaas服务申请的方式调用
3）升级后，原先的代码无需做任何更改，只需更改paas-conf.properties，并往CCS中添加相应的配置即可。中信项目 kafka的版本必须是kafka_2.11-0.9.0.1，以保持和ipaas sdk的兼容
4）加入分布式任务DTS组件


*2016-06-20 2.0-SNAPSHOT 
1）Rest服务的升级改造，解决了同一工程分别启动Rest和Dubbo的问题。之前如果一个工程需同时以Dubbo和Rest的方式提供服务，需部署两份到不同的注册中心，现在只要启动一份Rest的服务即可同时供Dubbo客户端和Rest客户端使用。相关的技术说明在附件中的第十章。
2）提供了HttpClientUtil，可以方便调用Rest服务。同时，之前的DubboConsumerFactory也可以dubbo的方式调用rest服务。
3）附件中的sdk说明文档已更新至svn：   http://10.1.130.76:8082/opt/BIU-BaaS_DOC/03.Design Doc/04.BaaS共享资料/开发规范/OPT+产品研发技术规范手册V2.0.docx 


*2016-05-06 2.0-SNAPSHOT 
1）加入ipaas组件，升级sdk为2.0-SNAPSHOT。2.0系列与1.0系列不兼容，1.0系列已在github建立分支并归档
2）opt-sdk1.x未使用ipaas的平台能力，opt-sdk2.0集成了ipaas平台能力。
3）opt-sdk2.0 集成了ipaas的ccs（配置中心）、mcs（缓存中心）、mds（消息中心）、dss（文档存储）功能，sdk已经通过测试
4）opt-sdk2.0的gradle坐标：compile 'com.ai.opt.sdk:opt-sdk:2.0-SNAPSHOT'
5）调整CCS的使用方式
6) 调整MCS的使用方式
7）增加消息服务MDS的使用说明
8）增加文档存储服务DSS的使用说明


