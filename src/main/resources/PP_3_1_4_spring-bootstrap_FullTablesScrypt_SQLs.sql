-- -----------------------------------------------------------------------
-- СКРИПТ СОЗДАЕТ ВСЕ ТАБЛИЦЫ И 3 ПОЛЬЗОВАТЕЛЯ С ТРЕМЯ ОСНОВНЫМИ РОЛЯМИ --
-- -----------------------------------------------------------------------

SHOW DATABASES;
DROP DATABASE IF EXISTS`PP_3_1_4_spring-bootstrap`;
CREATE SCHEMA IF NOT EXISTS `PP_3_1_4_spring-bootstrap` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `PP_3_1_4_spring-bootstrap`;

-- ----------------------------------------------------------------------------------------------------
-- СОЗДАДИМ ТАБЛИЦЫ --
-- Создание таблицы ролей
CREATE TABLE t_role (
    role_id BIGINT NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(255),
    PRIMARY KEY (role_id)
) ENGINE=InnoDB;

-- Создание таблицы пользователей
CREATE TABLE users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(128) NOT NULL,
    address VARCHAR(255),
    full_name VARCHAR(50) NOT NULL,
    date_birth DATE,
	email VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id)
) ENGINE=InnoDB;

-- Создание таблицы связывания пользователей и ролей
CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK_role FOREIGN KEY (role_id) REFERENCES t_role (role_id)
) ENGINE=InnoDB;


-- ----------------------------------------------------------------------------------------------------
-- ЗАПОЛНИТЬ ТАБЛИЦЫ --
-- Добавление ролей
INSERT INTO t_role (role_id, role_name) VALUES (1, 'ROLE_USER');
INSERT INTO t_role (role_id, role_name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO t_role (role_id, role_name) VALUES (3, 'ROLE_SUPERADMIN');

-- Добавление пользователей
INSERT INTO users (user_id, username, password, full_name, date_birth, address, email) 
VALUES (1, 'user', '$2a$10$Iom7deSLgxAxykvuANH2s.KpMy5xWbjgQmcsuiycdJt0UMoQflKaC', 'Name is User', '2000-01-01', 'User Address', 'user@mail.ru'); -- user // u

INSERT INTO users (user_id, username, password, full_name, date_birth, address, email) 
VALUES (2, 'admin', '$2a$10$dWJyopJqWj/PDxEozd6MzOzTwV.5c2GNoU6hUiou0YOF2CHkfDoZK', 'Name is Admin', '1990-02-02', 'Admin Address', 'admin@ya.ru'); -- admin // a

INSERT INTO users (user_id, username, password, full_name, date_birth, address, email) 
VALUES (3, 'superadmin', '$2a$10$BpTDSlU4gBqocZFn7/t7BuqoFww6wBaqMO5Tot7bvzarZDJJ9xwM2', 'Name is SUPER-Admin', '1980-03-03', 'SUPER-Admin Address', 'superAdmin@gmail.com'); -- superadmin // s

-- Связывание пользователей с ролями
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);  -- Связываем пользователя user с ролью USER
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);  -- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);  -- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1);  -- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2);  -- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (3, 3);  -- Связываем пользователя admin с ролью ADMIN

-- ----------------------------------------------------------------------------------------------------
-- Выведем то, что получилось
SHOW TABLES FROM `PP_3_1_4_spring-bootstrap`;
SELECT * FROM users;
SELECT * FROM t_role;
SELECT * FROM users_roles;