/*
SQLyog Trial v11.11 (32 bit)
MySQL - 5.5.33-1-log : Database - market
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`market` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `market`;

/*Table structure for table `SEQUENCE` */

DROP TABLE IF EXISTS `SEQUENCE`;

CREATE TABLE `SEQUENCE` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `SEQUENCE` */

insert  into `SEQUENCE`(`SEQ_NAME`,`SEQ_COUNT`) values ('SEQ_GEN_TABLE',10000);

/*Table structure for table `group` */

DROP TABLE IF EXISTS `group`;

CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) NOT NULL,
  `parent` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;

/*Data for the table `group` */

insert  into `group`(`id`,`name`,`parent`) values (1,'Товары',0),(103,'Шнуры',1),(105,'Радиоприёмники \"Лира\"',1),(118,'Шнуры Premier',103);

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
  CONSTRAINT `photo_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `photo_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Data for the table `photo` */

insert  into `photo`(`id`,`path`,`name`,`product_id`,`user_id`) values (23,'resources/market/lira/249.jpg','a',16,1),(24,'resources/market/lira/260.jpg','a',18,1),(25,'resources/market/lira/260-1.jpg','a',19,1),(26,'resources/market/lira/234-1.jpg','a',5,1),(27,'resources/market/lira/236.jpg','a',8,1),(28,'resources/market/lira/238.jpg','a',9,1),(29,'resources/market/lira/238-2.jpg','a',11,1),(30,'resources/market/lira/246.jpg','a',12,1),(31,'resources/market/lira/248.jpg','a',13,1),(32,'resources/market/lira/248-1.jpg','a',15,1),(34,'resources/market/lira/238.jpg','a',10,1);

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avail` tinyint(1) DEFAULT '0',
  `name` varchar(512) NOT NULL,
  `rating` int(11) NOT NULL,
  `price` int(11) unsigned NOT NULL DEFAULT '0',
  `comment` varchar(512) DEFAULT NULL,
  `barcode` varchar(256) DEFAULT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`id`,`avail`,`name`,`rating`,`price`,`comment`,`barcode`,`group_id`) values (5,1,'Лира РП-234-1',0,120,NULL,'1',105),(8,1,'Лира РП-236',0,300,NULL,'2',105),(9,1,'Лира РП-238',0,400,NULL,'3',105),(10,1,'Лира РП-238-1',0,500,NULL,'4',105),(11,1,'Лира РП-238-2',0,600,NULL,'5',105),(12,1,'Лира РП-246',0,700,NULL,'6',105),(13,1,'Лира РП-248',0,800,NULL,'7',105),(15,1,'Лира РП-248-1',0,900,NULL,'8',105),(16,1,'Лира РП-249',0,1000,NULL,'9',105),(18,1,'Лира РП-260',0,1100,NULL,'10',105),(19,1,'Лира РП-260-1',0,1200,NULL,'11',105);

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

insert  into `users`(`id`,`level`,`login`,`password`) values (1,0,'a','a');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
