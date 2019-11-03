# Spring IOC（控制反转）

```
控制反转（IOC）（Inversion of control）
依赖注入（DI）（Dependency Injection）
	当某个角色（比如创建一个JAVA实例，调用者）需要另一个角色（另一个JAVA实例，被调用者）的协助时，在传统的程序设计过程中，通常由调用者创建被调用者的实例，但是在Spring里，被调用者的创建工作不由调用者来完成，因此成为控制反转；创建被嗲用着实例的工作由Spring容器来完成，然后注入被调用者，因此称为依赖注入。
	
	Spring核心容器是整个应用中的超级大工厂，所有的JAVA对象都交给Spring容器来管理。这些JAVA对象被称为Spring容器的Bean。
```
## 控制反转示例
applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
	   http://www.springframework.org/schema/beans/spring-beans.xsd">
	   <!--
		全限定名：完整包名+类命
	    id bean的唯一性id
	    class 实例化创建的全限定名
        执行过程：
        1、加载XML配置文件
        2、解析XML文件中的每一个节点
        id = userDao     class = “。。。。”
        3、通过反射的机制创建对象
        UserDao  userDao =  Class。forName("com.mzw.dao.UserDao").newInstance();
        默认是单例模式
    	-->
    <bean id="userDao" name = "u1,u2,u3" class="com.mzw.dao.UserDao"></bean>
</beans>
```

## BeanFactory
### 简介：

1. Spring容器基本的接口就是BeanFactory。BeanFactory负责配置、创建、管理Bean，他有一个字接口：ApplicationContext，因此也称之为Spring上下文。Spring容器负责管理Bean与Bean之间的依赖关系。
2. BeanFactory：是IOC容器得到核心接口，它定义了IOC的基本功能，它主要定义了getBean()方法。getBean方法是IOC容器获取Bean对象和引发依赖注入的起点，方法的功能是返回特定名称为Bean。
3. BeanFactory是初始化Bean和调用它们生命周期方法的“吃苦耐劳者”，注意，BeanFactory只能管理单例（Singleton）Bean的生命周期，它不能管理原型（prototype，非单例Bean的生命周期，这是因为原型Bean示例被创建之后便被传给客户端，容器失去了对他的应用。
4. BeanFactory有庞大的继承、实现体系，有众多的字接口、实现。

### BeanFactory接口包含以下几个基本方法：
* Bool containBean(String name)//判断Spring容器是否包含id为name的Bean实例
* <T>getBean(Class<T> requiredType)//获取Spring容器中属于requiredType类型的唯一的Bean实例。
* Object getBean(String name)//返回Spring容器中id为name的Bean实例

BeanFactory接口提供了配置框架及基本功能，但是无法支持Spring得到aop功能和web应用。而ApplicationContext接口作为BeanFactory的派生，因而提供了BeanFactory的所有功能。而且ApplicationContext还在功能上做了扩展，相较于BeanFactory，ApplicationContext还提供了以下功能：
（1）MessageSource，提供国际化的信息访问
（2）资源访问，入URL和文件
（3）事务传播属性，即支持aop特性
（4）载入多个（有继承关系）上下文，使得每个上下文都专注于一个特定层次，比如应用的web层

### ApplicationContext

ApplicationContext是IOC容器另一个重要接口，它继承了BeanFactory的基本功能，同时也继承了容器的高级功能，如：MessageSource（国际化资源接口）、ResouceLoader（资源加载接口）ApplicationEventPublisher（应用实践发布接口）等。

### 二者区别
1. BeanFactory采用的是延迟加载形式注入Bean的，即只有在使用到某个Bean的时候（调用getBean())，才对该Bean进行加载实例化，这样，我们就不能发现一些存在的Spring的配置问题，而ApplicationContext则相反，他在容器启动时一次性创建所有的Bean。ApplicationContext唯一的不足就是占用内存资源。当应用程序配置Bean较多时，程序启动慢。
2. BeanFactory负责读取Bean配置文件，管理Bean加载，实例化，维护Bean之间的依赖关系，负责Bean的生命周期。ApplicationContext除了提供上述BeanFactory所提供的所有功能之外，还提供了完整的框架功能（国际化...资源访问...)
3. 常用的ApplicationContext的方式：FileSystemXmlApplicationContext：从文件系统或者url指定的xml配置文件，参数为配置文件名或文件名组，有相对路径和绝对路径。

	 	ApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath:applicationcontext.xml");//类路径上下文，可以从jar包读取温江
                //new FileSystemXmlApplicationContext("src/applicationcontext.xml");//也可以添加绝对路径
4. ClassPathXmlApplicationContext：从classpath的xml配置文件创建，可以从jar包中读取配置文件。ClassPathXmlApplicationContext编译路径的3种方式：
	
				ApplicationContext factory = new 	ClassPathApplicationContext("classpath:applicationcontext.xml");
		
		ApplicationContext factory = new ClassPathApplicationContext("applicationcontext.xml");
		
		ApplicationContext factory = new AnnotationConfigApplicationContext();//springboot 全面注解
	
## 依赖注入示例
### spring支持3中依赖注入的方式
1. 属性注入
2. 构造方法注入
3. 工厂方法注入（很少使用，不推荐）

### 属性注入(SET)

最简单的注入方式（简单、直观、Spring大量使用）

	<bean id="user"  class="com.mzw.bean.User">
	    <!--
	        set(属性)注入
	        解析name -》 setName("...");
	    -->
	    <property name="id" value="1001"></property>
	    <property name="name" value="李四"></property>
	    <property name="remark" value="我爱学习"></property>
	</bean>

#### 构造器注入
是指带有参数的构造函数注入（完成程序的初始化操作）

```xml
<bean id="student1"  class="com.mzw.bean.Student">
    <!--构造器注入
        index索引下标的方式（构造方法的顺序 最左侧为0）
    -->
    <constructor-arg name="age" value="23"></constructor-arg>
    <constructor-arg name="name" value="王五"></constructor-arg>
