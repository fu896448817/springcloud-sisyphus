### springcloud-sisyphus
> springcloud脚手架
####缘起
写这个脚手架的起源是，当我们学习一个新的技术或者要开启一个新的项目时，需要花费很长的时间去构建一个框架，即使有了springboot
之后我们可以比较快速的构建框架，但是如果我们想要比较友好的展示数据还是需要花费比较长的时间的。基于此，我在之前项目的
基础之上剥离了业务代码，完成该脚手架，让大家减少学习以及搭建的成本。其中包含以下服务

| ms     | 默认端口    |说明 |
| --------   | :-----:   | ----:|
| sisyphus-auth-ms        |  9090   | 鉴权中心|
| sisyphus-auth-api        |  9091   | openApi|
| sisyphus-api-gateway     |7788|网关|
| sisyphus-registry-server     |7777 | 注册中心 |
| sisyphus-config-server     |8888 | 配置中心 |
| sisyphus-basic-ms        |  9092    |基础通用服务，包含redis，mongo，mysql，fastdfs 等服务 |
| sisyphus-data-ms        |  9093    |数据统计服务，包含技术kafka，hbase，es等 |
