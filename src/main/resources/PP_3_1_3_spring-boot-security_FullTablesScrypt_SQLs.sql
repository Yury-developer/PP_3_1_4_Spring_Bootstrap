SHOW DATABASES;
DROP DATABASE IF EXISTS`PP_3_1_3_spring-boot-security`;
CREATE SCHEMA IF NOT EXISTS `PP_3_1_3_spring-boot-security` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `PP_3_1_3_spring-boot-security`;

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
    address VARCHAR(255),
    date_birth DATE,
    full_name VARCHAR(50) NOT NULL,
    password VARCHAR(128) NOT NULL,
    username VARCHAR(50) NOT NULL,
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
INSERT INTO t_role (role_id, role_name) VALUES (1, 'USER');
INSERT INTO t_role (role_id, role_name) VALUES (2, 'ADMIN');

-- Добавление пользователей
INSERT INTO users (user_id, address, date_birth, full_name, password, username) 
VALUES (1, 'User Address', '1990-01-01', 'User Name', '$2a$10$Iom7deSLgxAxykvuANH2s.KpMy5xWbjgQmcsuiycdJt0UMoQflKaC', 'user');

INSERT INTO users (user_id, address, date_birth, full_name, password, username) 
VALUES (2, 'Admin Address', '1985-01-01', 'Admin Name', '$2a$10$dWJyopJqWj/PDxEozd6MzOzTwV.5c2GNoU6hUiou0YOF2CHkfDoZK', 'admin');

-- Связывание пользователей с ролями
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);  -- Связываем пользователя user с ролью USER
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);  -- Связываем пользователя admin с ролью ADMIN
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);  -- Связываем пользователя admin с ролью ADMIN

-- ----------------------------------------------------------------------------------------------------
-- Выведем то, что получилось
SHOW TABLES FROM `PP_3_1_3_spring-boot-security`;
SELECT * FROM users;
SELECT * FROM t_role;
SELECT * FROM users_roles;