-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Час створення: Лют 03 2014 р., 11:12
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
  AUTO_INCREMENT =6;

--
-- Дамп даних таблиці `comments`
--

INSERT INTO `comments` (`comment_id`, `users_id`, `tour_id`, `next_comment_id`, `content`, `comment_date`) VALUES
  (1, 2, 1, NULL,
   'Nullam consectetur varius consectetur. Suspendisse ut tortor fringilla, ornare lacus tempor, venenatis est. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi purus ante, dictum nec eros et, volutpat consequat ante. Sed vulputate sapien ut aliquet consectetur. Quisque convallis gravida massa, sed hendrerit arcu laoreet at. Fusce quis iaculis quam, eget rhoncus turpis. Nullam iaculis eget arcu nec malesuada. Curabitur augue magna, suscipit eu magna in, condimentum tristique augue. Etiam eu interdum purus, eget egestas nisl. Sed vestibulum tortor in elit volutpat euismod. Fusce a felis volutpat, volutpat lorem a, consequat orci. Etiam nec nisl ac mi lobortis varius.',
   '2014-01-27 12:12:06'),
  (2, 3, 1, NULL,
   'Sed pretium, nibh vel dictum rutrum, lectus lorem porttitor lectus, et porttitor dui nunc sed ipsum. Nam a dolor in dui gravida ornare et nec sem. Vivamus eget enim ornare, pharetra magna ac, cursus metus. In quis commodo libero, sit amet accumsan risus. Etiam laoreet felis eu mi malesuada auctor. Donec et ultricies elit. Mauris et tortor vitae urna ornare pharetra vitae vel sem. Maecenas tristique quam sed tempor ultrices. Donec ut leo odio. Integer venenatis venenatis odio, eu pharetra orci consequat pretium. Sed lacinia rutrum arcu, non condimentum neque condimentum et.',
   '2014-01-27 12:12:06'),
  (3, 4, 2, NULL,
   'In hac habitasse platea dictumst. Curabitur rutrum justo eget mauris iaculis, at adipiscing massa feugiat. Sed rhoncus lacinia nisi. In at lobortis purus. Maecenas metus dui, aliquet eget semper ac, sagittis tincidunt elit. Fusce ante nibh, ultricies dignissim pellentesque eu, tincidunt consequat erat. Ut varius diam in erat consectetur ultricies. Curabitur vel nunc non lectus posuere aliquam et et quam. Nam tincidunt interdum tellus sit amet convallis. Sed velit ipsum, pulvinar a viverra vel, vestibulum at nisl. Nunc elementum tempor euismod. Phasellus porttitor nisl ut enim malesuada, consequat sodales erat faucibus. Vivamus dignissim pellentesque tincidunt. Morbi dapibus velit sit amet vulputate consequat. Etiam pharetra euismod eros, et sagittis turpis sagittis id.',
   '2014-01-27 12:12:06'),
  (4, 3, 2, NULL,
   'In hac habitasse platea dictumst. Curabitur rutrum justo eget mauris iaculis, at adipiscing massa feugiat. Sed rhoncus lacinia nisi. In at lobortis purus. Maecenas metus dui, aliquet eget semper ac, sagittis tincidunt elit. Fusce ante nibh, ultricies dignissim pellentesque eu, tincidunt consequat erat. Ut varius diam in erat consectetur ultricies. Curabitur vel nunc non lectus posuere aliquam et et quam. Nam tincidunt interdum tellus sit amet convallis. Sed velit ipsum, pulvinar a viverra vel, vestibulum at nisl. Nunc elementum tempor euismod. Phasellus porttitor nisl ut enim malesuada, consequat sodales erat faucibus. Vivamus dignissim pellentesque tincidunt. Morbi dapibus velit sit amet vulputate consequat. Etiam pharetra euismod eros, et sagittis turpis sagittis id.',
   '2014-01-27 12:12:06'),
  (5, 7, 1, NULL,
   '<p><img src="http://localhost:8080/quicktour/resources/ckeditor/plugins/smiley/images/skype/angry.gif"/></p>',
   '2014-01-30 17:29:18');

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
   'Ivano-Frankivsk', '0123456789', 'default@comp.com', 'default', 10, 'company license', '12345678987654321', 5),
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
  AUTO_INCREMENT =3;

