# <font face=幼圆 color=white>[启动器](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#using.build-systems.starters)</font>

## <font face=幼圆 color=white>*表 1. Spring Boot 应用程序启动器*</font>

| 姓名                                          | 描述                                                         |
| :-------------------------------------------- | :----------------------------------------------------------- |
| `spring-boot-starter`                         | 核心启动器，包括自动配置支持、日志记录和 YAML                |
| `spring-boot-starter-activemq`                | 使用 Apache ActiveMQ 进行 JMS 消息传递的启动器               |
| `spring-boot-starter-amqp`                    | 使用 Spring AMQP 和 Rabbit MQ 的入门                         |
| `spring-boot-starter-aop`                     | 使用 Spring AOP 和 AspectJ 进行面向方面编程的入门者          |
| `spring-boot-starter-artemis`                 | 使用 Apache Artemis 的 JMS 消息传递启动器                    |
| `spring-boot-starter-batch`                   | 使用 Spring Batch 的入门者                                   |
| `spring-boot-starter-cache`                   | 使用 Spring Framework 的缓存支持的 Starter                   |
| `spring-boot-starter-data-cassandra`          | 使用 Cassandra 分布式数据库和 Spring Data Cassandra 的入门者 |
| `spring-boot-starter-data-cassandra-reactive` | 使用 Cassandra 分布式数据库和 Spring Data Cassandra Reactive 的入门者 |
| `spring-boot-starter-data-couchbase`          | 使用 Couchbase 面向文档的数据库和 Spring Data Couchbase 的入门 |
| `spring-boot-starter-data-couchbase-reactive` | 使用 Couchbase 面向文档的数据库和 Spring Data Couchbase Reactive 的初学者 |
| `spring-boot-starter-data-elasticsearch`      | 使用 Elasticsearch 搜索和分析引擎和 Spring Data Elasticsearch 的入门者 |
| `spring-boot-starter-data-jdbc`               | 使用 Spring Data JDBC 的入门者                               |
| `spring-boot-starter-data-jpa`                | 将 Spring Data JPA 与 Hibernate 一起使用的入门               |
| `spring-boot-starter-data-ldap`               | 使用 Spring Data LDAP 的入门者                               |
| `spring-boot-starter-data-mongodb`            | 使用 MongoDB 面向文档的数据库和 Spring Data MongoDB 的入门   |
| `spring-boot-starter-data-mongodb-reactive`   | Starter for using MongoDB document-oriented database and Spring Data MongoDB Reactive |
| `spring-boot-starter-data-neo4j`              | 使用 Neo4j 图形数据库和 Spring Data Neo4j 的入门             |
| `spring-boot-starter-data-r2dbc`              | 使用 Spring Data R2DBC 的入门者                              |
| `spring-boot-starter-data-redis`              | 将 Redis 键值数据存储与 Spring Data Redis 和 Lettuce 客户端一起使用的入门 |
| `spring-boot-starter-data-redis-reactive`     | 使用带有 Spring Data Redis 反应式和 Lettuce 客户端的 Redis 键值数据存储的启动器 |
| `spring-boot-starter-data-rest`               | 使用 Spring Data REST 通过 REST 公开 Spring Data 存储库的启动器 |
| `spring-boot-starter-freemarker`              | 使用 FreeMarker 视图构建 MVC Web 应用程序的 Starter          |
| `spring-boot-starter-groovy-templates`        | 使用 Groovy 模板视图构建 MVC Web 应用程序的 Starter          |
| `spring-boot-starter-hateoas`                 | 使用 Spring MVC 和 Spring HATEOAS 构建基于超媒体的 RESTful Web 应用程序的启动器 |
| `spring-boot-starter-integration`             | 使用 Spring Integration 的入门者                             |
| `spring-boot-starter-jdbc`                    | 使用 JDBC 和 HikariCP 连接池的 Starter                       |
| `spring-boot-starter-jersey`                  | 使用 JAX-RS 和 Jersey 构建 RESTful Web 应用程序的初学者。一个替代方案[`spring-boot-starter-web`](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#spring-boot-starter-web) |
| `spring-boot-starter-jooq`                    | 使用 jOOQ 通过 JDBC 访问 SQL 数据库的入门程序。替代[`spring-boot-starter-data-jpa`](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#spring-boot-starter-data-jpa)或[`spring-boot-starter-jdbc`](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#spring-boot-starter-jdbc) |
| `spring-boot-starter-json`                    | 读写json的starter                                            |
| `spring-boot-starter-jta-atomikos`            | 使用 Atomikos 进行 JTA 事务的启动器                          |
| `spring-boot-starter-mail`                    | 使用 Java Mail 和 Spring Framework 的电子邮件发送支持的 Starter |
| `spring-boot-starter-mustache`                | 使用 Mustache 视图构建 Web 应用程序的入门程序                |
| `spring-boot-starter-oauth2-client`           | 使用 Spring Security 的 OAuth2/OpenID Connect 客户端功能的入门者 |
| `spring-boot-starter-oauth2-resource-server`  | 使用 Spring Security 的 OAuth2 资源服务器特性的入门者        |
| `spring-boot-starter-quartz`                  | 使用 Quartz 调度器的启动器                                   |
| `spring-boot-starter-rsocket`                 | 用于构建 RSocket 客户端和服务器的启动器                      |
| `spring-boot-starter-security`                | 使用 Spring Security 的入门者                                |
| `spring-boot-starter-test`                    | 使用包括 JUnit Jupiter、Hamcrest 和 Mockito 在内的库来测试 Spring Boot 应用程序的 Starter |
| `spring-boot-starter-thymeleaf`               | 使用 Thymeleaf 视图构建 MVC Web 应用程序的启动器             |
| `spring-boot-starter-validation`              | 使用带有 Hibernate Validator 的 Java Bean Validation 的 Starter |
| `spring-boot-starter-web`                     | 使用 Spring MVC 构建 Web 应用程序的入门程序，包括 RESTful 应用程序。使用 Tomcat 作为默认的嵌入式容器 |
| `spring-boot-starter-web-services`            | 使用 Spring Web 服务的入门者                                 |
| `spring-boot-starter-webflux`                 | 使用 Spring Framework 的响应式 Web 支持构建 WebFlux 应用程序的启动器 |
| `spring-boot-starter-websocket`               | 使用 Spring Framework 的 WebSocket 支持构建 WebSocket 应用程序的 Starter |



## <font face=幼圆 color=white>*表 2. Spring Boot 生产启动器*</font>

| 姓名                           | 描述                                                         |
| :----------------------------- | :----------------------------------------------------------- |
| `spring-boot-starter-actuator` | 使用 Spring Boot 的 Actuator 的 Starter，它提供了生产就绪的特性来帮助你监控和管理你的应用程序 |



## <font face=幼圆 color=white>*表 3. Spring Boot 技术入门*</font>

| 姓名                                | 描述                                                         |
| :---------------------------------- | :----------------------------------------------------------- |
| `spring-boot-starter-jetty`         | 使用 Jetty 作为嵌入式 servlet 容器的启动器。一个替代方案[`spring-boot-starter-tomcat`](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#spring-boot-starter-tomcat) |
| `spring-boot-starter-log4j2`        | 使用 Log4j2 进行日志记录的启动器。一个替代方案[`spring-boot-starter-logging`](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#spring-boot-starter-logging) |
| `spring-boot-starter-logging`       | 使用 Logback 进行日志记录的启动器。默认日志记录启动器        |
| `spring-boot-starter-reactor-netty` | 使用 Reactor Netty 作为嵌入式响应式 HTTP 服务器的启动器。    |
| `spring-boot-starter-tomcat`        | 使用 Tomcat 作为嵌入式 servlet 容器的启动器。使用的默认 servlet 容器启动器[`spring-boot-starter-web`](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#spring-boot-starter-web) |
| `spring-boot-starter-undertow`      | 使用 Undertow 作为嵌入式 servlet 容器的启动器。一个替代方案[`spring-boot-starter-tomcat`](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#spring-boot-starter-tomcat) |