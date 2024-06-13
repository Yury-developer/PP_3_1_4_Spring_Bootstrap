# При разработке часто использовались запросы, вывел в этот фаил.
DROP DATABASE IF EXISTS`PP_3_1_4_spring-bootstrap`;
CREATE SCHEMA IF NOT EXISTS `PP_3_1_4_spring-bootstrap` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

create table users (username varchar(255) not null primary key, password varchar(255) not null, eneble boolean not null);
create table authoririties (username varchar(255) not null, authoririty varchar(255) not null, foreign key (username) references users (username), unique(username, authoririty));
select * from users;
select * from authoririties;


SHOW DATABASES;
USE `PP_3_1_4_spring-bootstrap`;
SHOW TABLES FROM `PP_3_1_4_spring-bootstrap`;
SELECT * FROM users;
SELECT * FROM t_role;
SELECT * FROM users_roles;


-- ----------------------------------------------------------------------------------------------------
-- ЗАПОЛНИТЬ ВРУЧНУЮ ТАБЛИЦУ С ПОЛЬЗОВАТЕЛЯМИ --
USE `PP_3_1_4_spring-bootstrap`;

-- Добавление ролей
INSERT INTO t_role (role_id, role_name) VALUES (1, 'ROLE_USER');
INSERT INTO t_role (role_id, role_name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO t_role (role_id, role_name) VALUES (3, 'ROLE_SUPERADMIN');

-- Добавление пользователей
INSERT INTO users (user_id, address, date_birth, fullName, password, username) 
VALUES (1, 'User Address', '1990-01-01', 'User Name', '$2a$10$Iom7deSLgxAxykvuANH2s.KpMy5xWbjgQmcsuiycdJt0UMoQflKaC', 'user'); -- user // u

INSERT INTO users (user_id, address, date_birth, fullName, password, username) 
VALUES (2, 'Admin Address', '1985-01-01', 'Admin Name', '$2a$10$dWJyopJqWj/PDxEozd6MzOzTwV.5c2GNoU6hUiou0YOF2CHkfDoZK', 'admin'); -- admin // a

INSERT INTO users (user_id, address, date_birth, full_name, password, username) 
VALUES (3, 'SUPER-Admin Address', '1980-03-03', 'SUPER-Admin Name', '$2a$10$BpTDSlU4gBqocZFn7/t7BuqoFww6wBaqMO5Tot7bvzarZDJJ9xwM2', 'superadmin'); -- superadmin // s

-- Связывание пользователей с ролями
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);  -- Связываем пользователя user с ролью USER
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);  -- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);  -- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1);  -- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2);  -- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (3, 3);  -- Связываем пользователя admin с ролью ADMIN
-- ----------------------------------------------------------------------------------------------------



drop table if exists users;
drop table if exists t_role;
drop table if exists users_roles;


