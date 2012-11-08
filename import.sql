/*
SQLyog Ultimate v9.50 
MySQL - 5.5.28-1-log : Database - market
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`market` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `market`;

/*Table structure for table `SEQUENCE` */

DROP TABLE IF EXISTS `SEQUENCE`;

CREATE TABLE `SEQUENCE` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `SEQUENCE` */

insert  into `SEQUENCE`(`SEQ_NAME`,`SEQ_COUNT`) values ('SEQ_GEN_TABLE','10000');

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) NOT NULL,
  `parent` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`id`,`name`,`parent`) values (1,'Товары',0),(102,'\"Белая техника\"',1),(103,'Шнуры',1),(104,'Телефоны',1),(105,'Радиоприёмники \"Лира\"',1),(106,'DECT Телефоны',104),(107,'Товары для телефонов',104),(108,'Электроплитки',109),(109,'Кухонная техника',102),(110,'Техника для гигиены',102),(111,'Кофемолки',109),(112,'Блендеры',109),(113,'Электробритвы',110),(114,'Ногтевые станки',110),(115,'Комплектующие',1),(116,'Товары для радиолюбителей',115),(117,'Паяльные принадлежности',115);

/*Table structure for table `photo` */

DROP TABLE IF EXISTS `photo`;

CREATE TABLE `photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(512) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `photo_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `photo_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `photo` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avail` tinyint(1) DEFAULT '0',
  `name` varchar(512) NOT NULL,
  `rating` int(11) NOT NULL,
  `category` int(11) NOT NULL,
  `price` int(11) unsigned NOT NULL DEFAULT '0',
  `comment` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_product_category` (`category`),
  CONSTRAINT `FK_product_category` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`id`,`avail`,`name`,`rating`,`category`,`price`,`comment`) values (1,0,'Электроплита мечта',1,108,0,'awdawd'),(2,0,'Электроплита малютка',2,108,0,'awdawd'),(3,0,'Ногтевая студия микма',1,114,0,'awdawd'),(4,0,'Машинка для обработки ногтей',1,114,0,'awdawd'),(5,0,'Кофемолка микма',1,111,0,'awdawd'),(6,0,'Кофемолка бинатон',0,111,0,'awdawd'),(7,0,'Блендер малютка',1,112,0,'awdawd'),(8,0,'Блендер бинатон',1,112,0,'awdawd'),(9,0,'Электробритва микма',1,113,0,'awdawd'),(10,0,'Электробритва браун',1,113,0,'awdawd'),(11,0,'Шнур hdmi-hdmi',1,103,0,'awdawd'),(12,0,'Шнур 3,5-3,5 1 метр',1,103,0,'awdawd'),(13,0,'Шнур скарт-джек',1,103,0,'awdawd'),(14,0,'Телефон самсунг',1,106,0,'awdawd'),(15,0,'Телефон LG',0,106,0,'awdawd'),(16,0,'Зарядка для телефона самсунг',1,107,0,'awdawd'),(17,0,'Шнур для телефона LG',1,107,0,'awdawd'),(18,0,'Радиоприёмник лира 1',1,105,0,'awdawd'),(19,0,'Радиоприёмник лира 2',1,105,0,'awdawd'),(20,0,'Припой пос-60',1,116,0,'awdawd'),(21,0,'Флюс',1,116,0,'awdawd'),(22,0,'Паяльник 40 ватт',5,117,0,'awdawd'),(23,0,'Плата макетная 30х30см',5,117,0,'awdawd');

/*Table structure for table `settings` */

DROP TABLE IF EXISTS `settings`;

CREATE TABLE `settings` (
  `key` varchar(128) NOT NULL,
  `value` varchar(512) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `settings` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` int(11) DEFAULT NULL,
  `login` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_users_0` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
