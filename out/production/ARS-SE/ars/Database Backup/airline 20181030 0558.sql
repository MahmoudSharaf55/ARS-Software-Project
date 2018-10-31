-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.51b-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema airline
--

CREATE DATABASE IF NOT EXISTS airline;
USE airline;

--
-- Definition of table `airports`
--

DROP TABLE IF EXISTS `airports`;
CREATE TABLE `airports` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `airports`
--

/*!40000 ALTER TABLE `airports` DISABLE KEYS */;
/*!40000 ALTER TABLE `airports` ENABLE KEYS */;


--
-- Definition of table `flight`
--

DROP TABLE IF EXISTS `flight`;
CREATE TABLE `flight` (
  `flightNumber` int(10) unsigned NOT NULL auto_increment,
  `src` varchar(45) NOT NULL,
  `dest` varchar(45) NOT NULL,
  `dateAndTime` date NOT NULL default '0000-00-00',
  `price` int(10) unsigned NOT NULL,
  `seats` int(10) unsigned NOT NULL,
  `delay` tinyint(1) NOT NULL default '0',
  `master_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`flightNumber`),
  KEY `master_id` (`master_id`),
  CONSTRAINT `master_id` FOREIGN KEY (`master_id`) REFERENCES `master` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `flight`
--

/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;


--
-- Definition of table `master`
--

DROP TABLE IF EXISTS `master`;
CREATE TABLE `master` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `officeName` varchar(45) NOT NULL,
  `phone` varchar(11) NOT NULL default ' ',
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `master`
--

/*!40000 ALTER TABLE `master` DISABLE KEYS */;
INSERT INTO `master` (`id`,`officeName`,`phone`,`email`,`password`) VALUES 
 (1,'egypt air','Female','mohammed','�-{�$$�ը8��_'),
 (2,'efjsd','Female','mohammmed','�-{�$$�ը8��_'),
 (3,'werwer','Male','mohamed','�-{�$$�ը8��_'),
 (4,'sdfsdf','Male','mohammeed','�qYaT��'),
 (5,'sdfsdf','Male','mohammyed','���ڤ�'),
 (6,'ewrwer','34345345','ertert','g�%$����'),
 (7,'ewrwer','34345345','ertert4','g�%$����');
/*!40000 ALTER TABLE `master` ENABLE KEYS */;


--
-- Definition of table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `user_id` int(10) unsigned NOT NULL,
  `flight_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `user_id` (`user_id`),
  KEY `flight_id` (`flight_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `flight_id` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`flightNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ticket`
--

/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `date` date NOT NULL,
  `phone` varchar(10) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `rating` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  USING BTREE (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`,`name`,`date`,`phone`,`email`,`password`,`rating`) VALUES
 (11,'asdasd','2018-10-22','Male','asdasd','wucI��V5',0),
 (15,'Mohammed Sedky','1998-02-03','Male','mohammedsedky01147226634@gmail.com','�-{�$$�ը8��_',0),
 (17,'sdfsdf','2018-10-22','Male','mohammed','$a9,��1��/��e1',0),
 (18,'ssdf','2018-10-23','Male','ahmed','J.@g���',0),
 (19,'mohaned','1998-10-07','Male','mo','�~���e^�',0),
 (20,'asdasds','2018-10-26','Male','mohammmed','�-{�$$�ը8��_',0),
 (21,'asdasdasd','2018-10-30','Male','mohammedd','揇��\0��',0),
 (22,'erwerwe','2018-10-26','Male','erwerwer','��:�N���ը8��_',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