</bean>
```
1. 根据参数的名字传值：（推荐）	
	
		```xml
		<constructor-arg name="age" value="23"></constructor-arg>
	```
	
2. 直接传值。这种方法也是根据顺序排的，所有一旦调换位置就会出bug

    ```xml
    <bean id="student" name="s1,s2,s3" class="com.mzw.bean.Student">
            <!--构造器注入
                index索引下标的方式（构造方法的顺序 最左侧为0）
            -->
            <constructor-arg value="23"></constructor-arg>
            <constructor-arg value="王五"></constructor-arg>
    </bean>
    ```

3. 根据索引赋值index，索引都是以0开头 

    ```xml
    <bean id="student" name="s1,s2,s3" class="com.mzw.bean.Student">
            <!--构造器注入
                index索引下标的方式（构造方法的顺序 最左侧为0）
            -->
            <constructor-arg index="0" value="23"></constructor-arg>
            <constructor-arg index="1" value="王五"></constructor-arg>
     </bean>
    ```

    

### 注入其他类型
##### 外部bean

```xml
	<bean id="user" class="com.mzw.bean.User">
		<property name="name" value="张三"></property>
		<property name="role" ref="role"></property>
	</bean> 
	<bean id="role" class="com.mzw.bean.Role">
		<property name="rname" value="管理员"></property> 
	</bean>
```

##### 内部bean<br>当Bean实例仅仅给一个特定的属性使用时，可以将其声明为内部Bean
内部Bean声明直接包含在`<property>`或`<constuctor-arg>`元素里，不需要设置任何Id或name属性，内部Bean不能使用在任何地方
		
```xml
<bean id="user" class="com.mzw.bean.User">
	<property name="name" value="张三"></property>
	<property name="role">
		<bean class="com.mzw.bean.Role">
			<property name="rname" value="管理员"></property> 
		</bean>
	</property>
</bean> 
```


#### 集合类型的注入
对list集合的注入，如果类中有list类型的属性，在为其依赖注入的时候就需要在配置文件中的`<property>`元素下应用其子元素`<list>`
```xml
<bean id="grade" class="com.mzw.bean.Grade">
    <property name="gid" value="10001"></property>
    <property name="gname" value="数据结构"></property>
</bean>
<bean id="student1"  class="com.mzw.bean.Student">
    <!--构造器注入
        name注入
     -->
    <constructor-arg name="age" value="23"></constructor-arg>
    <constructor-arg name="name" value="王五"></constructor-arg>
    <property name="grades" >
       <list>
           <ref bean="grade"></ref>
           <bean class="com.mzw.bean.Grade">
               <property name="gid" value="10002"></property>
               <property name="gname" value="Socket编程"></property>
           </bean>
       </list>
    </property>
    <property name="infos">
        <list>
            <value>单身狗</value>
            <value>爱学习</value>
        </list>
    </property>
</bean>
```

#### Map注入

```xml
<property name= "map">
	<map key-type="java.lang.String" value-type="com.mzw.bean.Role">
		<entry key="r1" value-ref="role"></entry>
		<entry key="r2" >
			<bean class="com.mzw.bean.Role">
				<property name="rid" value="1"></property>
				<property name="rname" value=" 管理员"></property>
			</bean>
		</entry>
	</map>
</property>
```


## spring中bean的作用域

在spring中，可以在`<bean>`元素的scope属性里设置bean的作用域，以决定这个bean是单实例还是多实例的。

默认情况下，spring只为每个在ioc容器里生命的bean创建唯一一个实例，整个IOC容器范围内都只能共享该实例：所有后续的getBean()调用和bean引用都将返回这个唯一的bean实例。该实例的作用域被称为singleton，它是所有bean的默认作用域。

类别|说明
---|---
singleton|在SpringIOC容器中仅存在一个bean实例，bean以单实例的方式存在
prototype|每次调用getBean()时都会返回一个新的实例
request|每次HTTP请求都会创建一个新的Bean，该作用域仅适用于WebApplicationContext环境
session|每次会话中共享一个bean，仅适用于WebApplicationContext
globleSession|一般用于Portlet应用环境，该作用与仅适用于WebApplicationContext环境
当bean的作用域为单实例时，Spring会在IOC容器对象时就创建bean的对象实例。


## Bean生命周期
Spring IOC容器管理Bean的生命周期，Spring允许在Bean的生命周期的特定点执行特定的任务。
生命周期：从出生到销毁的过程。
SpringIOC对Bean生命周期管理的过程

1. 通过构造器或工厂方法创建Bean实例
2. 为Bean的属性设置值和对其他Bean引用
3. Bean 后置处理器的before
4. 调用Bean的初始化方法
5. Bean 后置处理器的after
6. Bean可以被调用了
7. 当容器关闭时close()调用Bean的销毁方法

在Bean的声明里设置init-method和destroy-method属性，为Bean指定初始化和销毁方法
init作用：set属性值注入时检查值的合理性。初始化动作。加载配置文件，创建数据库连接池等等。

applicationcontext.xml

```xml
<bean id="grade" class="com.mzw.bean.Grade" init-method="init" destroy-method="destroy">
    <property name="gname" value="JAVA-2019"></property>
    <property name="gid" value="190"></property>
