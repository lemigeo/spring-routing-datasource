CREATE DATABASE `master` /*!40100 DEFAULT CHARACTER SET utf8 */
CREATE DATABASE `slave` /*!40100 DEFAULT CHARACTER SET utf8 */
CREATE DATABASE `shard1` /*!40100 DEFAULT CHARACTER SET utf8 */
CREATE DATABASE `shard2` /*!40100 DEFAULT CHARACTER SET utf8 */

CREATE TABLE `master.account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reg_dt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `slave.account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reg_dt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `shard1.member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `reg_dt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `shard2.member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `reg_dt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;