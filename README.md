## 说明
学习张逸老师课程《领域驱动设计实践》的雇员薪资支付示例的实现，以达成以下几个目的：
* 领域驱动设计下对基础框架的影响
* 尽量完整实现整个示例，在具有界面展示、数据录入查询的接近完整系统的情况下，体会领域驱动的实践
* 练习在DDD下领域模型应用JPA的配置
* 练习场景驱动、测试驱动与领域驱动的配合

张逸老师示例地址：https://github.com/agiledon/payroll-ddd



## 环境

开发环境：Java 8
数据库：MySQL 8.0 Community

### 数据库环境准备

项目默认的数据库用户名为sa，密码为123456，数据库URI为localhost。在安装了MySQL 8.0后，若数据库服务器信息与默认信息不同，请修改如下文件。

**flywaydb的数据库配置**

在`pom.xml`文件的`<plugins>`中配置了如下内容：

```xml
<plugin>
   <groupId>org.flywaydb</groupId>
   <artifactId>flyway-maven-plugin</artifactId>
   <version>6.0.4</version>
   <configuration>
      <driver>com.mysql.jdbc.Driver</driver>
      <url>jdbc:mysql://localhost:3306/payroll?serverTimezone=UTC</url>
      <user>sa</user>
      <password>123456</password>
   </configuration>
</plugin>
```

一旦准备好flywaydb的环境，就可以运行命令执行DB的清理：

```
mvn flyway:clean
```

或执行命令执行DB的迁移：

```
mvn flyway:migrate
```

**测试环境准备**

若要运行集成测试，应首先修改test/resources/META-INF文件夹下文件`persistence.xml`的相应内容：

```xml
<?xml version="1.0" encoding="UTF-8"?>

<persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
   http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">

    <persistence-unit name="PAYROLL_JPA" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.ejb.HibernatePersistence</provider>
        <class>top.dddclub.payroll.employeecontext.domain.Employee</class>

        <properties>
            <property name="hibernate.connection.driver_class" value="com.mysql.cj.jdbc.Driver"/>
            <property name="hibernate.connection.url" value="jdbc:mysql://localhost:3306/payroll?serverTimezone=UTC"/>
            <property name="hibernate.connection.username" value="sa"/>
            <property name="hibernate.connection.password" value="123456"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>
            <property name="hibernate.archive.autodetection" value="class"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hbm2ddl.auto" value="update"/>
        </properties>
    </persistence-unit>
</persistence>
```

### 运行测试

默认情况下，如果运行`mvn test`则只会运行单元测试。如果确保数据库已经准备好，且通过flywaydb确保了数据库的表结构与测试数据已经准备好，则可以运行`mvn integration-test`。该命令会运行所有测试，包括单元测试和集成测试。

**注意：**项目中所有的单元测试以`Test`为测试类后缀，所有集成测试以`IT`为测试类后缀。