</bean>
```
Grade.java

```java
public void init(){
    if(this.gid>150 || this.gid<0){
        this.gid = 20;
    };
    System.out.println("3.初始化方法");
}

public void destroy(){
    System.out.println("4.销毁Grade");
}
```

**注解的方式**

init-method:`@PostConstuct`
destroy-method:`@PreDestroy`

## Bean后置处理器
Bean的后置处理器允许在调用初始化方法前后对Bean进行额外处理。
Bean后置处理器对IOC容器里的所有Bean实例逐一处理，而非单一实例。其典型的应用是：检查Bean属性的正确性或根据特定的标准更改Bean属性。（一个好的框架必备的特性至少要有开闭原则和可扩展性）

对Bean后置处理器而言，需要实现BeanFactoryPostProcessor接口。在初始化被调用前后，Spring将把每个Bean实例传递给上述接口的以下两个方法：

BeanFactoryPostProcessor接口的源码，它定义了两个方法，分别在初始方法前后执行
```java
public class SpringBeanPostProcessor implements BeanPostProcessor {
	/**
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before init"+beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after init"+beanName);
        return bean;
    }
}
```


## 循环依赖
循环依赖其实是指两个或两个以上的Bean互相持有对方，最终形成闭环。比如A依赖B，B依赖A。需要注意的是，这里不是函数的循环调用，是对象的相互依赖关系，循环调用其实就是一个死循环，除非有终结条件。

#### Spring中循环依赖的场景：
1. 构造器的循环依赖
2. field属性的循环依赖

其中构造器的循环依赖问题无法解决，只能抛出BeanCurrentlyInCreationException异常，在解决属性循环依赖时，spring采用的是提前暴露对象的方法。

#### spring怎么解决循环依赖的问题？
Spring的循环依赖的理论依据基于Java的引用传递，当获得对象的引用传递时，对象的属性可以延后设置。（但是构造器必须在获取引用之前）
因此，不要使用基于构造函数的依赖注入，可以通过以下方式解决：
1. 在字段上使用@Autowire注解，让Spring决定在合适的时机注入
2. 用基于setter方法的依赖注入

## Spring注解IOC

### 1. 简介
曾将Java和xml是完美契合的，许多人认为Java是跨平台的语言，而xml是跨平台的数据交互格式，所以Java和消灭了应该是天作之合。在这种潮流下，以前的Java框架都是用xml作为配置文件。
在目前的web应用中几乎都是用xml作为配置项，例如我们尝试用的框架struts，spring，hibernate,Ibatis等都采用xml作为配置。xml之所以这么流行，是因为它的很多优点是其他技术的配置无法替代的。

#### XML优点
1. xml作为可扩展标记语言最大的优势在于开发者能够为软件量身定制使用的标记，是代码更加通俗易懂。
2. 利用xml配置能使软件更具扩展性，例如Spring将class间的以来配置在xml中，最大限度地提升应用的可扩展性。
3. 具有成熟的验证机制确保程序的正确性。利用Schema或DTD可以对xml的正确性进行验证，避免了非法的配置导致应用程序除左
4. 修改配置而无需变动现有程序。

#### xml缺点

1. 需要解析工具和类库的支持。
2. 解析xml势必会影响程序的性能，占用系统资源。
3. 配置文件过多导致管理困难
4. 编译器无法对配置项的正确性进行校验或者差错只能在运行期
5. IDE无法验证配置项的正确性
6. 差错变得困难。往往配置的一个手误导致莫名其妙的错误。
7. 开发人员不得不同时维护代码和配置文件，开发效率低下。
8. 配置项与代码之间存在潜规则，改变任何一方都有可能影响到另一方。

### 2.基于注解的依赖注入
基于注解（Annotation）的配置有越来越流行的趋势，Spring 3顺应这种趋势，提供了完全基于注释配置Bean、装配Bean的功能，开发人员可以使用基于SpringIOC替换原来基于xml的配置。

**注解配置相对于xml配置具有很多优势：**

注解本身没有功能，就和xml一样。注解和xml都是一种元数据。元数据即解释数据的数据，这就是所谓的配置。Spring注解方式减少了配置文件的内容，更加便于管理，并且使用注解可以提高开发效率。

### 3. 注解依赖注入

既然我们不再使用Spring配置文件来配置任何Bean实例，那么我们只能指望Spring会自动搜索某系而路径下的Java类，并将这些类注册成Bean实例。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--
        Spring IOC扫描器
        base-package 要指定扫描的包
        resource-pattern 指定要扫描那些文件
        use-default-filters 默认的filter
        @Component，@Repository,@RService,@Controller

        context:exclude-filter  排除
        context:include-filter  包含指定的扫描filter
    -->
    <context:component-scan base-package="com.mzw" resource-pattern="**/*.class" use-default-filters="false">
    </context:component-scan>

</beans>
```



### 4. 声明Bean注解

`@Component`标注一个普通的Spring Bean类

`@Service`标注一个业务逻辑组件类

`@Controller`标注一个控制器组件

`@Repository`标注一个Dao组件类

`@Component`是左右受Spring管理组件的通用形式，而`@Repository`、`@Controller`、`@Service`则是`@Component`的细化，用来表示更具体的用例（例如，分别对应持久化层，服务层和表现层）。

也就是说，你能用`@Component`来注解你的组件类，但是如果用`@Repository`、`@Controller`、`@Service`来注解它们，你的泪也许能更好的被工具处理，或与切面进行关联。例如，浙西而典型化注解可以成为理想的切入点目标。也许还能携带更多语义。

** 备注：以上注解作用在类上。都是负责生命一个bean到Spring上下文中，bean的ID默认类的名字首字母小写。如果想自定义，可以`@Service("aaa")`这样来指定。

可通过`context:exclude-filter`排除指定扫描的某个注解（不扫描`@Service`注解）

```xml
<context:component-scan base-package="com.mzw" resource-pattern="**/*.class" use-default-filters="true">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"></context:include-filter>
    </context:component-scan>
```

可通过`context:include-filter`排除指定扫描的某个注解（扫描`@Service`注解），`use-default-filters`要设置为false

```xml
<context:component-scan base-package="com.mzw" resource-pattern="**/*.class" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Service"></context:include-filter>
    </context:component-scan>
```

**如果改变对象的作用域，可通过`@Scope("prototype")`注解来改变。（作用在类上）**

### 5. 组件装配（依赖注入）

#### 简要

自动装配是指Spring在装配Bean的时候，根据指定的自动装配规则,将某个Bean所需要引用类型的Bean注入进来。可以在类的成员变量上，构造方法,setter方法使用
`context:component-scan`元素还会自动注册`AutowiredAnnotationBeanPostProcessor`实例，
该实例可以自动装配具有`@Autowired`和`@Resource`、`@Inject`注解的

####  `@Autowired`

Spring通过`@Autowired`实现依赖注入:
Spring 2.5引入了`@Autowired`注释,它可以对类成员变量、方法及构造函数进行标注,完成自动装配的工作。(是根据类型进行自动装配)

```java
@Service
public class UserServiceImp1 implements UserService{
	@Autowired
	private UserDao userDao;
}
```

1. 属性加上@Autowired后不需要getter)和setter()方法, Spring会自动注入。
2. 默认情况下required为true,即一定要找到匹配bean,否则报异常
3. 或者@Autowired(required=false)如果根据类型找不到,不报错。显示为null
4. 如下:如果spring上下文中没有找到该类型的bean时，才会使用

```java
@Autowired(required=false)
private UserDao userDao=new UserDao();
```

**`@Autowired`根据bean类型从spring上线文中进行查找,注册类型必须唯一, 否则报异常**

在接口前面标上`@Autowired`注释使得接口可以被容器注入,如:当接口存在两个实现类的时候必须使用`@Qualifier`指定注入哪个实现类,否则可以省略
@Qualifier("userDao") // (是根据名称进行自动装配的)

```java
@Autowired
@Qualifier ("userDaoImp11")
private UserDao userDao;
```

@Value("qfjy") //对属性进行赋值

#### `@Resource`

`@Resource` (这个注解属于J2EE的) , 默认按照名称进行装配,名称可以通过name属性进行指定，如果没有指定name属性,当注解写在字段上时,默认取字段名进行按照名称查找,如果注解写在setter方法上默认取属性名进行装配。当找不到与名称匹配的bean时才 按照类型进行装配。但是需要注意的是,如果name属性一旦指定,就只会按照名称进行装配。

```java
@Resource
//默认会根据字段名查找User Dao接口下的us erDaoImp11名称的BEAN进行注入
private UserDao user DaoImp 11;
```

```java
@Resource(name="userDaoImpl") //查找userDaoImp1名称进行注入
private UserDao us er DaoImp11;
```

#### `@Autowired`和`@Resouces`的区别?

这两个注解都是用于完成依赖注入。
`@Autowired`是Spring提供的注解，根据byType进行匹配,如果有相同的类型，可通过`@Qualifier`进行byName匹配
`@Resources`是J2EE提供的注解,根据byName进行匹配,如果名称未找到,根据byType进行匹配

### 6. 生命周期

通过在目标方法上标注`@PostConstruct`和`@PreDestroy`注解指定初始化或销毁方法，可以定义任意多个方法

```java
@PostConstruct
public void init(){
	System.out.println();
}
@PreDestroy
public void destroy(){
	System.out.println();
}
```

### 7. 完整总结

|                  | 基于XML配置                                                  | 基于注解配置                                                 | 基于Java类配置                                               |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Bean定义         | 在XML文件中通过<bean>元素定义Bean                            | 在Bean实现类处通过标注`@Compoment`或衍型类`@Repository`,`@Service`及`@Controller`定义Bean | 在标注了`@Configuration`的java类中。通过在类方法上标注`@Bean`定义一个Bean。方法必须提供Bean的实例化逻辑 |
| Bean名称         | 通过<bean>的id或name属性定义                                 | 通过注解的value属性定义，如`@Component(userDao)`.默认名称为小写字母打头的类名(不带包名) :userDao | 通过`@Bean`的name属性定义，如`@Bean(userDao)`。默认名称为方法名 |
| Bean注入         | 通过<property>子元素或通过p命名空间的动态属性，如：p:userDao-ref="userDao"进行注入 | 通过在成员变量或方法入参处标注`@Autowired`,按类型匹配自动注。还可以配合使用`@Qualfler`按名称匹配方式注入 | 比较灵活,可以通过在方法处通过`@Autowired`方法入参绑定Bean ,然后在方法中通过代码进行注入.还可以通过调用配置类的`@Bean`方法进行注入 |
| Bean生命过程方法 | 通过<bean>的init-method和destory-method属性指定Bean实现类的方法名。最多只能指定一个初始化方法和一个销毁方法。 | 通过在目标方法上标注`@PostConstruct`和`@PreDestroy`注解指定初始化或销毁方法，可以定文任意多个方法 | 通过`@Bean`的initMethod或destoryMethod指定一个初始化或销毁方法。对于初始化方法来说,你可以直接在方法内部通过代码的方式灵活定义初始化逻辑 |
| Bean作用域范围   | 通过<bean>的scope属性指定,如:<bean class="com. qfjy. userDao" scope="prototype" /> | 通过在类定义处标注`@Scope`指定,如`@Scope("prototype")`       | 通过在Bean方法定义处标注`@Scope`指定                         |
| 适合场景         | 1 ) Bean实现类来源于第三方类库,如DataSource，JdbcTemplate等。因无法在类中标注注解，通过XML配置方式较好<br/>2)命名空间的起置，如aop. context等 。只能采用基于XML的配置 | 1 ) Bean实现类来源于第三方类库,如DataSource，JdbcTemplate等。因无法在类中标注注解通过XML配置方式较好<br/>2)命名空间的起置，如aop. context等 。只能采用基于XML的配置 | 基于Java类配置的优势在于可以通过代码方式控制Bean初始化的整体逻辑。所以如果实例化Bean的逻辑比较复杂.则比较适合用基于Java类配置的方式 |



### 8. Java类配置

#### Spring1.x时代

在Spring1.x时代,都是通过xml文件配置bean ,随着项目的不断扩大,需要将xm|配置分放到不同的配置文件
中,需要频繁的在java类和xm|配置文件中切换。

#### Spring2.x时代

随着JDK 1.5带来的注解支持, Spring2.x可以使用注解对Bean进行声明和注入,大大的减少了xml配置文件,同时
也大大简化了项目的开发。
那么,问题来了,究竟是应该使用xml还是注解呢?

**最佳实践:**

1、 应用的基本配置用xml,比如:数据源、资源文件等;
2、业务开发用注解 ,比如: Service中注入bean等;

Spring3.x到Spring4.x到Spring5.x

从Spring..x开始提供了]ava配置方式,使用Java配置方式可以更好的理解你配置的Bean ,现在我们就处于这个时
代,并且Spring4.x和Springboot都推荐使用java配置的方式。

**Java配置是Spring4.x推荐的配置方式,可以完全替代xml配置**



## Lombok

maven配置

```xml
<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.10</version>
    <scope>provided</scope>
</dependency>
```

对应的pojo下如下添加注释：

lombok注解：

​	[官方文档](http://projectlombok.org/features/index)

常用注解：

​	`@Data`：注释在类上，提供所有属性的getter和setter方法

​	此外还提供了equals、canEqual、hashCode、toString方法

​	`@Setter`：注解在属性上，为属性提供setter方法

​	`@Getter`：注解在属性上，为属性提供getter方法

​	`@Log4j`：注解在类上，为类提供命名为log的log4j对象

​	`@NoArgsConstructor`：注解在类上，为类提供一个无参的构造函数

​	`@AllArgsConstructor`：注解在类上，为类提供一个全参的构造函数



## SpringAOP

### 1. 需求分析

日志记录功能，监视用户对特殊功能操作的每一个流程和步骤。

### 2. 理解DRY规则

在软件开发领域有一个非常重要的规则：Don‘t Repeat Yourself，就是所谓的DRY规则，意思就是不要重复写代码。

### 3.Aop简介

**Aop为Aspect  Oriented Programming**的缩写，意为面向切面编程

AOP提供了另外-种思考程序结构的角度,弥补了OOP (面向对象编程)的不足。就像刚开始理解00概念-样，
对于新手来说AOP也是非常抽象难以理解的,不能仅从一个概念上去定义AOP。假如我们有一个系统 ,分为好多个
模块,每个模块都负责处理一项重要的功能。 但是每个模块都需要一一些相似的辅助功能如安全、日志输出等等。这就是一种交叉业务,而这种”交叉”非常适合用AOP来解决。
**Aspect切面**
AOP它利用一种称为"横切”的技术,剖解开封装的对象内部,并将那些影响了多个类的公共行为封装到一一个可重用
模块,并将其名为"Aspect" ,即切面。

![image-20191026000743214](C:\Users\mzw\AppData\Roaming\Typora\typora-user-images\image-20191026000743214.png)



所谓"**切面**”，简单地说,就是将那些与业务无关,却为业务模块所共同调用的逻辑或责任封装起来。
切面就是横切面,代表的是-一个普遍存在的共有功能。
**原理**
AOP技术是建立在Java语言的反射机制与动态代理机制之上的

<img src="C:\Users\mzw\AppData\Roaming\Typora\typora-user-images\image-20191026010214473.png" alt="image-20191026010214473" style="zoom:80%;" />

**总结**

面向切面编程( AOP )就是对软件系统不同关注点的分离, 开发者通过拦截方法调用并在方法调用前后添加辅助代码。

*拦截器（Interceptor）*是动态拦截Action调用的对象，它提供了一种机制使得开发者可以在一个Action开始之前或执行之后插入需要的代码。

### 4. AOP常用术语

1. **切面( Aspect)** : 切面是你要实现的交叉功能。它是应用系统模块化的一一个切面或领域。
2. **连接点( Join point)** : 连接点是应用程序执行过程中插入切面的地点(在程序执行过程中某个特定的点)在
   Spring AOP中一个连接点代表-个方法的执行。 这个点可以是方法调用,异常抛出或者是要修改的字段。通
   过申明一-个import org.aspectj.lang.JoinPoint类型的参数可以使通知( Advice )的主体部分获得连接点信
   息。
3. **通知( Advice)** : 通知切面的实际实现。它通知应用系统新的行为。通知包括好多种类,在后面单独列出。
4. **切入点( Pointcut)** : 切入点定义了通知应该应用在哪些连接点。通知( Advice )可以应用到AOP的任何连
   接点。通知( Advice )将和一一个切入点表达式关联,并在满足这个连接点的切入点上运行(例如:在执行一个
   特定名称的方法时)切入点表达式如何和连接点匹配是AOP的核心Spring使用缺省的Aspect切入点的语法。
5. **引入( Introduction)** :引入允许你为已经存在的类添加新的方法和属性。Spring允许引入新的接口 (以及
   一一个对应的实现)到任何被代理的对象。
6. **目标对象( Target Object)** : 目标对象是被通知对象。Spring AOP是运行时代里实现的,所以这个对象永远
   是一个被代理对象。
7. **AOP代理( AOP Proxy)** : 代理是将通知( Advice )应用到目标对象后创建的对象。
8. **织入( Weaving)** :把切面( Aspect )连接到其它的应用程序类型或者对象上来创建一个被通知 ( advised )
   的对象。可以在编译时做这件事(例如使用Aspect编译器) ,也可以在类加载或运行时完成。Spring和其他
   纯Java AOP框架一样，在运行时完成织入。

#### 4.1通知(Advice)类型

**前置通知( Before Advice)：**在一一个连接点之前执行的通知,但这个通知不能阻止连接点前的执行(除非它抛出
异常)
**后置通知( After Advice) ：**
**返回后通知( After returning advice) ：** 在一个连接点正常完成后执行的通知。例如: -一个方法正常返回,没
有抛出任何异常。
**抛出后通知( After throwing advice) :** 在一一个方法抛出异常时执行的通知。
**环绕通知( Around advice) ：**包围一一个连接点的通知,就像方法调用。这是最强大的一种通知类型。环绕通知
可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回他们自己的返回值域或抛出异
常来结束执行。



### 5. Aop开发示例

导入依赖的jar包：

Spring Aop：`aspectjweaver.jar`,`spring-aop-x.x.x.RELEASE.jar`,`spring-aspects-x.x.x.RELEASE.jar`

#### 实现的开发方式

1. 编程方式
2. 配置方式
3. 基于注解方式`@AspectJ`注解驱动的切面

## 基于注解的Spring AOP的配置和使用日志记录

### 1. 创建切面

```java
package com.mzw.aop;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
/**
 * 日志切面管理
 */
@Component
@Aspect     //一个切面
public class LogAspect {
    /**
     * @Before  通知的类型（前置通知），目标方法之前执行的方法
     * execution    设置切入点知道对哪个方法做前置通知操作
     * 第一个*代表方法返回值
     * 第二个*代表类名称
     * 第三个*代表方法名称
     * 最后（..）代表所有参数
     */
    @Before(value ="execution(* com.mzw.service.impl.*.*(..))")
    public void before(){
        System.out.println("日志-------->目标方法之前执行");
    }
}
```

### 2. 在applicationcontext.xml中加入配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       ">

        <!-- IOC指定扫描包 -->
        <context:component-scan base-package="com.mzw">
        </context:component-scan>

        <!--AOP启动配置     自动激活动态代理@AspectJ @Before @Pointcut-->
        <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>
```

### 3. PointCut execation切入点

**切入点( Pointcut)** :切入点定义了通知应该应用在哪些连接点。通知( Advice )可以应用到AOP的任何连接
点。通知( Advice )将和一一个切入点表达式关联,并在满足这个连接点的切入点上运行(例如:在执行-一个特定
名称的方法时)切入点表达式如何和连接点匹配是AOP的核心Spring使用缺省的Aspectj切入点的语法。
示例:

```java
例: execution (public * com.qfjy.service..*(..))
整个表达式可以分为五个部分:
1、execution(): : 表达式主体。
2、第一个*号:表示返回类型，*号表示所有的类型。
3、包名:表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com. qfjy. service包、子孙包下所有类的方法。
4、第二个*号:表示类名，*号表示所有的类。
5、*(..) :最后这个星号表示方法名，*号表示所有的方法，后面括弧里表示方法的参数，两个句点表示任何参数0或多个
6、public可以省略。代表访问public方法
```

Pointcut可以有下列方式来定义或者通过&&||和!的方式进行组合

```java
("execution(* com. qfjy. controller.*.*(..))||execution(* com.qfjy.service.*.*(..))")
```

```
例如:
1.execution(* *(..); 代表所有类中的所有方法 ，
参数说明:第一个* [返回类型为任意]，第二* [方法名为任意]，(..)表示的是方法参数为任意
2.execution(* set*(..)) ;任何一 个以“set”开始的方法的执行:
3.execution(* *(* ,String));方法参数第-一个为任意参数类型，第二个为String
4.execution(* cn.qfjy.service.*.*(..)); service包 下的所有类的所有方法
```

### 4. JoinPoint连接点

**连接点( Join point)** :连接点是应用程序执行过程中插入切面的地点(在程序执行过程中某个特定的点)在SpringAOP中一个连接点代表-个方法的执行。 这个点可以是方法调用,异常抛出或者是要修改的字段。通过申明一个`org.aspectj.langJoinPoint`类型的参数可以使通知( Advice)的主体部分获得连接点信息。

**希望日志方法记录信息更为详细(JoinPoint)**
AspectJ使用`org.aspectj.lang.JoinPoint`接口表示目标类连接点对象,如果是环绕增强时,使用`org.aspectj.lang.ProceedingJoinPoint`表示连接点对象,该类是JoinPoint的子接口。任何一个增强方法都可以通过将第一个入参声明为JoinPoint访问到连接点上下文的信息。这两个接口的主要方法:

```java
JoinPoint:
java.larg.object[] getArgs(): 获取连接点方法运行时的入参列表(入参的参数值) ;
Signature getSignature(): 获取连接点的方法签名对象(方法名称) ;
java.lang.object getTarget(): 获取连接点所在的目标对象;
java.lang.object getThis(): 获取代理对象本身;
```

```java
@Before(value ="execution(public * com.mzw.service.impl.UserServiceImpl.*(..))")
public void before(JoinPoint joinPoint){
    Object[] args = joinPoint.getArgs();//得到方法所有参数信息
    for (Object obg :args){
    	System.out.print(obg+"\t");
    }
    System.out.println();
    String methodName = joinPoint.getSignature().getName(); //方法名称
    String name = joinPoint.getTarget().getClass().getName();
    System.out.println("日志：------->前置通知："+name+"\t"+methodName);
}
```

### 通知

#### 通知(Advice)类型：

**前置通知( Before Advice) :**在一个连接点之前执行的通知,但这个通知不能阻止连接点前的执行(除非它抛出异常)
**后置通知( After Advice ) :** 在目标方法执行后(无论是否出现异常)执行的通知
**返回后通知( After returning advice) :** 在一个连接点正常完成后执行的通知。例如: -一个方法正常返回,没有抛出任何异常。
**抛出后通知( After throwing advice) :** 在-一个方法抛出异常时执行的通知。

**Finally后通知( After finally advice) :**当某连接点退出的时候执行的通知(不论是正常返回还是抛出异常)。
**环绕通知( Around advice) :**包围-一个连接点的通知,就像方法调用。这是最强大的一种通知类型。环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回他们自己的返回值域或抛出异常来结束执行。

#### 后置通知( After Advice )

**后置通知( After Advice ) :**在目标方法执行后(无论是否出现异常)执行的通知

```java
@After(value = "execution(* com.mzw.service.impl.UserServiceImpl.*(..))")
public void after(JoinPoint joinPoint){
	String methodName = joinPoint.getSignature().getName(); //方法名称
	String name = joinPoint.getTarget().getClass().getName();
	System.out.println("日志：------->后置通知："+methodName + " 返回："+name);
}
```

#### 返回后通知( After returning advice)

**返回后通知( After returning advice) :** 在一个连接点正常完成后执行的通知。例如: -一个方法正常返回,没有抛出任何异常。

```java
/****
* 返回后通知( After returning advice) :** 在一个连接点正常完成后执行的通知。例如: -一个方法正常返回,没有抛出任何异常。
*  value 切入点的表达式
*  pointcur 切入点的返回值
*  returning：String 返回值定义的别名
*/
@AfterReturning(pointcut = 
     "execution(* com.mzw.service.impl.UserServiceImpl.login(..))",returning = "result")
public void afterReturn(JoinPoint joinPoint,Object result){//返回参数名称要和上面的一致
    String methodName = joinPoint.getSignature().getName();    
    String className = joinPoint.getTarget().getClass().getName();    
    if (result!=null){    
        System.out.println("【返回后通知】----》登陆成功");        
    }else{  
        System.out.println("【返回后通知】----》用户名或密码错误");       
    }    
}	
```

#### 抛出后通知( After throwing advice) 

**抛出后通知( After throwing advice) :** 在-一个方法抛出异常时执行的通知。

```
异常通知:在方法出现异常后，会执行的通知代码。
能够访问到异常对象、同时可通过指定特定异常，执行相应通知代码
```

```java
/***
* 异常通知：方法出现异常会执行
* throwing ：一场别名，必须和方法名一样
* 可以通过改变Exception的异常类型，来对特定异常执行响应代码
* @param joinPoint
* @param exception
*/
@AfterThrowing(pointcut=
"execution(* com.mzw.service.impl.UserServiceImpl.testException(..))",
throwing="exception")
public void afterThrowing(JoinPoint joinPoint, NullPointerException exception){
	if (exception!=null){
	System.out.println("【异常通知】----》"+joinPoint.getTarget().getClass().getName()+"\t"+joinPoint.getSignature().getName()+"\t"+exception);
	}
}
```

#### 环绕通知( Around advice)

**环绕通知( Around advice) :**包围-一个连接点的通知,就像方法调用。这是最强大的一种通知类型。环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回他们自己的返回值域或抛出异常来结束执行。

```java
/***
* 环绕通知
* 环绕通知(Around advice) :包围一个连接点的通知，就像方法调用。
* 这是最强大的一种通知类型。环绕通知可以在方法调用前后完成自定义的行为。
* 它也会选择是否继续执行连接点或直接返回他们自己的返回值域或抛出异常来结束执行
*/
@Around(value="execution(* com.mzw.service.impl.UserServiceImpl.selectById(..))")
public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
    String methodName = proceedingJoinPoint.getSignature().getName();
    String className = proceedingJoinPoint.getTarget().getClass().getName();
    Object object = null;
    System.out.println("前置通知");
    try {
        object = proceedingJoinPoint.proceed();
        System.out.println("后置通知");
    } catch (Throwable throwable) {
    	//抛出后通知
   		System.out.println("抛出后通知");
    	throwable.printStackTrace();
    }
    System.out.println("返回后通知");
}
```

### 技巧

为了方便对PointCut管理，减少每个类上方的内容匹配

可以通过定义切入点实现

```java
@Pointcut (value="execution(* com. qfjy. service. imp1.userServiceImp1.*(..))")
public void userPointCut(){}
```

对应通知方法切入点如下:

```java
//前置通知
@Before("userPointCut()")
public void before(JoinPoint join){
	//代码省略....
}
//环绕通知
@Around(value="userPointCut ()")
public object ar oundAdvi ce(Proceedi ngJoinPoint join){
	//代码省略
}
```

### 总结

Spring AOP的特点:功能非常强大,开发编写非常容易。
在实际开发中,常用于:
	日志的记录管理、统-一的异常管理、事务的管理等。

## Spring支持的常用数据库事务传播属性和事务隔离级别

### 事务的传播行为

当事务方法被另一个事务调用的时候，必须指定事务应该如何传播。例如：方法可能继续在现有事务中运行，也可能开启一个新的事务，并在自己的事务中运行。
事务的传播行为可以由传播属性决定。Spring定义了7种类传播行为。

传播属性|描述
---|---
REQUIRED|如果有事务在运行，当前的方法就在这个事务内运行，否则，就启动一个新的事务，并在自己的事务内运行
REQUIRED_NEW|当前的方法必须启动新的事务，并在它自己的事务内运行，如果有事务在运行，就将他挂起
SUPPORTS|如果有事务在运行，当前的方法就在这个事务内运行，否则他可以不运行在事务内
NOT_SIPPORTED|当前的方法不应该运行在该事务中，如果有运行的事务，将他挂起
MANDATORY|当前的方法必须运行在事务的内部，如果没有正在运行的事务，就抛出异常
NEVER|当前方法不应该运行在事务中，如果有运行的事务，就抛出异常
NESTED|如果有事务在运行，当前的方法就应该在这个事务的嵌套事务内运行，否则，就启动一个新的事务，并在它自己的事务内运行

事务的传播属性可以在@Transactional注解的propagation属性中定义。


## SpringMVC的工作流程

处理模型数据方式一：将返回值设置为ModelAndView
处理模型数据方式二：方法的返回值仍为String类型，在方法的入参中传入Map、Model或者ModelMap

不管方法一还是方法二，SpringMVC都会转化为一个ModelAndView对象


## MyBatis中当实体类中的属性名和表中的字段名不一样，怎么办？
解决方案：
1. 写sql语句时起别名（推荐）
2. 在Mybatis的全局配置文件中开启驼峰命名规则（可以将数据库中的下划线映射为驼峰命名）
3. 在Mapper映射文件中使用resultMap来自定义映射规则


## Servlet生命周期
```java
当容器【（Web容器）Tomcat服务器】
当客户端发送请求时，容器会判断当前容器中是否存在Servlet，如果存在，直接调用，创建该对象容器。
```

- Servlet 通过调用 **init ()** 方法进行初始化。
- Servlet 调用 **service()** 方法来处理客户端的请求。
- Servlet 通过调用 **destroy()** 方法终止（结束）。
- 最后，Servlet 是由 JVM 的垃圾回收器进行垃圾回收的。
