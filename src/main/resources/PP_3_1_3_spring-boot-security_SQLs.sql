# При разработке часто использовались запросы, вывел в этот фаил.
DROP DATABASE IF EXISTS`PP_3_1_3_spring-boot-security`;
CREATE SCHEMA IF NOT EXISTS `PP_3_1_3_spring-boot-security` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

create table users (username varchar(255) not null primary key, password varchar(255) not null, eneble boolean not null);
create table authoririties (username varchar(255) not null, authoririty varchar(255) not null, foreign key (username) references users (username), unique(username, authoririty));
select * from users;
select * from authoririties;


SHOW DATABASES;
USE `PP_3_1_3_spring-boot-security`;
SHOW TABLES FROM `PP_3_1_3_spring-boot-security`;
SELECT * FROM users;
SELECT * FROM t_role;
SELECT * FROM users_roles;


-- ----------------------------------------------------------------------------------------------------
-- ЗАПОЛНИТЬ ВРУЧНУЮ ТАБЛИЦУ С ПОЛЬЗОВАТЕЛЯМИ --
USE `PP_3_1_3_spring-boot-security`;

-- Добавление ролей
INSERT INTO t_role (role_id, role_name) VALUES (1, 'USER');
INSERT INTO t_role (role_id, role_name) VALUES (2, 'ADMIN');

-- Добавление пользователей
INSERT INTO users (user_id, address, date_birth, fullName, password, username) 
VALUES (1, 'User Address', '1990-01-01', 'User Name', '$2a$10$Iom7deSLgxAxykvuANH2s.KpMy5xWbjgQmcsuiycdJt0UMoQflKaC', 'user'); -- user // u

INSERT INTO users (user_id, address, date_birth, fullName, password, username) 
VALUES (2, 'Admin Address', '1985-01-01', 'Admin Name', '$2a$10$dWJyopJqWj/PDxEozd6MzOzTwV.5c2GNoU6hUiou0YOF2CHkfDoZK', 'admin'); -- admin // a

-- Связывание пользователей с ролями
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);  -- Связываем пользователя user с ролью USER
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);  -- Связываем пользователя admin с ролью ADMIN
-- ----------------------------------------------------------------------------------------------------



drop table if exists users;
drop table if exists t_role;

alter table users add constraint FKm17m36qxyja8k4t4yqhkp6lr9 foreign key (fk_address_id) references address (address_id);


