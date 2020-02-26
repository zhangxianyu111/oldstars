切换数据源注意：

1.applicationContext.xml
   修改默认数据源<!--##### 数据库连接池配置 ##### -->
         <bean id="dataSource"
               class="com.demo.dynamicdatasource.DynamicDataSource">
             <property name="defaultTargetDataSource" ref="mysqlDS" /><!--设置默认数据源 -->
             <property name="targetDataSources"><!--设置多个数据源 -->
                 <map>
                     <entry key="mysqlDataSource" value-ref="mysqlDS" />
                     <entry key="postgresqlDataSource" value-ref="postgresqlDS" />
                 </map>
             </property>
         </bean>
2.log4j.properties
   修改数据库存储
   mysql:
      log4j.appender.resWarn_database.Threshold=WARN
      log4j.appender.resWarn_database.BufferSize=1
      log4j.appender.resWarn_database.layout=org.apache.log4j.PatternLayout
      log4j.appender.resWarn_database=org.apache.log4j.jdbc.JDBCAppender
      log4j.appender.resWarn_database.driver=com.mysql.jdbc.Driver
      log4j.appender.resWarn_database.URL=jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=UTF-8
      log4j.appender.resWarn_database.user=root
      log4j.appender.resWarn_database.password=root
      log4j.appender.resWarn_database.sql=insert into reslog (class,method,createtime,loglevel,exception,msg,modulename) VALUES ('%X{class}','%X{method}','%d{yyyy-MM-dd hh:mm:ss}','%p','%X{exception}','%m','resWarn')
   pgsql:
      log4j.appender.resWarn_database.Threshold=WARN
      log4j.appender.resWarn_database.BufferSize=1
      log4j.appender.resWarn_database.layout=org.apache.log4j.PatternLayout
      log4j.appender.resWarn_database=org.apache.log4j.jdbc.JDBCAppender
      log4j.appender.resWarn_database.driver=org.postgresql.Driver
      log4j.appender.resWarn_database.URL=jdbc:postgresql://localhost:5432/demo
      log4j.appender.resWarn_database.user=postgres
      log4j.appender.resWarn_database.password=postgres
      log4j.appender.resWarn_database.sql=insert into public.reslog (class,method,create_time,log_levle,exception,msg,module_name) VALUES ('%X{class}','%X{method}','%d{yyyy-MM-dd hh:mm:ss}','%p','%X{exception}','%m','resWarn')
3.quartz.properties

   mysql:
   org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
   
   pgsql:
   org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.PostgreSQLDelegate
   
