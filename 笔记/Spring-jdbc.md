# Spring架构

![图片1](图片1.png)

# Spring DAO（JDBC模块）

## 简介

Spring的JDBC模板为了避免直接使用JDBC而带来的复杂且冗长的代码，Spring提供了一个强有力的模板类---
JdbcTemplate来简化JDBC操作。 并且,数据源DataSource对象与模板]dbcTemplate对象均可通过Bean的形式定
义在配置文件中,充分发挥了依赖注入的威力
需要依赖的jar :

`spring-instrument-x.x.x.RELEASE.jar`

`spring-jdbc-x.x.x.RELEASE.jar` 

`spring-tx-x.x.x.RELEASE.jar`

## 数据源的配置

使用DBC模板,首先需要配置好数据源,数据源直接以Bean的形式配置在Spring配置文件中。
**由于建立数据库连接是一个非常耗时耗资源的行为,所以通过连接池预先同数据库建立一些连接 ,放在内存中，应**用**程序需要建立数据库连接时直接到连接池中申请一个就行,用完后再放回去。**

### 数据库连接池Druid

Druid是阿里巴巴开源平台上的一个项目,整个项目由数据库连接池、插件框架和SQL解析器组成。该项目主要是为了扩展JDBC的一-些限制,可以让程序员实现-一些特殊的需求 ,比如向密钥服务请求凭证、统计SQL信息、SQL 性能收集、SQL注入检查、SQL 翻译等,程序员可以通过定制来实现自己需要的功能。

### 导入jar

```java
mysq1-connector-java-x.x.x.jar
druid-x.x.x.jar
//如下是JdbcTemplate需要的jar
spring-instrument -x.x.x.RELEASE.jar
spring-jdbc-x.x.x.RELEASE.jar
spring-tx-x.x.x.RELEASE.jar
```



## 配置Spring数据源连接池信息

```xml
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close" init-method="init">
        <property name="url" value="${jdbc_url}" />
        <property name="username" value="${jdbc_user}" />
        <property name="password" value="${jdbc_password}" />
        <property name="driverClassName" value="${jdbc_driver}" />
        <property name="filters" value="${filters}" />

        <property name="maxActive" value="${maxActive}" />
        <property name="initialSize" value="${initialSize}" />
        <property name="maxWait" value="${maxWait}" />
        <property name="minIdle" value="${minIdle}" />

        <property name="timeBetweenEvictionRunsMillis" value="${timeBetweenEvictionRunsMillis}" />
        <property name="minEvictableIdleTimeMillis" value="${minEvictableIdleTimeMillis}" />

        <property name="validationQuery" value="${validationQuery}" />
        <property name="testWhileIdle" value="${testWhileIdle}" />
        <property name="testOnBorrow" value="${testOnBorrow}" />
        <property name="testOnReturn" value="${testOnReturn}" />
        <property name="maxOpenPreparedStatements"
                  value="${maxOpenPreparedStatements}" />
        <!--<property name="removeAbandoned" value="${removeAbandoned}" /> &lt;!&ndash; 打开removeAbandoned功能 &ndash;&gt;-->
        <!--<property name="removeAbandonedTimeout" value="${removeAbandonedTimeout}" /> &lt;!&ndash; 1800秒，也就是30分钟 &ndash;&gt;-->
        <!--<property name="logAbandoned" value="${logAbandoned}" /> &lt;!&ndash; 关闭abanded连接时输出错误日志 &ndash;&gt;-->
    </bean>
```



## JDBC从属性文件读取数据库连接信息

### JDBC从属性文件读取数据库连接信息

该属性文件jdbc.properties若要被Spring配置文件读取,其必须在配置文件中进行注册。
注册方式有两种:↓
(1) `<bean>`方式
(2) `<context>` context:property-placeholder/方式
该方式要求在Spring配置文件头部加入context的约束,即修改配置文件头。引入外部文件需要加上classpath：

```
<!--
    加载外部配置文件property
    标签中有一个属性location,用于指定属性文件的位置
-->
<context :property-placeholderlocation="classpath:jdbc.properties"/>
```

Spring配置文件从属性文件中读取数据时,需要在`<property>`的value属性中使用${},
将在属性文件中定义的key括起来,以引用指定属性的值

```xml
<property name="url" value="${jdbc_url}" />
        <property name="username" value="${jdbc_user}" />
        <property name="password" value="${jdbc_password}" />
        <property name="driverClassName" value="${jdbc_driver}" />
        <property name="filters" value="${filters}" />

        <property name="maxActive" value="${maxActive}" />
        <property name="initialSize" value="${initialSize}" />
        <property name="maxWait" value="${maxWait}" />
        <property name="minIdle" value="${minIdle}" />

        <property name="timeBetweenEvictionRunsMillis" value="${timeBetweenEvictionRunsMillis}" />
        <property name="minEvictableIdleTimeMillis" value="${minEvictableIdleTimeMillis}" />

        <property name="validationQuery" value="${validationQuery}" />
        <property name="testWhileIdle" value="${testWhileIdle}" />
        <property name="testOnBorrow" value="${testOnBorrow}" />
        <property name="testOnReturn" value="${testOnReturn}" />
        <property name="maxOpenPreparedStatements"   															        		  		value="${maxOpenPreparedStatements}" />
```

