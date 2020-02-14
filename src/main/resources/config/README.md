定时任务或操作数据库删除3个月前的数据
1.写一个存储过程
create procedure del_data()
BEGIN
    DELETE FROM error_log_info WHERE create_date < DATE_SUB(CURDATE(),INTERVAL 3 MONTH);
END
1.1 使用存储过程
call del_data();
2.开启事件调度器
SET GLOBAL event_scheduler = ON;
3.创建事件 从2020-2-12零点开始，每隔一天执行del_data存储过程
create event del_event  
on schedule 
EVERY 1 day  
STARTS '2020-2-12 00:00:00'  
do call del_data()