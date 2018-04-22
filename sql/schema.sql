CREATE DATABASE `master` DEFAULT CHARACTER SET utf8;
CREATE DATABASE `slave` DEFAULT CHARACTER SET utf8;
CREATE DATABASE `shard1` DEFAULT CHARACTER SET utf8;
CREATE DATABASE `shard2` DEFAULT CHARACTER SET utf8;

USE master;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reg_dt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

USE slave;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reg_dt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

USE shard1;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `reg_dt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

USE shard2;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `reg_dt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;