-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Час створення: Лют 19 2014 р., 09:36
-- Версія сервера: 5.5.34-MariaDB
-- Версія PHP: 5.5.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База даних: `quicktour`
--

-- --------------------------------------------------------

--
-- Структура таблиці `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `comment_id`      INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `users_id`        INT(11)          NOT NULL,
  `tour_id`         INT(11)          NOT NULL,
  `next_comment_id` INT(10) UNSIGNED DEFAULT NULL,
  `content`         VARCHAR(2000) DEFAULT NULL,
  `comment_date`    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  KEY `id` (`comment_id`),
  KEY `next_comment_id` (`next_comment_id`),
  KEY `comments_ibfk_1` (`users_id`),
  KEY `tour_id` (`tour_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =14;

--
-- Дамп даних таблиці `comments`
--

INSERT INTO `comments` (`comment_id`, `users_id`, `tour_id`, `next_comment_id`, `content`, `comment_date`) VALUES
  (2, 3, 1, NULL,
   'Sed pretium, nibh vel dictum rutrum, lectus lorem porttitor lectus, et porttitor dui nunc sed ipsum. Nam a dolor in dui gravida ornare et nec sem. Vivamus eget enim ornare, pharetra magna ac, cursus metus. In quis commodo libero, sit amet accumsan risus. Etiam laoreet felis eu mi malesuada auctor. Donec et ultricies elit. Mauris et tortor vitae urna ornare pharetra vitae vel sem. Maecenas tristique quam sed tempor ultrices. Donec ut leo odio. Integer venenatis venenatis odio, eu pharetra orci consequat pretium. Sed lacinia rutrum arcu, non condimentum neque condimentum et.',
   '2014-01-27 12:12:06'),
  (4, 3, 2, NULL, 'In hac habitasse platea dictumst. Curabitur rutrum justo eget mauris iaculis, at adipiscing massa feugiat. Sed rhoncus lacinia nisi. In at lobortis purus. Maecenas metus dui, aliquet eget semper ac, sagittis tincidunt elit. Fusce ante nibh, ultricies dignissim pellentesque eu, tincidunt consequat erat. Ut varius diam in erat consectetur ultricies. Curabitur vel nunc non lectus posuere aliquam et et quam. Nam tincidunt interdum tellus sit amet convallis. Sed velit ipsum, pulvinar a viverra vel, vestibulum at nisl. Nunc elementum tempor euismod. Phasellus porttitor nisl ut enim malesuada, consequat sodales erat faucibus. Vivamus dignissim pellentesque tincidunt. Morbi dapibus velit sit amet vulputate consequat. Etiam pharetra euismod eros, et sagittis turpis sagittis id.', '2014-01-27 12:12:06'),
  (5, 7, 1, NULL, '<p><img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/angry.gif"/></p>', '2014-01-30 17:29:18'),
  (6, 5, 15, NULL, '<p>fuck you bitches!<img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/angel.gif"/></p>', '2014-02-11 09:27:45'),
  (7, 5, 15, NULL, '<p>fuck you bitches!<img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/angel.gif"/></p>', '2014-02-11 09:27:46'),
  (8, 5, 15, NULL, '<p>fuck you bitches!<img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/angel.gif"/></p>', '2014-02-11 09:27:47'),
  (9, 5, 15, NULL, '<p>fuck you bitches!<img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/angel.gif"/></p>', '2014-02-11 09:27:55'),
  (10, 5, 15, NULL, '<p>fuck you bitches!<img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/angel.gif"/></p>', '2014-02-11 09:27:56'),
  (11, 5, 15, NULL, '<p>fuck you bitches!<img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/angel.gif"/></p>', '2014-02-11 09:28:09'),
  (12, 5, 15, NULL, '<p>fuck you bitches!<img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/angel.gif"/></p>', '2014-02-11 09:28:41'),
  (13, 5, 1, NULL, '<img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/heart.gif"/>', '2014-02-16 22:59:17');

-- --------------------------------------------------------

--
-- Структура таблиці `companies`
--

