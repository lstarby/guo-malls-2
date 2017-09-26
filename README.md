# 1.宜立方商城介绍
宜立方网上商城是一个综合性的B2C平台，类似京东商城、天猫商城。会员可以在商城浏览商品、下订单，以及参加各种活动。<br>
管理员、运营可以在平台后台管理系统中管理商品、订单、会员等。<br>
客服可以在后台管理系统中处理用户的询问以及投诉。

# 2.宜立方商城架构

## 2.1功能列表

<img src="https://i.imgur.com/UGx6wTE.png"   width = "500" height = "500" />

- 后台管理系统：管理商品、订单、类目、商品规格属性、用户管理以及内容发布等功能。
- 前台系统：用户可以在前台系统中进行注册、登录、浏览商品、首页、下单等操作。
- 会员系统：用户可以在该系统中查询已下的订单、收藏的商品、我的优惠券、团购等信息。
- 订单系统：提供下单、查询订单、修改订单状态、定时处理订单。
- 搜索系统：提供商品的搜索功能。
- 单点登录系统：为多个系统之间提供用户登录凭证以及查询登录用户的信息。

## 2.2基于soa的架构
SOA：Service Oriented Architecture面向服务的架构。也就是把工程拆分成服务层、表现层两个工程。服务层中包含业务逻辑，只需要对外提供服务即可。表现层只需要处理和页面的交互，业务逻辑都是调用服务层的服务来实现。
![](https://i.imgur.com/in39lMm.png)

## 2.3宜立方商城系统架构
![](https://i.imgur.com/4sqT3Og.png)

# 3.SSM整合思路
- 1、Dao层：
Mybatis的配置文件：SqlMapConfig.xml
不需要配置任何内容，需要有文件头。文件必须存在。
applicationContext-dao.xml：
mybatis整合spring，通过由spring创建数据库连接池，spring管理SqlSessionFactory、mapper代理对象。需要mybatis和spring的整合包。
- 2、Service层：
applicationContext-service.xml：
所有的service实现类都放到spring容器中管理。并由spring管理事务。

- 3、表现层：
Springmvc框架，由springmvc管理controller。
Springmvc的三大组件。

## 3.1解决mapper绑定异常
![](https://i.imgur.com/QksbShi.png)

此异常的原因是由于mapper接口编译后在同一个目录下没有找到mapper映射文件而出现的。由于maven工程在默认情况下src/main/java目录下的mapper文件是不发布到target目录下的。

- 解决方法

在e3-manager-dao工程的pom文件中添加如下内容：

```
<!-- 如果不添加此节点mybatis的mapper.xml文件都会被漏掉。 -->
	<build>
		<resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
	</build>

```

# 4.将工程改造为SOA架构
由于宜立方商城是基于soa的架构，表现层和服务层是不同的工程。所以要实现商品列表查询需要两个系统之间进行通信。<br>
如何实现远程通信？

- 1、Webservice：效率不高基于soap协议。项目中不推荐使用。
- 2、使用restful形式的服务：http+json。很多项目中应用。如果服务太多，服务之间调用关系混乱，需要治疗服务。
- 3、使用dubbo。使用rpc协议进行远程调用，直接使用socket通信。传输效率高，并且可以统计出系统之间的调用关系、调用次数。

## 4.1dubbo
随着互联网的发展，网站应用的规模不断扩大，常规的垂直应用架构已无法应对，分布式服务架构以及流动计算架构势在必行，亟需一个治理系统确保架构有条不紊的演进。
![](https://i.imgur.com/QrweOTZ.jpg)

•	单一应用架构
- 当网站流量很小时，只需一个应用，将所有功能都部署在一起，以减少部署节点和成本。
- 此时，用于简化增删改查工作量的 数据访问框架(ORM) 是关键。

•	垂直应用架构
- 当访问量逐渐增大，单一应用增加机器带来的加速度越来越小，将应用拆成互不相干的几个应用，以提升效率。
- 此时，用于加速前端页面开发的 Web框架(MVC) 是关键。

•	分布式服务架构
- 当垂直应用越来越多，应用之间交互不可避免，将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，使前端应用能更快速的响应多变的市场需求。
- 此时，用于提高业务复用及整合的 分布式服务框架(RPC) 是关键。

•	流动计算架构

- 当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。
- 此时，用于提高机器利用率的 资源调度和治理中心(SOA) 是关键。

## 4.2dubbo使用方法
Dubbo采用全Spring配置方式，透明化接入应用，对应用没有任何API侵入，只需用Spring加载Dubbo的配置即可，Dubbo基于Spring的Schema扩展进行加载。<br>

远程服务：
在本地服务的基础上，只需做简单配置，即可完成远程化： <br>

将上面的local.xml配置拆分成两份，将服务定义部分放在服务提供方remote-provider.xml，将服务引用部分放在服务消费方remote-consumer.xml。
并在提供方增加暴露服务配置<dubbo:service>，在消费方增加引用服务配置<dubbo:reference>。 <br>
发布服务：
```
<!-- 和本地服务一样实现远程服务 -->
<bean id="xxxService" class="com.xxx.XxxServiceImpl" />
<!-- 增加暴露远程服务配置 -->
<dubbo:service interface="com.xxx.XxxService" ref="xxxService" />

```

调用服务：

```
<!-- 增加引用远程服务配置 -->
<dubbo:reference id="xxxService" interface="com.xxx.XxxService" />
<!-- 和本地服务一样使用远程服务 -->
<bean id="xxxAction" class="com.xxx.XxxAction">
	<property name="xxxService" ref="xxxService" />
</bean>
```

## 4.3注册中心
官方推荐使用zookeeper注册中心。<br>
注册中心负责服务地址的注册与查找，相当于目录服务，服务提供者和消费者只在启动时与注册中心交互，注册中心不转发请求，压力较小。使用dubbo-2.3.3以上版本，建议使用zookeeper注册中心。<br>
Zookeeper是Apacahe Hadoop的子项目，是一个树型的目录服务，支持变更推送，适合作为Dubbo服务的注册中心，工业强度较高，可用于生产环境，并推荐使用<br>

Zookeeper：
- 1、可以作为集群的管理工具使用。
- 2、可以集中管理配置文件。

# 5.	图片服务器
## 5.1什么是FastDFS？
FastDFS是用c语言编写的一款开源的分布式文件系统。FastDFS为互联网量身定制，充分考虑了冗余备份、负载均衡、线性扩容等机制，并注重高可用、高性能等指标，使用FastDFS很容易搭建一套高性能的文件服务器集群提供文件上传、下载等服务。
## 5.2 2.2.	FastDFS架构
FastDFS架构包括 Tracker server和Storage server。客户端请求Tracker server进行文件上传、下载，通过Tracker server调度最终由Storage server完成文件上传和下载。

- Tracker server作用是负载均衡和调度，通过Tracker server在文件上传时可以根据一些策略找到Storage server提供文件上传服务。可以将tracker称为追踪服务器或调度服务器。

- Storage server作用是文件存储，客户端上传的文件最终存储在Storage服务器上，Storage server没有实现自己的文件系统而是利用操作系统 的文件系统来管理文件。可以将storage称为存储服务器。

![](https://i.imgur.com/Ptq978L.png)

服务端两个角色：
- Tracker：管理集群，tracker也可以实现集群。每个tracker节点地位平等。
收集Storage集群的状态。
- Storage：实际保存文件
Storage分为多个组，每个组之间保存的文件是不同的。每个组内部可以有多个成员，组成员内部保存的内容是一样的，组成员的地位是一致的，没有主从的概念。

## 5.3文件上传的流程
![](https://i.imgur.com/QBPUKJ4.png)

客户端上传文件后存储服务器将文件ID返回给客户端，此文件ID用于以后访问该文件的索引信息。文件索引信息包括：组名，虚拟磁盘路径，数据两级目录，文件名。

 http://192.168.0.134/group1/M00/00/00/wKgAhlnGGZCAfvCtAAlZyM9N1Yg432.jpg
- 组名：文件上传后所在的storage组名称，在文件上传成功后有storage服务器返回，需要客户端自行保存。
- 虚拟磁盘路径：storage配置的虚拟路径，与磁盘选项store_path*对应。如果配置了store_path0则是M00，如果配置了store_path1则是M01，以此类推。
- 数据两级目录：storage服务器在每个虚拟磁盘路径下创建的两级目录，用于存储数据文件。
- 文件名：与文件上传时不同。是由存储服务器根据特定信息生成，文件名包含：源存储服务器IP地址、文件创建时间戳、文件大小、随机数和文件拓展名等信息。

##５.4文件下载
![](https://i.imgur.com/kIJyXdd.png)

## 5.5图片服务器安装
[安装请参考这篇文章](https://github.com/guoxiaoxu/Linux-Tutorial/blob/69984bf56cf592abe0693b6af52aacaa2d43bed5/FastDFS-Install-And-Settings.md)

# 6功能展示
## 6.1商品上架、下架、删除功能展示
![](https://i.imgur.com/tmKSCPy.gif)

## 6.2内容管理功能展示
![](https://i.imgur.com/7vLFK45.gif)

## 6.3轮播广告展示

![](https://i.imgur.com/YRtJiSY.gif)

# 7什么是SolrCloud

SolrCloud(solr 云)是Solr提供的分布式搜索方案，当你需要大规模，容错，分布式索引和检索能力时使用 SolrCloud。当一个系统的索引数据量少的时候是不需要使用SolrCloud的，当索引量很大，搜索请求并发很高，这时需要使用SolrCloud来满足这些需求。<br>
 SolrCloud是基于Solr和Zookeeper的分布式搜索方案，它的主要思想是使用Zookeeper作为集群的配置信息中心。<br>
它有几个特色功能：
- 1、中式的配置信息
- 2、自动容错
- 3、近实时搜索
- 4、查询时自动负载均衡

## 7.1Solr集群的系统架构

![](https://i.imgur.com/SbCqbP4.png)