--
-- Дамп даних таблиці `discount_dependency`
--

INSERT INTO `discount_dependency` (`discount_dependency_id`, `tag`, `table_field`, `description`) VALUES
  (1, 'adult', 'orders.numberOfAdults', 'Number of adults in order'),
  (2, 'age', 'users.age', 'User''s age');

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
  (4, 'Grown up discount', 'Discount for grown ups!', 'users.age>20', '3', NULL, NULL, 2),
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
  `users_id`             INT(11)          NOT NULL,
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
  AUTO_INCREMENT =45;

--
-- Дамп даних таблиці `orders`
--

INSERT INTO `orders` (`order_id`, `order_date`, `number_of_adults`, `number_of_children`, `user_info`, `price`, `discount`, `next_payment_date`, `status`, `tour_info_id`, `companies_id`, `users_id`, `accepted_date`, `confirmed_date`, `completed_date`, `cancelled_date`, `vote`, `discount_information`)
VALUES
  (4, '2013-12-10 00:00:00', 1, 2, 'User info 1334', '2000.00', '10.00', '2013-12-15', 'Received', 4, 2, 8, NULL, NULL,
   NULL, NULL, 0, ''),
(5, '2013-11-20 00:00:00', 3, 0, 'User info 2134', '1500.00', '0.00', '2013-11-25', 'Received', 5, 2, 9, NULL, NULL, NULL, NULL, 0, ''),
(7, '2013-12-18 00:00:00', 2, 1, 'User info 888', '800.00', '10.00', '2013-12-21', 'Confirmed', 1, 1, 7, NULL, NULL, NULL, NULL, 0, ''),
(9, '2013-12-18 00:00:00', 2, 2, 'User info 3255', '1200.00', '10.00', '2013-12-20', 'Completed', 5, 2, 7, NULL, NULL, NULL, NULL, 0, ''),
(10, '2013-12-29 15:05:06', 5, 3, 'User info 1', '2500.00', '15.00', '2014-01-05', 'Completed', 15, 1, 8, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 5, ''),
(11, '2013-12-30 12:08:03', 2, 0, 'User info 2', '1500.00', '10.00', '2014-01-18', 'Confirmed', 19, 2, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', NULL, NULL, 0, ''),
(12, '2013-12-25 10:00:20', 3, 2, 'User info 3', '2000.00', '5.00', '2013-12-27', 'Cancelled', 10, 2, 9, NULL, NULL, NULL, '2013-12-28 00:00:00', 0, ''),
(15, '2013-12-10 15:05:06', 5, 3, 'User info 6', '2500.00', '15.00', '2013-12-15', 'Completed', 5, 1, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 4, ''),
(16, '2013-12-29 15:05:06', 5, 3, 'User info 7', '2500.00', '15.00', '2014-01-05', 'Completed', 16, 2, 9, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 5, ''),
(19, '2013-12-19 15:05:06', 4, 3, 'User info 10', '2400.00', '15.00', '2014-01-02', 'Completed', 2, 1, 8, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 5, ''),
(20, '2013-12-20 12:08:03', 1, 2, 'User info 11', '1200.00', '10.00', '2014-01-08', 'Confirmed', 3, 2, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', NULL, NULL, 0, ''),
(21, '2013-12-15 10:00:20', 2, 2, 'User info 12', '1800.00', '5.00', '2013-12-17', 'Cancelled', 4, 2, 9, NULL, NULL, NULL, '2013-12-18 00:00:00', 0, ''),
(22, '2013-12-20 08:01:15', 3, 2, 'User info 13', '1100.00', '12.00', NULL, 'Received', 5, 1, 9, NULL, NULL, NULL, NULL, 0, ''),
(24, '2013-12-05 15:05:06', 2, 3, 'User info 15', '2400.00', '15.00', '2013-12-05', 'Completed', 7, 1, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 2, ''),
(25, '2013-12-19 15:05:06', 3, 3, 'User info 16', '2500.00', '15.00', '2014-01-02', 'Completed', 8, 2, 9, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 1, ''),
(26, '2013-12-20 18:11:25', 1, 0, 'User info 17', '2600.00', '5.00', NULL, 'Received', 5, 1, 9, NULL, NULL, NULL, NULL, 0, ''),
(28, '2013-12-30 15:05:06', 6, 3, 'User info 19', '2200.00', '15.00', '2014-01-05', 'Completed', 11, 1, 8, '2013-12-30 00:00:00', '2014-01-03 00:00:00', '2014-01-05 00:00:00', NULL, 5, ''),
(29, '2013-12-31 12:08:03', 3, 2, 'User info 20', '1100.00', '10.00', '2014-01-18', 'Confirmed', 12, 2, 7, '2013-12-30 00:00:00', '2014-01-03 00:00:00', NULL, NULL, 0, ''),
(30, '2013-12-26 10:00:20', 4, 3, 'User info 21', '1800.00', '5.00', '2013-12-27', 'Cancelled', 13, 2, 9, NULL, NULL, NULL, '2013-12-29 00:00:00', 0, ''),
(33, '2013-12-11 15:05:06', 4, 3, 'User info 24', '2100.00', '15.00', '2013-12-15', 'Completed', 16, 1, 7, '2013-12-28 00:00:00', '2014-01-05 00:00:00', '2014-01-09 00:00:00', NULL, 3, ''),
(34, '2013-12-30 15:05:06', 2, 4, 'User info 25', '2200.00', '15.00', '2014-01-05', 'Completed', 17, 2, 9, '2013-12-31 00:00:00', '2014-01-06 00:00:00', '2014-01-08 00:00:00', NULL, 5, ''),
  (37, '2013-12-28 15:05:06', 2, 3, 'User info 28', '2100.00', '15.00', '2014-01-05', 'Completed', 5, 1, 8,
   '2013-12-29 00:00:00', '2014-01-02 00:00:00', '2014-01-06 00:00:00', NULL, 5, ''),
  (38, '2013-12-29 12:08:03', 2, 0, 'User info 29', '1600.00', '10.00', '2014-01-18', 'Confirmed', 9, 2, 7,
   '2013-12-29 00:00:00', '2014-01-03 00:00:00', NULL, NULL, 0, ''),
  (39, '2013-12-24 10:00:20', 3, 2, 'User info 30', '2050.00', '5.00', '2013-12-27', 'Cancelled', 12, 2, 9, NULL, NULL,
   NULL, '2013-12-30 00:00:00', 0, ''),
  (40, '2013-12-29 08:01:15', 4, 2, 'User info 31', '1100.00', '12.00', NULL, 'Received', 5, 1, 8, NULL, NULL, NULL,
   NULL, 0, ''),
  (42, '2013-12-09 15:05:06', 5, 3, 'User info 33', '2500.00', '15.00', '2013-12-15', 'Completed', 5, 1, 7,
   '2013-12-28 00:00:00', '2014-01-03 00:00:00', '2014-01-06 00:00:00', NULL, 2, ''),
  (43, '2013-12-28 15:05:06', 5, 3, 'User info 34', '2600.00', '15.00', '2014-01-05', 'Completed', 6, 2, 9,
   '2013-12-29 00:00:00', '2014-01-04 00:00:00', '2014-01-08 00:00:00', NULL, 5, ''),
  (44, '2013-12-29 18:11:25', 4, 2, 'User info 35', '2250.00', '5.00', NULL, 'Received', 5, 1, 7, NULL, NULL, NULL,
   NULL, 0, '');

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
  AUTO_INCREMENT =26;

--
-- Дамп даних таблиці `photos`
--

INSERT INTO `photos` (`photo_id`, `url`) VALUES
  (1, 'http://buddies.koinup.com/group-637.png'),
  (2, 'http://infinitelives.net/avatars/flashjen.jpg'),
  (3, 'http://www.templatesbox.com/data/premium.templates/images/logo/full.preview/11897522410FI.jpg'),
  (4, 'defaultAvatar.jpg'),
  (5, 'defaultLogo.jpg'),
  (16, 'http://www.flickr.com/photos/115679176@N05/12273131466/'),
  (17, 'http://www.flickr.com/photos/115679176@N05/12273858965/'),
  (18, 'http://www.flickr.com/photos/115679176@N05/12274474636/'),
  (19, 'http://farm6.static.flickr.com/5483/12274496786_eea23fd76b.jpg'),
  (20, 'http://farm3.static.flickr.com/2887/12275570864_62dafd7712.jpg'),
  (21, 'http://farm4.static.flickr.com/3739/12275626414_b62a81b5c0.jpg'),
  (22, 'http://farm6.static.flickr.com/5477/12275846006_b17a123c86.jpg'),
  (23, 'http://farm8.static.flickr.com/7445/12275779444_250be177b2.jpg'),
  (24, 'http://farm8.static.flickr.com/7315/12275873994_664a220b1b.jpg'),
  (25, 'http://farm4.static.flickr.com/3713/12275648973_3e4336e02e.jpg');

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
  AUTO_INCREMENT =10;

--
-- Дамп даних таблиці `places`
--

INSERT INTO `places` (`place_id`, `country`, `name`, `description`, `optional`, `price`, `geoheight`, `geowidth`) VALUES
  (1, 'Greece', 'Athines',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.',
   1, 800, 37.966667, 23.716667),
  (2, 'Greece', 'Mediteranian Sea',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.',
   1, 1200, 36.923548, 21.68518),
  (3, 'USA', 'New York',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.',
   1, 1100, 40.742055, - 74.013748),
  (4, 'USA', 'Chicago',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.',
   1, 600, 41.896144, - 87.628555),
  (5, 'USA', 'Michigan Lake',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.',
   0, 800, 43.032761, - 87.902527),
  (6, 'Poland', 'Krakiv',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.',
   1, 800, 50.073888, 19.944191),
  (7, 'Poland', 'Warsaw',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.',
   1, 800, 52.237472, 21.009865),
  (8, 'USA', 'Kansas', NULL, 1, 900, 39.124734, - 94.576035),
  (9, 'USA', 'Las Vegas', NULL, 1, 1100, 36.11777, - 115.172453);

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
-- Структура таблиці `tours`
--

CREATE TABLE IF NOT EXISTS `tours` (
  `tour_id`               INT(11) NOT NULL AUTO_INCREMENT,
  `name`                  VARCHAR(128) DEFAULT NULL,
  `description`           VARCHAR(4096) DEFAULT NULL,
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
  AUTO_INCREMENT =11;

--
-- Дамп даних таблиці `tours`
--

INSERT INTO `tours` (`tour_id`, `name`, `description`, `transport_description`, `photo_id`, `active`, `price`, `companies_id`, `travel_type`)
VALUES
  (1, 'Tour to Greece',
   '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies</p>',
   'Plain', 25, 1, 0, 2, 'driving'),
  (2, 'Tour to USA',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies',
   'Plain', NULL, 1, 1700, 2, 'driving'),
  (3, 'Tour to Poland',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies',
   'Bus', NULL, 1, 1600, 2, 'bycicling'),
  (4, 'Fantastic places of Asia',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, ',
   'Plain, Bus', NULL, 1, 3600, 2, 'driving'),
  (5, 'Germany famous cities',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies',
   'Bus, Train', NULL, 1, 2500, 2, 'driving'),
  (6, 'Europe tour',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, ',
   'Bus, Train, Plain', NULL, 1, 3400, 2, 'driving'),
  (7, 'Tour around the world',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, ',
   'All the most popular kinds of transports', NULL, 1, 5900, 2, 'driving'),
  (8, 'U. S. Yosemite National park',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, ',
   'Plain, Car', NULL, 1, 4100, 2, 'driving'),
  (9, 'Tour to Canada',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, ',
   'Plain', NULL, 1, 3500, 2, 'driving'),
  (10, 'Tour to Russia',
   'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, ',
   'Plain, Train, Bus', NULL, 1, 2600, 2, 'driving');

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
  AUTO_INCREMENT =57;

--
-- Дамп даних таблиці `tours_discount_policy`
--

INSERT INTO `tours_discount_policy` (`tours_discount_policy_id`, `tours_id`, `discount_policy_id`) VALUES
  (25, 3, 4),
  (26, 3, 5),
  (27, 3, 6),
  (28, 3, 7),
  (29, 4, 4),
  (30, 4, 5),
  (31, 4, 6),
  (32, 4, 7),
  (33, 5, 4),
  (34, 5, 5),
  (35, 5, 6),
  (36, 5, 7),
  (37, 6, 4),
  (38, 6, 5),
  (39, 6, 6),
  (40, 6, 7),
  (41, 7, 4),
  (42, 7, 5),
  (43, 7, 6),
  (44, 7, 7),
  (45, 8, 4),
  (46, 8, 5),
  (47, 8, 6),
  (48, 8, 7),
  (49, 9, 4),
  (50, 9, 5),
  (51, 9, 6),
  (52, 9, 7),
  (53, 10, 4),
  (54, 10, 5),
  (55, 10, 6),
  (56, 10, 7);

-- --------------------------------------------------------

--
-- Структура таблиці `tours_places`
--

CREATE TABLE IF NOT EXISTS `tours_places` (
  `tours_place_id` INT(11) NOT NULL,
  `tours_id`       INT(11) NOT NULL,
  `places_id`      INT(11) NOT NULL,
  PRIMARY KEY (`tours_place_id`),
  KEY `fk_Tours_has_Places_Places1_idx` (`places_id`),
  KEY `fk_Tours_has_Places_Tours1_idx` (`tours_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;

--
-- Дамп даних таблиці `tours_places`
--

INSERT INTO `tours_places` (`tours_place_id`, `tours_id`, `places_id`) VALUES
  (1, 2, 3),
  (2, 2, 4),
  (3, 2, 5),
  (4, 2, 8),
  (5, 2, 9),
  (6, 3, 6),
  (7, 3, 7);

-- --------------------------------------------------------

--
-- Структура таблиці `tours_price_includes`
--

CREATE TABLE IF NOT EXISTS `tours_price_includes` (
  `tours_price_include_id` INT(11) NOT NULL,
  `tours_id`               INT(11) NOT NULL,
  `price_includes_id`      INT(11) NOT NULL,
  PRIMARY KEY (`tours_price_include_id`),
  KEY `fk_Tours_has_PriceIncludes_PriceIncludes1_idx` (`tours_price_include_id`),
  KEY `fk_Tours_has_PriceIncludes_Tours1_idx` (`tours_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;

--
-- Дамп даних таблиці `tours_price_includes`
--

INSERT INTO `tours_price_includes` (`tours_price_include_id`, `tours_id`, `price_includes_id`) VALUES
  (1, 2, 1),
  (2, 2, 2),
  (3, 2, 3),
  (4, 3, 1),
  (5, 3, 2),
  (6, 3, 3),
  (7, 3, 4),
  (8, 3, 7);

-- --------------------------------------------------------

--
-- Структура таблиці `tour_info`
--

CREATE TABLE IF NOT EXISTS `tour_info` (
  `tour_info_id` INT(11) NOT NULL AUTO_INCREMENT,
  `tours_id`     INT(11) NOT NULL,
  `start_date`   DATE DEFAULT NULL,
  `end_date`     DATE DEFAULT NULL,
  `discount`     INT(3) DEFAULT NULL,
  PRIMARY KEY (`tour_info_id`),
  UNIQUE KEY `Tourid_UNIQUE` (`tour_info_id`),
  KEY `fk_Tour_tours_idx` (`tours_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =20;

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
  (19, 10, '2014-01-30', '2014-02-20', 2);

-- --------------------------------------------------------
--
-- Структура таблиці `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id`      INT(11)      NOT NULL AUTO_INCREMENT,
  `login`        VARCHAR(16)  NOT NULL,
  `password`     VARCHAR(128) DEFAULT NULL,
  `email`        VARCHAR(255) NOT NULL,
  `phone`        VARCHAR(45) DEFAULT NULL,
  `create_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name`         VARCHAR(45) DEFAULT NULL,
  `surname`      VARCHAR(45) DEFAULT NULL,
  `age`          INT(11) DEFAULT NULL,
  `sex`          VARCHAR(45) DEFAULT NULL,
  `company_code` VARCHAR(20) DEFAULT NULL,
  `photos_id`    INT(11) DEFAULT NULL,
  `active`       VARCHAR(10) DEFAULT NULL,
  `role`         VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `id_UNIQUE` (`user_id`),
  KEY `fk_Users_Photos1_idx` (`photos_id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  AUTO_INCREMENT =20;

--
-- Дамп даних таблиці `users`
--

INSERT INTO `users` (`user_id`, `login`, `password`, `email`, `phone`, `create_time`, `name`, `surname`, `age`, `sex`, `company_code`, `photos_id`, `active`, `role`)
VALUES
  (1, 'yan', '7a9b4bd9948d7a47e824f873f72e371254850fa7284b2ec2d6beb048246ed981b9c9e20493e137f4',
   'yankolyaspas@gmail.com', NULL, '2014-01-27 12:12:05', 'Kolya', NULL, NULL, NULL, '12345678985554321', 4, '1',
   'agent'),
(2, 'bogdanshp', '74ec0c44deec96ba486de709ec327932f43c0c40ae0d2c0c286528f23ae61e92eb57762ef3a06925', 'bogdanshpakovsky@gmail.com', NULL, '2014-01-27 12:12:05', 'Bodya', NULL, NULL, NULL, '', 4, '1', 'agent'),
(3, 'Zarichnyi', '9b93c8bf2241cf090ecf95a16914d82987d0a0f2bd4a380f5a907558844d896f0c9eaac8e574b288', 'kopalhem@gmail.com', '0987654', '2014-01-27 12:12:06', 'Andrew', 'Zarichnyi', 20, 'Male', '3244221331', 4, '1', 'admin'),
(4, 'admin', 'c018ea78cf1602d6bc5ff3d94462b332bd1c2c099caab19a6910fa160e5ebeb6fcfc5eee605d96b3', 'odd.mean@gmail.com', '0955555555', '2014-01-27 12:12:06', 'Odd', 'Mean', 30, 'Male', '001', 4, '1', 'admin'),
(5, 'agent1', '091fd45e668251e235831fbceaa76630cfeede62c968dca8ea340dd54c3f5990cf5cec733f744a64', 'agent.one@gmail.com', '0633333333', '2014-01-27 12:12:06', 'agentOneName', 'agentOneSurname', 25, 'Female', '12345678985554321', 4, '1', 'agent'),
(6, 'agent2', '4c59e5b9cb5a491ff5c1fedd7254a345b35af67ff40bbd7b39ec8bde390cc3a7f6c2f7ff9a26dab8', 'agent.two@gmail.com', '0999999999', '2014-01-27 12:12:06', 'agentTwoName', 'agentTwoSurname', 23, 'Male', '12345678987654321', 4, '1', 'agent'),
(7, 'user1', '55ff08400ab98e506e2ab58b3e62215dd1774776fa2f0dd3d94170456995d9b6a6f93b41823c92b5', 'user.one@gmail.com', '0667891234', '2014-01-27 12:12:06', 'UserOneName', 'UserOneSurname', 27, 'Female', '12345678987654321', 4, '1', 'user'),
(8, 'user2', '3a4e3ccd5f2937f1e179e9a015a7f26892e997399c33862cfd8e9fd459e4a88f0c73191d9aa4c66e', 'user.two@gmail.com', '0687801235', '2014-01-27 12:12:06', 'UserTwoName', 'UserTwoSurname', 21, 'Male', '', 4, '1', 'user'),
(9, 'user3', '2eaf7abd06b27207ef710b08b996454349f8fa2b49021249118cbb83e0b20a3f43cd0bd2a7a907db', 'user.three@gmail.com', '0953591554', '2014-01-27 12:12:06', 'UserThreeName', 'UserThreeSurname', 19, 'Male', '', 4, '1', 'user'),
(16, 'dsadsa', 'c61ab5da573fd3ae8a4bcf2ef39bd1bd157dc649e1bde2084017cd6baa574351ecd60e665ffdd133', 'dsad@dasd.com', '32312321', '2014-02-02 13:28:48', 'dsda', 'dsads', 12, NULL, '', NULL, '0', 'user'),
(17, 'gazda', 'ddc25373e5290d9507e47784b0ed25f4aedb187fc270aa62aa96183bba426b841e83a3720a8c21c2', 'dsads@dasda.com', '321312321', '2014-02-02 18:07:13', 'dsad', 'dasdsa', 123, 'Male', '', 16, '0', 'user'),
  (18, 'dsads', 'fa8b90768d7c462fd781ddc075317d425a9d876447e7f2ec35718be4fefb098070866ddb35152e0f', 'dsa@das.com',
   '1232112', '2014-02-02 19:20:14', 'dasds', 'das', 123, NULL, '', NULL, '0', 'user'),
  (19, 'dsadsad', 'f927fad9718ebfaf17cb7be6ca5fa42178ff7e47a914b11799086e314fe275b81803f29255ceee3a', 'dasdsa@das.com',
   '321321', '2014-02-02 19:23:10', 'dsad', 'dasdasdsa', 123, NULL, '', NULL, '0', 'user');

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
  AUTO_INCREMENT =10;

--
-- Дамп даних таблиці `validation_links`
--

INSERT INTO `validation_links` (`validation_link_id`, `user_id`, `url`, `create_time`) VALUES
  (1, 11, 'localhost:/login/dasdas', '2014-02-01 14:02:48'),
  (2, 12, 'localhost:/login/das', '2014-02-01 19:00:11'),
  (3, 13, 'localhost:/login/dsadas', '2014-02-01 19:21:20'),
  (4, 14, 'localhost:/login/dsadsa', '2014-02-02 09:54:28'),
  (6, 16, 'localhost:/login/dsadsa', '2014-02-02 13:28:48'),
  (7, 17, 'localhost:/login/gazda', '2014-02-02 18:07:13'),
  (8, 18, 'localhost:/login/dsads', '2014-02-02 19:20:15'),
  (9, 19, 'localhost:/login/dsadsad', '2014-02-02 19:23:10');

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
-- Обмеження зовнішнього ключа таблиці `orders`
--
ALTER TABLE `orders`
ADD CONSTRAINT `orders_ibfk_6` FOREIGN KEY (`users_id`) REFERENCES `users` (`user_id`),
ADD CONSTRAINT `orders_ibfk_4` FOREIGN KEY (`tour_info_id`) REFERENCES `tour_info` (`tour_info_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `orders_ibfk_5` FOREIGN KEY (`companies_id`) REFERENCES `companies` (`company_id`);

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
