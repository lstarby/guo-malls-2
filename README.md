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
