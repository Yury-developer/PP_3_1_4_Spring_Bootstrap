-- ----------------------------------------------------------------
--      Перед запуском необходимо выполнить данный запрос        --
--  СКРИПТ СОЗДАЕТ САМУ  БАЗУ ДАННЫХ, В КОТОРОЙ БУДЕМ РАБОТАТЬ   --
-- ----------------------------------------------------------------
--        Before starting, you need to execute this request      --
-- THE SCRIPT CREATES THE DATABASE ITSELF, IN WHICH WE WILL WORK --
-- ----------------------------------------------------------------

SHOW DATABASES;
DROP DATABASE IF EXISTS`PP_3_1_4_spring-bootstrap`;
CREATE SCHEMA IF NOT EXISTS `PP_3_1_4_spring-bootstrap` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `PP_3_1_4_spring-bootstrap`;
