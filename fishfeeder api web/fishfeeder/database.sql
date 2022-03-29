/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 10.1.35-MariaDB : Database - fishfeeder
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fishfeeder` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `fishfeeder`;

/*Table structure for table `hasil_sensor` */

DROP TABLE IF EXISTS `hasil_sensor`;

CREATE TABLE `hasil_sensor` (
  `id_sensor` int(11) NOT NULL AUTO_INCREMENT,
  `waktu` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `suhu` float DEFAULT '0',
  `ph` float DEFAULT '0',
  `oksigen` float DEFAULT '0',
  `keterangan` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id_sensor`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `hasil_sensor` */

insert  into `hasil_sensor`(`id_sensor`,`waktu`,`suhu`,`ph`,`oksigen`,`keterangan`) values (1,'2019-06-26 11:30:27',20,6,10,'tanah bergerak'),(2,'2019-06-26 11:30:39',30,8,10,'tanah bergerak'),(3,'2019-06-27 03:48:54',15,7,14,'tanah bergerak'),(4,'2019-06-27 03:49:04',17,3,11,'tanah bergerak'),(5,'2019-06-27 03:49:18',25,6,6,'tanah bergerak');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