CREATE TABLE IF NOT EXISTS `companies` (
  `company_id`    INT(11)     NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(45) NOT NULL,
  `information`   LONGTEXT,
  `address`       VARCHAR(256) DEFAULT NULL,
  `contact_phone` VARCHAR(12) NOT NULL,
  `contact_email` VARCHAR(20) NOT NULL,
  `type`          VARCHAR(30) DEFAULT NULL,
  `discount`      INT(11) DEFAULT NULL,
  `license`       VARCHAR(20) NOT NULL,
  `company_code`  VARCHAR(20) DEFAULT NULL,
  `photos_id`     INT(11) DEFAULT NULL,
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `id_UNIQUE` (`company_id`),
  UNIQUE KEY `name` (`name`),
  KEY `fk_Companies_Photos1_idx` (`photos_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =3;

--
-- Дамп даних таблиці `companies`
--

INSERT INTO `companies` (`company_id`, `name`, `information`, `address`, `contact_phone`, `contact_email`, `type`, `discount`, `license`, `company_code`, `photos_id`)
VALUES
  (1, 'Default Company',
   'Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec',
   'Ivano-Frankivsk', '0123456789', 'default@comp.com', 'Tour Agency', 10, 'company license', '12345678987654321', 5),
  (2, 'Default agency',
   'Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec',
   'Ivano-Frankivsk', '0123456798', 'default@agent.com', 'agency', 0, 'company license', '12345678985554321', 5);

-- --------------------------------------------------------

--
-- Структура таблиці `discount_dependency`
--

CREATE TABLE IF NOT EXISTS `discount_dependency` (
  `discount_dependency_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tag`                    VARCHAR(45) DEFAULT NULL,
  `table_field`            VARCHAR(45) DEFAULT NULL,
  `description`            TEXT,
  PRIMARY KEY (`discount_dependency_id`),
  UNIQUE KEY `tag` (`tag`, `table_field`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =6;

--
-- Дамп даних таблиці `discount_dependency`
--

INSERT INTO `discount_dependency` (`discount_dependency_id`, `tag`, `table_field`, `description`) VALUES
  (2, 'age', 'users.age', 'User''s age'),
  (5, 'adult', 'orders.number_of_adults', 'Number of adults in order');

-- --------------------------------------------------------

--
-- Структура таблиці `discount_policy`
--

CREATE TABLE IF NOT EXISTS `discount_policy` (
  `discount_policy_id` INT(11)     NOT NULL AUTO_INCREMENT,
  `name`               VARCHAR(50) NOT NULL,
  `description`        TEXT        NOT NULL,
  `cond`               TEXT,
  `formula`            VARCHAR(50) NOT NULL,
  `start_date`         DATE DEFAULT NULL,
  `end_date`           DATE DEFAULT NULL,
  `companies_id`       INT(11)     NOT NULL,
  PRIMARY KEY (`discount_policy_id`),
  KEY `fk_discount_policy_Companies1_idx` (`companies_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =8;

--
-- Дамп даних таблиці `discount_policy`
--

INSERT INTO `discount_policy` (`discount_policy_id`, `name`, `description`, `cond`, `formula`, `start_date`, `end_date`, `companies_id`)
VALUES
  (4, 'Grown up discount', 'Discount for grown ups!', 'users.age>''20''', '3', '2014-02-12', '2014-02-17', 2),
  (5, 'Free discount', '<p>Discount for everyone!!</p>\r\n', NULL, '5', NULL, NULL, 2),
  (6, 'Discount for big company', '', 'orders.numberOfAdults>''3''', 'orders.numberOfAdults/20', NULL, NULL, 2),
  (7, 'Discount for big family', '', 'orders.numberOfChildren>''2''', '10', NULL, NULL, 2);

-- --------------------------------------------------------

--
-- Структура таблиці `excursions`
--

CREATE TABLE IF NOT EXISTS `excursions` (
  `excursion_id` INT(11) NOT NULL AUTO_INCREMENT,
  `places_id`    INT(11) NOT NULL,
  `name`         VARCHAR(45) DEFAULT NULL,
  `optional`     TINYINT(1) DEFAULT NULL,
  `price`        SMALLINT(6) DEFAULT NULL,
  PRIMARY KEY (`excursion_id`),
  KEY `fk_Excursions_Places1_idx` (`places_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =19;

--
-- Дамп даних таблиці `excursions`
--

INSERT INTO `excursions` (`excursion_id`, `places_id`, `name`, `optional`, `price`) VALUES
  (1, 1, 'Excursion on old town', 0, 50),
  (2, 1, 'Excursion on theatres', 1, 20),
  (3, 1, 'Excursion3', 0, 60),
  (4, 2, 'Excursion1', 1, 30),
  (5, 2, 'Excursion2', 0, 50),
  (6, 3, 'Excursion1', 0, 50),
  (7, 3, 'Excursion2', 0, 50),
  (8, 3, 'Excursion3', 1, 150),
  (9, 4, 'Excursion1', 0, 50),
  (10, 4, 'Excursion2', 1, 50),
  (11, 5, 'Excursion1', 0, 50),
  (12, 5, 'Excursion2', 1, 50),
  (13, 5, 'Excursion3', 1, 50),
  (14, 6, 'Excursion1', 0, 50),
  (15, 6, 'Excursion2', 1, 50),
  (16, 6, 'Excursion3', 1, 50),
  (17, 6, 'Excursion4', 0, 50),
  (18, 7, 'Excursion1', 0, 50);

-- --------------------------------------------------------

--
-- Структура таблиці `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `id`         BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(50)
               COLLATE utf8_bin    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COLLATE =utf8_bin
  AUTO_INCREMENT =1;

-- --------------------------------------------------------

--
-- Структура таблиці `group_authorities`
--

CREATE TABLE IF NOT EXISTS `group_authorities` (
  `group_id`  BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `authority` VARCHAR(50)
              COLLATE utf8_bin    NOT NULL,
  KEY `fk_group_authorities_group` (`group_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COLLATE =utf8_bin
  AUTO_INCREMENT =1;

-- --------------------------------------------------------

--
-- Структура таблиці `group_members`
--

CREATE TABLE IF NOT EXISTS `group_members` (
  `id`       BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50)
             COLLATE utf8_bin    NOT NULL,
  `group_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_members_group` (`group_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COLLATE =utf8_bin
  AUTO_INCREMENT =1;

-- --------------------------------------------------------

--
-- Структура таблиці `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `order_id`             INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_date`           DATETIME DEFAULT NULL,
  `number_of_adults`     INT(11) DEFAULT NULL,
  `number_of_children`   INT(11) DEFAULT NULL,
  `user_info`            TEXT,
  `price`                DECIMAL(10, 2) DEFAULT NULL,
  `discount`             DECIMAL(5, 2) DEFAULT NULL,
  `next_payment_date`    DATE DEFAULT NULL,
  `status`               VARCHAR(45) DEFAULT NULL,
  `tour_info_id`         INT(11)          NOT NULL,
  `companies_id`         INT(11)          NOT NULL,
  `users_id`             INT(11) DEFAULT NULL,
  `accepted_date`        DATETIME DEFAULT NULL,
  `confirmed_date`       DATETIME DEFAULT NULL,
  `completed_date`       DATETIME DEFAULT NULL,
  `cancelled_date`       DATETIME DEFAULT NULL,
  `vote`                 INT(10) UNSIGNED DEFAULT NULL,
  `discount_information` TEXT             NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `id_UNIQUE` (`order_id`),
  KEY `fk_Orders_Tour1_idx` (`tour_info_id`),
  KEY `fk_Orders_Companies1_idx` (`companies_id`),
  KEY `fk_Orders_Users1_idx` (`users_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =50;

--
-- Дамп даних таблиці `orders`
--

INSERT INTO `orders` (`order_id`, `order_date`, `number_of_adults`, `number_of_children`, `user_info`, `price`, `discount`, `next_payment_date`, `status`, `tour_info_id`, `companies_id`, `users_id`, `accepted_date`, `confirmed_date`, `completed_date`, `cancelled_date`, `vote`, `discount_information`)
VALUES
  (4, '2013-12-10 00:00:00', 1, 2, 'User info 1334', '2000.00', '10.00', '2013-12-15', 'RECEIVED', 4, 2, 8, NULL, NULL,
   NULL, NULL, 0, ''),
(7, '2013-12-18 00:00:00', 2, 1, 'User info 888', '800.00', '10.00', '2013-12-21', 'CONFIRMED', 1, 1, 7, NULL, NULL, NULL, NULL, 0, ''),
(9, '2013-12-18 00:00:00', 2, 2, 'User info 3255', '1200.00', '29.00', '2013-12-20', 'COMPLETED', 5, 2, 7, NULL, NULL, '2014-02-08 11:39:31', NULL, 0, ''),
(10, '2013-12-29 15:05:06', 5, 3, 'User info 1', '2500.00', '15.00', '2014-01-05', 'COMPLETED', 15, 1, 8, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 5, ''),
(11, '2013-12-30 12:08:03', 2, 0, 'User info 2', '1500.00', '10.00', '2014-01-18', 'CONFIRMED', 19, 2, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', NULL, NULL, 0, ''),
(12, '2013-12-25 10:00:20', 3, 2, 'User info 3', '2000.00', '5.00', '2013-12-27', 'CANCELLED', 10, 2, 9, NULL, NULL, NULL, '2013-12-28 00:00:00', 0, ''),
(15, '2013-12-10 15:05:06', 5, 3, 'User info 6', '2500.00', '15.00', '2013-12-15', 'COMPLETED', 5, 1, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 4, ''),
(16, '2013-12-29 15:05:06', 5, 3, 'User info 7', '2500.00', '15.00', '2014-01-05', 'COMPLETED', 16, 2, 9, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 5, ''),
(19, '2013-12-19 15:05:06', 4, 3, 'User info 10', '2400.00', '15.00', '2014-01-02', 'COMPLETED', 2, 1, 8, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 5, ''),
(20, '2013-12-20 12:08:03', 1, 2, 'User info 11', '1200.00', '10.00', '2014-01-08', 'CONFIRMED', 3, 2, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', NULL, NULL, 0, ''),
(21, '2013-12-15 10:00:20', 2, 2, 'User info 12', '1800.00', '5.00', '2013-12-17', 'CANCELLED', 4, 2, 9, NULL, NULL, NULL, '2013-12-18 00:00:00', 0, ''),
(22, '2013-12-20 08:01:15', 3, 2, 'User info 13', '1100.00', '12.00', NULL, 'RECEIVED', 5, 1, 9, NULL, NULL, NULL, NULL, 0, ''),
(24, '2013-12-05 15:05:06', 2, 3, 'User info 15', '2400.00', '15.00', '2013-12-05', 'COMPLETED', 7, 1, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 2, ''),
(25, '2013-12-19 15:05:06', 3, 3, 'User info 16', '2500.00', '15.00', '2014-01-02', 'COMPLETED', 8, 2, 9, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 1, ''),
(26, '2013-12-20 18:11:25', 1, 0, 'User info 17', '2600.00', '5.00', NULL, 'RECEIVED', 5, 1, 9, NULL, NULL, NULL, NULL, 0, ''),
(28, '2013-12-30 15:05:06', 6, 3, 'User info 19', '2200.00', '15.00', '2014-01-05', 'COMPLETED', 11, 1, 8, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 5, ''),
(29, '2013-12-31 12:08:03', 3, 2, 'User info 20', '1100.00', '10.00', '2014-01-18', 'CONFIRMED', 12, 2, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', NULL, NULL, 0, ''),
(30, '2013-12-26 10:00:20', 4, 3, 'User info 21', '1800.00', '5.00', '2013-12-27', 'CANCELLED', 13, 2, 9, NULL, NULL, NULL, '2013-12-29 00:00:00', 0, ''),
(33, '2013-12-11 15:05:06', 4, 3, 'User info 24', '2100.00', '15.00', '2013-12-15', 'COMPLETED', 16, 1, 7, '2013-12-28 00:00:00', '2014-01-05 00:00:00', '2014-01-09 00:00:00', NULL, 3, ''),
(34, '2013-12-30 15:05:06', 2, 4, 'User info 25', '2200.00', '15.00', '2014-01-05', 'COMPLETED', 17, 2, 9, '2013-12-31 00:00:00', '2014-01-06 00:00:00', '2014-01-08 00:00:00', NULL, 5, ''),
(37, '2013-12-28 15:05:06', 2, 3, 'User info 28', '2100.00', '15.00', '2014-01-05', 'COMPLETED', 5, 1, 8, '2013-12-29 00:00:00', '2014-01-02 00:00:00', '2014-01-06 00:00:00', NULL, 5, ''),
(38, '2013-12-29 12:08:03', 2, 0, 'User info 29', '1600.00', '10.00', '2014-01-18', 'CONFIRMED', 9, 2, 7, '2013-12-29 00:00:00', '2014-01-03 00:00:00', NULL, NULL, 0, ''),
(39, '2013-12-24 10:00:20', 3, 2, 'User info 30', '2050.00', '5.00', '2013-12-27', 'CANCELLED', 12, 2, 9, NULL, NULL, NULL, '2013-12-30 00:00:00', 0, ''),
(40, '2013-12-29 08:01:15', 4, 2, 'User info 31', '1100.00', '12.00', NULL, 'RECEIVED', 5, 1, 8, NULL, NULL, NULL, NULL, 0, ''),
(42, '2013-12-09 15:05:06', 5, 3, 'User info 33', '2500.00', '15.00', '2013-12-15', 'COMPLETED', 5, 1, 7, '2013-12-28 00:00:00', '2014-01-03 00:00:00', '2014-01-06 00:00:00', NULL, 2, ''),
(43, '2013-12-28 15:05:06', 5, 3, 'User info 34', '2600.00', '15.00', '2014-01-05', 'COMPLETED', 6, 2, 9, '2013-12-29 00:00:00', '2014-01-04 00:00:00', '2014-01-08 00:00:00', NULL, 5, ''),
(44, '2013-12-29 18:11:25', 4, 2, 'User info 35', '2250.00', '5.00', NULL, 'RECEIVED', 5, 1, 7, NULL, NULL, NULL, NULL, 0, ''),
(45, '2014-02-15 20:35:39', 1, 0, 'User_info', '10000.00', '15.00', NULL, 'RECEIVED', 1, 2, 7, NULL, NULL, NULL, NULL, 0, 'tour discount=15<br>Company discount=10<br>'),
(46, '2014-02-15 20:41:01', 1, 0, 'User_info', '10000.00', '15.00', NULL, 'RECEIVED', 1, 2, 7, NULL, NULL, NULL, NULL, 0, 'tour discount=15<br>Company discount=10<br>'),
(47, '2014-02-18 20:37:31', 20, 7, 'Email:babkamen@gmail.com<br>Gazda Ivanovych<br>+380(98)920-90-98<br>We are union!', '300000.00', '5.00', NULL, 'RECEIVED', 17, 2, NULL, NULL, NULL, NULL, NULL, 0, 'tour discount=5<br>'),
(48, '2014-02-18 20:38:28', 20, 7, 'Email:babkamen@gmail.com<br>Gazda Ivanovych<br>+380(98)920-90-98<br>We are union!', '300000.00', '5.00', NULL, 'RECEIVED', 17, 2, NULL, NULL, NULL, NULL, NULL, 0, 'tour discount=5<br>'),
  (49, '2014-02-18 20:38:53', 20, 7,
   'Email:babkamen@gmail.com<br>Gazda Ivanovych<br>+380(98)920-90-98<br>We are union!', '300000.00', '5.00', NULL,
   'RECEIVED', 17, 2, NULL, NULL, NULL, NULL, NULL, 0, 'tour discount=5<br>');

-- --------------------------------------------------------

--
-- Структура таблиці `photos`
--

CREATE TABLE IF NOT EXISTS `photos` (
  `photo_id` INT(11) NOT NULL AUTO_INCREMENT,
  `url`      VARCHAR(256) DEFAULT NULL,
  PRIMARY KEY (`photo_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =63;

--
-- Дамп даних таблиці `photos`
--

INSERT INTO `photos` (`photo_id`, `url`) VALUES
  (1, 'http://buddies.koinup.com/group-637.png'),
  (2, 'http://infinitelives.net/avatars/flashjen.jpg'),
  (3, 'http://www.templatesbox.com/data/premium.templates/images/logo/full.preview/11897522410FI.jpg'),
  (4, 'defaultAvatar.jpg'),
  (5, 'defaultLogo.jpg'),
  (16, 'http://blogs.telegraph.co.uk/news/files/2012/07/europe_2114838b.jpg'),
  (17, 'http://www.flickr.com/photos/115679176@N05/12273858965/'),
  (18, 'http://www.flickr.com/photos/115679176@N05/12274474636/'),
  (19, 'http://farm6.static.flickr.com/5483/12274496786_eea23fd76b.jpg'),
  (20, 'http://farm3.static.flickr.com/2887/12275570864_62dafd7712.jpg'),
  (21, 'http://farm4.static.flickr.com/3739/12275626414_b62a81b5c0.jpg'),
  (22, 'http://farm6.static.flickr.com/5477/12275846006_b17a123c86.jpg'),
  (23, 'http://farm8.static.flickr.com/7445/12275779444_250be177b2.jpg'),
  (24, 'http://farm8.static.flickr.com/7315/12275873994_664a220b1b.jpg'),
  (25, 'http://farm4.static.flickr.com/3713/12275648973_3e4336e02e.jpg'),
  (26, 'http://farm4.static.flickr.com/3741/12394111523_c6e213e8f9.jpg'),
  (27, 'http://farm4.static.flickr.com/3692/12394042015_95db995f0b.jpg'),
  (28, 'http://farm4.static.flickr.com/3810/12394307813_fde345e736.jpg'),
  (29, 'http://farm8.static.flickr.com/7346/12396144423_bdcd05557b.jpg'),
  (30, 'http://farm3.static.flickr.com/2853/12405914073_323299cdac.jpg'),
  (31, 'http://farm4.static.flickr.com/3691/12405767255_b33c446da7.jpg'),
  (32, 'http://farm8.static.flickr.com/7341/12413420584_ff6176cc2e.jpg'),
  (33, 'http://farm3.static.flickr.com/2808/12413159673_9a69310d08.jpg'),
  (34, 'http://farm6.static.flickr.com/5546/12413204773_e812c69acf.jpg'),
  (35, 'http://farm3.static.flickr.com/2810/12413567694_7310606f75.jpg'),
  (36, 'http://farm8.static.flickr.com/7347/12539274763_8937e0356d.jpg'),
  (37, 'http://farm4.static.flickr.com/3719/12567298433_d8f478e462.jpg'),
  (38, 'http://farm6.static.flickr.com/5526/12567540613_96b90a7fa8.jpg'),
  (39, 'http://farm8.static.flickr.com/7311/12567510325_d6865e23ca.jpg'),
  (40, 'http://farm8.static.flickr.com/7376/12568043084_abd70545e2.jpg'),
  (41, 'http://farm8.static.flickr.com/7291/12571082415_86ed42ee76.jpg'),
  (42, 'http://farm6.static.flickr.com/5476/12575250224_9058d700a8.jpg'),
  (43, 'http://farm4.static.flickr.com/3670/12574867145_77ca2df84d.jpg'),
  (44, 'http://farm8.static.flickr.com/7437/12575017313_aaf93ab559.jpg'),
  (45, 'http://farm8.static.flickr.com/7457/12575118753_f1392c9ab5.jpg'),
  (46, 'http://farm6.static.flickr.com/5500/12575278553_6017250291.jpg'),
  (47, 'http://farm4.static.flickr.com/3788/12575372933_962686ffeb.jpg'),
  (48, 'http://farm3.static.flickr.com/2831/12575742754_33126ced8a.jpg'),
  (49, 'http://farm8.static.flickr.com/7389/12575763094_433928837e.jpg'),
  (50, 'http://farm3.static.flickr.com/2831/12575341795_e792b21b50.jpg'),
  (51, 'http://farm4.static.flickr.com/3675/12575375765_9aa9de8bea.jpg'),
  (52, 'http://farm8.static.flickr.com/7314/12575859324_8701b04492.jpg'),
  (53, 'http://farm8.static.flickr.com/7443/12575884624_0b4e1c48b4.jpg'),
  (54, 'http://farm8.static.flickr.com/7429/12575560683_e5c1518377.jpg'),
  (55, 'http://farm8.static.flickr.com/7314/12575595313_e75e000541.jpg'),
  (56, 'http://farm4.static.flickr.com/3674/12576054024_a35e59c21e.jpg'),
  (57, 'http://farm6.static.flickr.com/5522/12576137794_08d6b5f780.jpg'),
  (58, 'http://farm8.static.flickr.com/7421/12575901595_1ef5cbb6d9.jpg'),
  (59, 'http://farm8.static.flickr.com/7372/12575948445_803fbb3df9.jpg'),
  (60, 'http://farm6.static.flickr.com/5523/12592556763_bddd20f2ab.jpg'),
  (61, 'http://farm4.static.flickr.com/3774/12593404105_cec7f6cf75.jpg'),
  (62, 'http://farm8.static.flickr.com/7383/12593483335_be9a687c40.jpg');

-- --------------------------------------------------------

--
-- Структура таблиці `places`
--

CREATE TABLE IF NOT EXISTS `places` (
  `place_id`    INT(11)    NOT NULL AUTO_INCREMENT,
  `country`     VARCHAR(45) DEFAULT NULL,
  `name`        VARCHAR(128) DEFAULT NULL,
  `description` VARCHAR(4096) DEFAULT NULL,
  `optional`    TINYINT(1) NOT NULL DEFAULT '1',
  `price`       SMALLINT(6) DEFAULT NULL,
  `geoheight`   DOUBLE DEFAULT NULL,
  `geowidth`    DOUBLE DEFAULT NULL,
  PRIMARY KEY (`place_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =43;

--
-- Дамп даних таблиці `places`
--

INSERT INTO `places` (`place_id`, `country`, `name`, `description`, `optional`, `price`, `geoheight`, `geowidth`) VALUES
  (1, 'Greece', 'Athines',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.',
   1, 800, 37.966667, 23.716667),
  (2, 'Greece', 'Mediteranian Sea', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.', 1, 1200, 36.923548, 21.68518),
  (3, 'USA', 'New York,New York', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.', 0, 1100, 40.742055, - 74.013748),
  (4, 'USA', 'Chicago,Chicago', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.', 0, 600, 41.896144, - 87.628555),
  (5, 'USA', 'Michigan Lake,Michigan Lake', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.', 0, 800, 43.032761, - 87.902527),
  (6, 'Poland', 'Krakiv,Krakiv,Krakiv,Krakiv', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.', 0, 800, 50.073888, 19.944191),
  (7, 'Poland', 'Warsaw,Warsaw,Warsaw,Warsaw', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.', 0, 800, 52.237472, 21.009865),
  (8, 'USA', 'Kansas,Kansas', '', 0, 900, 39.124734, - 94.576035),
  (9, 'USA', 'Las Vegas,Las Vegas', '', 0, 1100, 36.11777, - 115.172453),
  (10, 'Ukraine', 'Chornobyl', '', 0, 5000, 51.261926, 30.23604499999999),
  (12, 'Ukraine', 'Chornobyl', '', 0, 5000, 51.261926, 30.23604499999999),
  (13, 'Ukraine', 'Chornobyl', '', 0, 5000, 51.261926, 30.23604499999999),
  (18, 'Poland', 'Wroclaw', 'just text', 0, 5000, 51.1078852, 17.03853760000004),
  (19, 'Ukraine', 'Lviv', 'descr2', 0, 3000, 49.839683, 24.029717000000005),
  (20, 'Thailand', 'Bangkok,Bangkok,Bangkok,Bangkok,Bangkok,Bangkok,Bangkok,Bangkok,Bangkok,Bangkok', '', 0, 5000, 13.7278956, 100.52412349999997),
  (21, 'Japan', 'undefined', '', 0, 2000, 35.6894875, 139.69170639999993),
  (22, 'Germany', 'Munich', '', 0, 1000, 48.1351253, 11.581980599999952),
  (23, 'Germany', 'Stuttgart', '', 0, 500, 48.7754181, 9.181758800000011),
  (24, 'Canada', 'Vancouver', '', 0, 1000, 49.261226, - 123.1139268),
  (25, 'Japan', 'Nagasaki', '', 0, 500, 32.7502856, 129.87766699999997),
  (26, 'Zimbabwe', 'undefined', '', 0, 240, - 19.015438, 29.154856999999993),
  (27, 'United States', 'Yosemite national park', '', 0, 2000, 37.8651011, - 119.53832940000001),
  (28, 'United States', 'California', '', 0, 7000, 36.778261, - 119.41793239999998),
  (29, 'Canada', 'Vancouver,Vancouver', '', 0, 10000, 49.261226, - 123.1139268),
  (30, 'Canada', 'Alberta,Alberta', '', 0, 5000, 53.9332706, - 116.5765035),
  (31, 'Russia', 'Moscow', '', 0, 10000, 55.7520233, 37.61749940000004),
  (32, 'Russia', 'Saint Petersburg', '', 0, 20000, 59.9342802, 30.335098600000038),
  (33, 'Japan', 'Ono,Ono,Ono,Ono,Ono,Ono', '', 0, 2000, 37.299110316712856, 140.5986325442791),
  (34, 'Ukraine', 'Kiev', '', 0, 200, 50.4501, 30.523400000000038),
  (39, 'Poland', 'Kulerz', '', 0, 2000, 49.95416528713043, 19.892944302409887),
  (40, 'Poland', 'Katowice', '', 0, 500, 50.24779032029236, 19.014038052409887),
  (41, 'Poland', 'Stalowa Wola', '', 0, 2000, 50.582771563524595, 21.974853482097387),
  (42, 'Ukraine', 'Lviv', '', 0, 3000, 49.85876255391952, 24.067748989909887);

-- --------------------------------------------------------

--
-- Структура таблиці `price_includes`
--

CREATE TABLE IF NOT EXISTS `price_includes` (
  `price_include_id` INT(11)      NOT NULL AUTO_INCREMENT,
  `description`      VARCHAR(128) NOT NULL,
  PRIMARY KEY (`price_include_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =8;

--
-- Дамп даних таблиці `price_includes`
--

INSERT INTO `price_includes` (`price_include_id`, `description`) VALUES
  (1, 'Feeding'),
  (2, 'Transport'),
  (3, 'Living in hotels'),
  (4, 'Basic excursions'),
  (5, 'Additional hotel options'),
  (6, 'Extended service'),
  (7, 'Additional excursions');

-- --------------------------------------------------------

--
-- Структура таблиці `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `RoleId` INT(11)          NOT NULL,
  `Role`   VARCHAR(45)
           COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`RoleId`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COLLATE =utf8_bin;

-- --------------------------------------------------------

--
-- Структура таблиці `tours`
--

CREATE TABLE IF NOT EXISTS `tours` (
  `tour_id`               INT(11) NOT NULL AUTO_INCREMENT,
  `name`                  VARCHAR(128) DEFAULT NULL,
  `description`           TEXT,
  `transport_description` VARCHAR(512) DEFAULT NULL,
  `photo_id`              INT(11) DEFAULT NULL,
  `active`                TINYINT(1) DEFAULT NULL,
  `price`                 INT(10) DEFAULT NULL,
  `companies_id`          INT(11) DEFAULT NULL,
  `travel_type`           ENUM('driving', 'walking', 'transit', 'bycicling') DEFAULT NULL,
  PRIMARY KEY (`tour_id`),
  UNIQUE KEY `photo_id_2` (`photo_id`),
  KEY `companies_id` (`companies_id`),
  KEY `photo_id` (`photo_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =16;

--
-- Дамп даних таблиці `tours`
--

INSERT INTO `tours` (`tour_id`, `name`, `description`, `transport_description`, `photo_id`, `active`, `price`, `companies_id`, `travel_type`)
VALUES
  (1, 'Tour to Greece',
   '<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quis feugiat dui. Suspendisse fringilla ante nunc, sed bibendum libero tempus sit amet. Nulla a odio auctor, lacinia ante et, dictum turpis. Cras nec rhoncus risus. Aliquam faucibus est id velit ornare sollicitudin. In hac habitasse platea dictumst. In quis lacinia mi. Sed rhoncus vestibulum leo vel molestie.</p> &lt;script&gt;alert(&quot;Hello&quot;) &lt;/script&gt;\n<br /> &nbsp;\n<br /> &nbsp; \n<p>Donec lectus libero, vestibulum at sollicitudin id, commodo sit amet justo. Curabitur nec dui eget mauris ultricies gravida. Sed tincidunt, dolor et egestas dictum, turpis nunc iaculis ante, id pretium eros nulla at nisi. Praesent pretium eleifend dui, ac consectetur risus vulputate sed. Curabitur at neque odio. Nullam semper, dolor eu volutpat condimentum, nulla mauris eleifend quam, eget sodales lorem sem ut arcu. Sed ligula massa, ullamcorper ut tellus sed, venenatis consectetur nunc. Ut nec suscipit orci, nec tincidunt ligula. Pellentesque sit amet leo eu odio iaculis dignissim. Phasellus vel sem malesuada, dapibus libero vel, porttitor tellus. In hac habitasse platea dictumst. Aenean diam sem, semper ut tincidunt sit amet, condimentum ut lacus. Nam ligula neque, dapibus eget ipsum a, sollicitudin gravida purus. Fusce eget dolor sapien.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Duis faucibus laoreet ligula feugiat imperdiet. Donec at leo pulvinar, tincidunt dolor id, facilisis mi. Maecenas sit amet suscipit leo. Etiam pharetra vestibulum dolor eu dignissim. Morbi hendrerit fermentum suscipit. Integer ullamcorper risus justo, ut imperdiet magna egestas vitae. Morbi diam felis, feugiat commodo magna ut, lacinia egestas eros. Phasellus posuere odio a risus mattis condimentum. Nulla tempus porta mauris ut rhoncus. Pellentesque magna tellus, mollis vel augue sit amet, vehicula malesuada odio. Sed interdum condimentum ornare.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Mauris adipiscing iaculis dolor, eget adipiscing nulla imperdiet sit amet. Fusce sodales euismod risus, ut commodo lacus viverra vitae. Curabitur ut quam elementum quam feugiat malesuada nec sed elit. Suspendisse lorem orci, ultrices ut tincidunt quis, varius ut metus. Quisque ut dapibus ipsum, a ultrices urna. Sed interdum nec nisl dictum varius. Morbi eu gravida dolor. Sed sed consectetur eros, aliquam euismod est. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Integer semper justo non mi lacinia, ut elementum ligula porta. Mauris posuere erat quam, non rutrum velit pretium at. Ut scelerisque augue vitae mauris hendrerit viverra. Morbi id pellentesque libero. Sed feugiat congue facilisis. Suspendisse ut nulla eget elit placerat interdum. Donec mattis aliquet augue nec vestibulum. Nam commodo consectetur ipsum in lacinia. Mauris pellentesque massa sed nulla congue auctor. Suspendisse adipiscing mi at lorem dapibus iaculis. Fusce urna nisi, rhoncus id tortor in, adipiscing fringilla quam. Pellentesque scelerisque tellus sed odio hendrerit bibendum. Maecenas id felis non ipsum pharetra posuere. Ut pharetra sollicitudin odio, non ornare turpis. Curabitur blandit dui aliquet mollis semper. Mauris pellentesque neque fringilla risus interdum, ac sollicitudin mauris hendrerit.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Morbi nunc eros, hendrerit vitae libero quis, semper molestie sapien. Etiam quis ullamcorper purus, ultricies accumsan erat. Duis sit amet iaculis eros. Proin eget sapien pretium, cursus elit scelerisque, congue enim. Nulla non ipsum convallis, varius lorem id, blandit felis. Aenean sit amet lectus molestie, bibendum ipsum a, laoreet ligula. Aliquam ac consequat risus.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Vestibulum ipsum ligula, congue vel dapibus quis, fermentum vel massa. Fusce vitae arcu augue. Integer in nulla ut enim fermentum dapibus. In molestie, nisi non imperdiet tincidunt, sem neque elementum mi, vitae suscipit nisi diam sed tortor. Etiam eu aliquam magna. Aliquam eu ipsum eu diam dictum aliquam in a sem. Fusce posuere arcu in lobortis feugiat. Aenean suscipit tristique orci at malesuada. Mauris pellentesque quam vitae vulputate rhoncus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean consequat ante vel imperdiet sagittis. Proin euismod posuere velit, et ultricies enim fringilla ac. Sed feugiat tortor ut euismod tempor.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Morbi in massa in velit vestibulum lacinia. Pellentesque semper est condimentum luctus pulvinar. Aenean aliquam tempor placerat. Morbi elementum enim id erat viverra, eu luctus risus gravida. Nam pharetra tellus sit amet dolor ultricies laoreet id eget leo. Phasellus scelerisque nisl quis tortor commodo interdum. Morbi ac porta erat. Mauris vitae dictum elit, sit amet facilisis quam. Phasellus vel dictum leo, vitae faucibus ante. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent commodo ipsum nec lectus convallis, nec pellentesque erat elementum. Suspendisse rhoncus, neque eget vulputate imperdiet, dolor felis faucibus ipsum, nec rutrum purus turpis vel nibh. Nam at aliquam justo. Integer non fermentum enim.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Morbi eget iaculis mi, sed sagittis sem. Sed lacinia placerat libero quis vestibulum. Pellentesque in dolor commodo, porta odio quis, congue ipsum. Donec quis fringilla lorem, non egestas orci. Aliquam iaculis sollicitudin lacus in pharetra. Pellentesque eros mi, vulputate in sodales eget, fermentum eget ipsum. Morbi elementum velit arcu, quis egestas dui scelerisque vitae. Aenean vel molestie erat, a tincidunt felis. Sed ultricies nunc quis mattis aliquam. Proin hendrerit turpis vel dui bibendum ultrices. Fusce in leo elit. Integer posuere risus eu lectus fermentum, at vestibulum nibh pulvinar.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Proin sit amet tellus vel augue convallis suscipit quis ut purus. Cras ut augue ultricies, pulvinar metus a, molestie dolor. In odio diam, aliquet non semper elementum, gravida ut ligula. Nullam adipiscing, quam nec aliquam interdum, sapien justo congue velit, at ultrices diam quam eu urna. Cras facilisis neque enim, sed convallis eros lacinia eget. Donec sollicitudin quis justo id tempus. Vivamus erat lacus, pellentesque id urna et, accumsan tempor eros. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Aenean nec blandit dolor. Maecenas dolor erat, placerat aliquet erat et, adipiscing porta tellus. Nullam in feugiat ante, non commodo tortor. Donec adipiscing aliquet faucibus. Nullam in nisl dapibus, volutpat risus id, imperdiet elit. Sed a molestie libero. Sed feugiat quam vitae turpis sollicitudin, id lobortis tellus molestie. In odio ante, pretium quis leo non, fringilla aliquam lectus. Integer dapibus eget neque pulvinar tempor. Integer sed dui eleifend, malesuada libero nec, molestie tellus. Nunc et egestas quam. Etiam euismod tellus eget nisl posuere, nec facilisis elit ullamcorper. Aenean ultricies bibendum purus nec consectetur. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas pretium erat vel ligula euismod accumsan.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Suspendisse sed egestas odio. Duis eu aliquet urna. Nunc vitae malesuada mi, et euismod lectus. Donec eu tincidunt sem. Aenean a bibendum nisi. Morbi egestas bibendum facilisis. Nunc faucibus tincidunt nulla sit amet convallis. Integer non metus vel nulla varius luctus at sed quam. Nullam ultricies placerat dolor, sed blandit ante eleifend in. Nunc condimentum odio lorem, non sollicitudin erat cursus non.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Nullam sed scelerisque mauris, et ultricies velit. Fusce pellentesque felis sit amet mauris sagittis sagittis. Cras tincidunt purus at lacus mollis viverra. Pellentesque euismod vitae sapien quis sodales. Donec sollicitudin hendrerit tortor eget lacinia. Maecenas ac est ut dui ornare ultricies non eu sem. Sed euismod augue magna, eu mattis nibh dictum porttitor. Mauris at nulla vel tellus gravida sodales. Mauris porttitor a nibh sit amet rutrum. Ut vel porta elit. Ut in leo sagittis, commodo quam eget, laoreet libero. Maecenas enim lacus, pellentesque in ante ut, ultrices ornare risus. Fusce mi sem, egestas non mauris in, pretium lacinia sem. Duis laoreet dui turpis, non suscipit enim ultricies id. Donec accumsan vitae leo sit amet fermentum. Proin mi nulla, porttitor at enim eu, auctor tempor leo.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Suspendisse adipiscing erat non semper tincidunt. Proin quis dolor purus. Phasellus tincidunt scelerisque turpis, ac elementum felis ornare vitae. Suspendisse iaculis adipiscing erat, non sollicitudin est volutpat eget. Vestibulum et dictum arcu, nec sollicitudin odio. Vestibulum non dictum felis, egestas ornare nunc. Donec in diam tincidunt, adipiscing dolor vitae, luctus velit. Integer purus justo, tincidunt et felis eu, viverra posuere mauris. Proin eget diam eu tellus aliquet sodales. Donec ornare dui eu lacinia ultricies. Ut eget arcu sollicitudin metus tempor laoreet eu non nunc. Sed accumsan sem quis molestie varius. Nulla cursus nibh non iaculis laoreet. Phasellus ligula lectus, pulvinar sit amet purus id, tincidunt auctor odio.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Duis rhoncus est nisi, ac malesuada lacus sagittis dapibus. Vivamus vestibulum, mi ut ultricies feugiat, tellus ligula ultricies mauris, vel tempus sapien odio eu nulla. Cras consectetur rutrum lacus in gravida. Suspendisse quis quam id lectus ornare consequat. Suspendisse nec gravida ante, vitae tempor risus. Fusce tempus tortor tortor, sit amet placerat orci pretium sit amet. Nam lobortis quam ut lorem sodales, et tempus diam elementum.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Nulla facilisi. Morbi dapibus felis malesuada feugiat faucibus. Praesent hendrerit mauris ac erat consectetur pulvinar. Nunc gravida molestie orci eget convallis. Nunc eu nibh mollis, volutpat justo a, molestie ligula. Quisque mattis ante nulla. Vestibulum consequat dolor id lectus tempor, nec vehicula nulla blandit. Morbi imperdiet in mi sed fermentum.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Etiam venenatis porttitor ipsum quis dictum. Etiam sit amet congue orci. Ut odio lorem, dignissim vel feugiat vitae, convallis at dui. Cras augue massa, dictum vulputate gravida a, ultrices volutpat felis. Vestibulum iaculis semper consequat. Pellentesque mattis, eros iaculis tincidunt vehicula, tellus sem vestibulum felis, aliquet blandit diam urna ac lacus. Praesent non sapien vitae ipsum eleifend aliquet quis vel lorem. Proin justo purus, posuere vel luctus et, elementum adipiscing tortor. Duis ut pulvinar felis, mattis vulputate leo. Praesent rhoncus lorem est. Curabitur gravida ut turpis vitae sagittis. Phasellus adipiscing hendrerit tortor nec scelerisque. Pellentesque ac augue et diam facilisis gravida vel eu felis. Nam at odio nec odio pulvinar lacinia ut sed mi.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Sed posuere sit amet nisl non dignissim. Praesent eget lorem non risus mollis ullamcorper vel pulvinar tortor. Mauris pharetra dignissim venenatis. Proin vehicula massa vel odio cursus laoreet. Etiam elementum faucibus nulla, nec tempor ligula dignissim et. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Etiam mollis aliquet sapien at egestas. Cras quis augue id nulla tincidunt iaculis eu id purus. Integer arcu mi, viverra nec nisl eu, rutrum imperdiet nisi. Vivamus tempor neque vitae accumsan euismod. Duis laoreet euismod purus. Fusce et erat tincidunt, tincidunt nisi ut, cursus felis. Sed imperdiet leo in eros fermentum fringilla at sit amet nisi.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Vestibulum dictum pharetra sapien at congue. Donec ac sapien commodo, molestie magna nec, varius nisl. Cras et turpis augue. Cras pretium quis massa sit amet tristique. Nulla mollis lorem in massa adipiscing, sit amet viverra sapien mattis. Ut gravida, urna ac consectetur tempor, magna augue dictum metus, nec volutpat libero urna ac dolor. Aenean tempor quis turpis in vehicula. Quisque semper eu libero non aliquet. Nulla tempus ante dui, suscipit gravida erat ultricies in. Duis ultricies lobortis ligula non venenatis. Pellentesque vitae purus et nibh hendrerit adipiscing. Mauris pellentesque ornare magna, sed pulvinar dui lacinia et. Vestibulum id est et felis tincidunt lobortis.</p> \n<br /> &nbsp;\n<br /> &nbsp; \n<p>Cras fermentum rhoncus ante a eleifend. Donec mi quam, porttitor in fermentum nec, consequat et mi. Phasellus molestie suscipit bibendum. Vivamus ac metus congue, molestie orci id, porttitor turpis. Sed vitae elit nisl. Vivamus sit amet accumsan tellus. Suspendisse est elit, aliquet ac mauris ac, tristique fringilla massa. In fermentum eros quis purus luctus, eget mollis lacus rutrum. Ut malesuada consectetur justo, et posuere ligula convallis vitae. Donec luctus metus ut lectus euismod fermentum. Duis id nulla et nibh fermentum consequat in in urna. Cras ac diam risus. Etiam a sapien vel sem molestie fringilla at nec dui. Pellentesque tempus, nisi vitae suscipit blandit, mauris est elementum metus, condimentum feugiat orci nisl at eros.</p>',
   'Plain', 46, 1, 5000, 2, 'driving'),
(2, 'Tour to USA', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies', 'Plain', 47, 1, 4500, 2, 'driving'),
(3, 'Tour to Poland', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies', 'Bus', 57, 1, 1600, 2, 'bycicling'),
(4, 'Fantastic places of Asia', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 'Plain, Bus', 59, 1, 7000, 2, 'driving'),
(5, 'Germany famous cities', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies', 'Bus, Train', 50, 1, 1500, 2, 'driving'),
(6, 'Europe tour', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, ', 'Bus, Train, Plain', 16, 1, 3400, 2, 'driving'),
(7, 'Tour around the world', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 'All the most popular kinds of transports', 51, 1, 1740, 2, 'driving'),
(8, 'U. S. Yosemite National park', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 'Plain, Car', 52, 1, 9000, 2, 'driving'),
(9, 'Tour to Canada', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 'Plain', 56, 1, 15000, 2, 'driving'),
(10, 'Tour to Russia', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,', 'Plain, Train, Bus', 55, 1, 30000, 2, 'driving'),
(14, 'Tour to Chernobyl', '', 'dead body', 26, 0, NULL, 2, 'driving'),
  (15, 'Tour to Chornobyl', '', 'dead body', 27, 1, 5200, 2, 'driving');

-- --------------------------------------------------------

--
-- Структура таблиці `tours_discount_policy`
--

CREATE TABLE IF NOT EXISTS `tours_discount_policy` (
  `tours_discount_policy_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tours_id`                 INT(11) NOT NULL,
  `discount_policy_id`       INT(11) NOT NULL,
  PRIMARY KEY (`tours_discount_policy_id`),
  KEY `fk_Tours_has_discount_policy_discount_policy1_idx` (`discount_policy_id`),
  KEY `fk_Tours_has_discount_policy_Tours1_idx` (`tours_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =53;

--
-- Дамп даних таблиці `tours_discount_policy`
--

INSERT INTO `tours_discount_policy` (`tours_discount_policy_id`, `tours_id`, `discount_policy_id`) VALUES
  (37, 6, 4),
  (38, 6, 5),
  (39, 6, 6),
  (40, 6, 7),
  (41, 3, 4),
  (42, 3, 5),
  (43, 3, 6),
  (44, 3, 7),
  (45, 4, 4),
  (46, 4, 5),
  (47, 4, 6),
  (48, 4, 7),
  (49, 5, 4),
  (50, 5, 5),
  (51, 5, 6),
  (52, 5, 7);

-- --------------------------------------------------------

--
-- Структура таблиці `tours_places`
--

CREATE TABLE IF NOT EXISTS `tours_places` (
  `tours_place_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tours_id`       INT(11) NOT NULL,
  `places_id`      INT(11) NOT NULL,
  PRIMARY KEY (`tours_place_id`),
  KEY `fk_Tours_has_Places_Places1_idx` (`places_id`),
  KEY `fk_Tours_has_Places_Tours1_idx` (`tours_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =86;

--
-- Дамп даних таблиці `tours_places`
--

INSERT INTO `tours_places` (`tours_place_id`, `tours_id`, `places_id`) VALUES
  (13, 2, 3),
  (14, 2, 4),
  (15, 2, 5),
  (16, 2, 8),
  (17, 2, 9),
  (22, 5, 22),
  (23, 5, 23),
  (24, 7, 24),
  (25, 7, 25),
  (26, 7, 26),
  (27, 8, 27),
  (28, 8, 28),
  (31, 10, 31),
  (32, 10, 32),
  (33, 9, 29),
  (34, 9, 30),
  (35, 3, 6),
  (36, 3, 7),
  (45, 4, 20),
  (46, 4, 33),
  (65, 15, 13),
  (66, 15, 34),
  (84, 1, 41),
  (85, 1, 42);

-- --------------------------------------------------------

--
-- Структура таблиці `tours_price_includes`
--

CREATE TABLE IF NOT EXISTS `tours_price_includes` (
  `tours_price_include_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tours_id`               INT(11) NOT NULL,
  `price_includes_id`      INT(11) NOT NULL,
  PRIMARY KEY (`tours_price_include_id`),
  KEY `fk_Tours_has_PriceIncludes_PriceIncludes1_idx` (`tours_price_include_id`),
  KEY `fk_Tours_has_PriceIncludes_Tours1_idx` (`tours_id`),
  KEY `fk_Tours_has_PriceIncludes_PriceIncludes1` (`price_includes_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =23;

--
-- Дамп даних таблиці `tours_price_includes`
--

INSERT INTO `tours_price_includes` (`tours_price_include_id`, `tours_id`, `price_includes_id`) VALUES
  (20, 1, 2),
  (21, 1, 4),
  (22, 1, 6);

-- --------------------------------------------------------

--
-- Структура таблиці `tour_info`
--

CREATE TABLE IF NOT EXISTS `tour_info` (
  `tour_info_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tours_id`     INT(11) NOT NULL,
  `start_date`   DATE DEFAULT NULL,
  `end_date`     DATE DEFAULT NULL,
  `discount`     INT(3) DEFAULT '0',
  PRIMARY KEY (`tour_info_id`),
  UNIQUE KEY `Tourid_UNIQUE` (`tour_info_id`),
  KEY `fk_Tour_tours_idx` (`tours_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =22;

--
-- Дамп даних таблиці `tour_info`
--

INSERT INTO `tour_info` (`tour_info_id`, `tours_id`, `start_date`, `end_date`, `discount`) VALUES
  (1, 1, '2013-11-10', '2013-11-25', 5),
  (2, 1, '2013-11-15', '2013-11-30', 6),
  (3, 1, '2013-11-20', '2013-12-05', 4),
  (4, 2, '2013-12-10', '2014-01-05', 3),
  (5, 2, '2013-12-20', '2014-01-15', 2),
  (6, 3, '2013-11-15', '2013-11-14', 10),
  (7, 4, '2014-01-05', '2014-01-25', 3),
  (8, 4, '2014-02-05', '2014-02-25', 8),
  (9, 5, '2014-01-05', '2014-01-25', 6),
  (10, 5, '2014-02-05', '2014-02-25', 10),
  (11, 6, '2014-01-10', '2014-01-31', 2),
  (12, 7, '2014-01-01', '2014-01-15', 3),
  (13, 7, '2014-01-25', '2014-02-10', 5),
  (14, 7, '2014-05-10', '2014-05-26', 15),
  (15, 8, '2014-03-15', '2014-03-25', 6),
  (16, 8, '2014-07-15', '2014-07-25', 3),
  (17, 9, '2014-02-10', '2014-02-23', 5),
  (18, 9, '2014-05-15', '2014-05-28', 5),
  (19, 10, '2014-01-30', '2014-02-20', 2),
  (20, 14, '2014-02-13', '2014-02-14', 5),
  (21, 15, '2014-02-13', '2014-02-14', 5);

-- --------------------------------------------------------

--
-- Структура таблиці `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id`      INT(11)      NOT NULL AUTO_INCREMENT,
  `username`     VARCHAR(16)  NOT NULL,
  `password`     VARCHAR(128) NOT NULL,
  `email`        VARCHAR(255) NOT NULL,
  `phone`        VARCHAR(45) DEFAULT NULL,
  `create_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name`         VARCHAR(45) DEFAULT NULL,
  `surname`      VARCHAR(45) DEFAULT NULL,
  `age`          INT(11) DEFAULT NULL,
  `gender`       VARCHAR(45) DEFAULT NULL,
  `company_code` VARCHAR(20) DEFAULT NULL,
  `photos_id`    INT(11) DEFAULT NULL,
  `enabled`      TINYINT(1) DEFAULT NULL,
  `role`         VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `id_UNIQUE` (`user_id`),
  UNIQUE KEY `login` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_Users_Photos1_idx` (`photos_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =10;

--
-- Дамп даних таблиці `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `phone`, `create_time`, `name`, `surname`, `age`, `gender`, `company_code`, `photos_id`, `enabled`, `role`)
VALUES
  (3, 'Zarichnyi', '9b93c8bf2241cf090ecf95a16914d82987d0a0f2bd4a380f5a907558844d896f0c9eaac8e574b288',
   'kopalhem@gmail.com', '0987654', '2014-01-27 12:12:06', 'Andrew', 'Zarichnyi', 20, 'Male', '3244221331', 4, 1,
   'admin'),
  (5, 'agent1', '091fd45e668251e235831fbceaa76630cfeede62c968dca8ea340dd54c3f5990cf5cec733f744a64',
   'agent.one@gmail.com', '0633333333', '2014-01-27 12:12:06', 'agentOneName', 'agentOneSurname', 25, 'Female',
   '12345678985554321', 4, 1, 'agent'),
  (6, 'agent2', '4c59e5b9cb5a491ff5c1fedd7254a345b35af67ff40bbd7b39ec8bde390cc3a7f6c2f7ff9a26dab8',
   'agent.two@gmail.com', '0999999999', '2014-01-27 12:12:06', 'agentTwoName', 'agentTwoSurname', 23, 'Male',
   '12345678987654321', 4, 1, 'agent'),
  (7, 'user1', '55ff08400ab98e506e2ab58b3e62215dd1774776fa2f0dd3d94170456995d9b6a6f93b41823c92b5', 'user.one@gmail.com',
   '0667891234', '2014-02-17 17:57:58', 'Gazda', 'Ivanovych', 27, 'Male', '12345678987654321', 62, 0, 'user'),
  (8, 'user2', '3a4e3ccd5f2937f1e179e9a015a7f26892e997399c33862cfd8e9fd459e4a88f0c73191d9aa4c66e', 'user.two@gmail.com',
   '0687801235', '2014-01-27 12:12:06', 'UserTwoName', 'UserTwoSurname', 21, 'Male', '', 4, 1, 'user'),
  (9, 'user3', '2eaf7abd06b27207ef710b08b996454349f8fa2b49021249118cbb83e0b20a3f43cd0bd2a7a907db',
   'user.three@gmail.com', '0953591554', '2014-02-04 21:31:06', 'UserThreeName', 'UserThreeSurname', 19, 'Male', '', 4,
   1, 'user');

-- --------------------------------------------------------

--
-- Структура таблиці `validation_links`
--

CREATE TABLE IF NOT EXISTS `validation_links` (
  `validation_link_id` INT(11)   NOT NULL AUTO_INCREMENT,
  `user_id`            INT(11)   NOT NULL,
  `url`                VARCHAR(256) DEFAULT NULL,
  `create_time`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`validation_link_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =2;

--
-- Дамп даних таблиці `validation_links`
--

INSERT INTO `validation_links` (`validation_link_id`, `user_id`, `url`, `create_time`) VALUES
  (1, 23, '97f8a755010b9ecd51190064b51b649f', '2014-02-15 13:11:29');

--
-- Обмеження зовнішнього ключа збережених таблиць
--

--
-- Обмеження зовнішнього ключа таблиці `comments`
--
ALTER TABLE `comments`
ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`users_id`) REFERENCES `users` (`user_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`next_comment_id`) REFERENCES `comments` (`comment_id`)
  ON DELETE SET NULL
  ON UPDATE CASCADE,
ADD CONSTRAINT `comments_ibfk_3` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`tour_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `companies`
--
ALTER TABLE `companies`
ADD CONSTRAINT `fk_Companies_Photos1` FOREIGN KEY (`photos_id`) REFERENCES `photos` (`photo_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--
-- Обмеження зовнішнього ключа таблиці `discount_policy`
--
ALTER TABLE `discount_policy`
ADD CONSTRAINT `fk_discount_policy_Companies1` FOREIGN KEY (`companies_id`) REFERENCES `companies` (`company_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--
-- Обмеження зовнішнього ключа таблиці `excursions`
--
ALTER TABLE `excursions`
ADD CONSTRAINT `excursions_ibfk_1` FOREIGN KEY (`places_id`) REFERENCES `places` (`place_id`);

--
-- Обмеження зовнішнього ключа таблиці `group_authorities`
--
ALTER TABLE `group_authorities`
ADD CONSTRAINT `fk_group_authorities_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`);

--
-- Обмеження зовнішнього ключа таблиці `group_members`
--
ALTER TABLE `group_members`
ADD CONSTRAINT `fk_group_members_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`);

--
-- Обмеження зовнішнього ключа таблиці `orders`
--
ALTER TABLE `orders`
ADD CONSTRAINT `orders_ibfk_4` FOREIGN KEY (`tour_info_id`) REFERENCES `tour_info` (`tour_info_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `orders_ibfk_5` FOREIGN KEY (`companies_id`) REFERENCES `companies` (`company_id`),
ADD CONSTRAINT `orders_ibfk_6` FOREIGN KEY (`users_id`) REFERENCES `users` (`user_id`);

--
-- Обмеження зовнішнього ключа таблиці `tours`
--
ALTER TABLE `tours`
ADD CONSTRAINT `tours_ibfk_1` FOREIGN KEY (`companies_id`) REFERENCES `companies` (`company_id`),
ADD CONSTRAINT `tours_ibfk_2` FOREIGN KEY (`photo_id`) REFERENCES `photos` (`photo_id`)
  ON DELETE SET NULL
  ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `tours_discount_policy`
--
ALTER TABLE `tours_discount_policy`
ADD CONSTRAINT `fk_Tours_has_discount_policy_discount_policy1` FOREIGN KEY (`discount_policy_id`) REFERENCES `discount_policy` (`discount_policy_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `fk_Tours_has_discount_policy_Tours1` FOREIGN KEY (`tours_id`) REFERENCES `tours` (`tour_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--
-- Обмеження зовнішнього ключа таблиці `tours_places`
--
ALTER TABLE `tours_places`
ADD CONSTRAINT `fk_Tours_has_Places_Places1` FOREIGN KEY (`places_id`) REFERENCES `places` (`place_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Tours_has_Places_Tours1` FOREIGN KEY (`tours_id`) REFERENCES `tours` (`tour_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--
-- Обмеження зовнішнього ключа таблиці `tours_price_includes`
--
ALTER TABLE `tours_price_includes`
ADD CONSTRAINT `fk_Tours_has_PriceIncludes_PriceIncludes1` FOREIGN KEY (`price_includes_id`) REFERENCES `price_includes` (`price_include_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Tours_has_PriceIncludes_Tours1` FOREIGN KEY (`tours_id`) REFERENCES `tours` (`tour_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--
-- Обмеження зовнішнього ключа таблиці `users`
--
ALTER TABLE `users`
ADD CONSTRAINT `fk_Users_Photos1` FOREIGN KEY (`photos_id`) REFERENCES `photos` (`photo_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