### 引入JDBCTemplate

```xml
<!--spring jdbcTemplate-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource">
        </property>
    </bean>
```

## 完整的application.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"

       xsi:schemaLocation="
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
        ">

    <!--Spring IOC 扫描指定包-->
    <context:component-scan base-package="com.mzw" resource-pattern="**/*。class" use-default-filters="true"></context:component-scan>

    <!--Spring AOP启动配置 自动激活动态代理-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

    <!--配置数据源 druid-->

    <!--加载外部property文件
    location用来指定属性文件的位置
    -->
    <context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close" init-method="init">
        <property name="url" value="${jdbc_url}" />
        <property name="username" value="${jdbc_user}" />
        <property name="password" value="${jdbc_password}" />
        <property name="driverClassName" value="${jdbc_driver}" />
        <property name="filters" value="${filters}" />

        <property name="maxActive" value="${maxActive}" />
        <property name="initialSize" value="${initialSize}" />
        <property name="maxWait" value="${maxWait}" />
        <property name="minIdle" value="${minIdle}" />

        <property name="timeBetweenEvictionRunsMillis" value="${timeBetweenEvictionRunsMillis}" />
        <property name="minEvictableIdleTimeMillis" value="${minEvictableIdleTimeMillis}" />

        <property name="validationQuery" value="${validationQuery}" />
        <property name="testWhileIdle" value="${testWhileIdle}" />
        <property name="testOnBorrow" value="${testOnBorrow}" />
        <property name="testOnReturn" value="${testOnReturn}" />
        <property name="maxOpenPreparedStatements"
                  value="${maxOpenPreparedStatements}" />
        <!--<property name="removeAbandoned" value="${removeAbandoned}" /> &lt;!&ndash; 打开removeAbandoned功能 &ndash;&gt;-->
        <!--<property name="removeAbandonedTimeout" value="${removeAbandonedTimeout}" /> &lt;!&ndash; 1800秒，也就是30分钟 &ndash;&gt;-->
        <!--<property name="logAbandoned" value="${logAbandoned}" /> &lt;!&ndash; 关闭abanded连接时输出错误日志 &ndash;&gt;-->
    </bean>
    <!--spring jdbcTemplate-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource">

        </property>
    </bean>

</beans>
```



## JDBCTemplate示例

Spring对数据库的操作在jdbc.上面做了深层次的封装,使用spring的注入功能,可以把DataSource注册到
JdbcTemplate之中。
**JdbcTemplate主要提供以下五类方法:**

1. query方法及queryForXXX方法用于执行查询相关语句;
2. update方法及batchUpdate方法: update方法用于执行新增、修改、删除等语句; batchUpdate方法用于执
行批处理相关语句;
3. call方法:用于执行存储过程、函数相关语句。
4. execute方法:可以用于执行任何SQL语句, -般用于执行DDL语句;

### 查询功能

query方法及queryFoxXXX方法:用于执行查询相关语句

**读取单个对象**

```java
public User selectById(int id) {
	RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);
    String sql = "select id,name,age,remark from user where id = ?";
    try{
    	return jdbcTemplate.queryForObject(sql, userRowMapper, id);
    }catch(EmptyResultDataAccessException em){
    	System.err.println(em.getMessage());
    }
    return null;
}
```

**读取多个对象**

```java
public List<User> selectList() {
    RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);
    String sql = "select id,name,age,remark from user";
    try{
    	return jdbcTemplate.query(sql, userRowMapper);
    }catch(EmptyResultDataAccessException em){
    	System.err.println(em.getMessage());
    }
    return null;
}
```

**获取某记录列count,sum,max,min,avg等函数返回为唯一值**

```java
public int count(){
	String sql = "select count(*) from user";
	return jdbcTemplate.queryForObject(sql,Integer.class);
}
```

### 修改功能

update 方法用于执行增加，修改，删除等语句

```java
public int insert(User user) {
	String sql = "insert into user(name,age,remark) values(?,?,?)";
	int num = jdbcTemplate.update(sql,user.getName(),user.getAge(),user.getRemark());
	return num;
}
public int update(User user) {
	String sql = "update user set name = ?,age = ?,remark=? where id = ?";
	int num = jdbcTemplate.update(sql,user.getName(),user.getAge(),
	user.getRemark(),user.getId());
    return num;
}
public int deleteById(int id) {
	String sql = "delete from user where id = ?";
    int num = jdbcTemplate.update(sql,id);
    return num;
}
```

### 批量插入、修改、删除功能

```java
 public int[] insertBatch(List<User> userList) {
    String sql = "update user set name = ?,age = ?,remark=? where id = ?";
    List<Object[]> objects = new ArrayList<>();
    for(User user:userList){
    	Object[] objects1 = new Object[3];
        objects1[0] = user.getName();
        objects1[1] = user.getAge();
        objects1[2] = user.getRemark();
    	objects.add(objects1);
    }
	return jdbcTemplate.batchUpdate(sql,objects);
 }
```
