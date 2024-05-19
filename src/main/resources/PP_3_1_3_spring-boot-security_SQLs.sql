# При разработке часто использовались запросы, вывел в этот фаил.
DROP DATABASE IF EXISTS`PP_3_1_3_spring-boot-security`;
CREATE SCHEMA IF NOT EXISTS `PP_3_1_3_spring-boot-security` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

SHOW DATABASES;
USE `PP_3_1_3_spring-boot-security`;
SHOW TABLES FROM `PP_3_1_3_spring-boot-security`;
SELECT * FROM 'PP_3_1_3_spring-boot-security';

drop table if exists users;

alter table users add constraint FKm17m36qxyja8k4t4yqhkp6lr9 foreign key (fk_address_id) references address (address_id);


