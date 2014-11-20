CREATE TABLE IF NOT EXISTS `kom_evt_storage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider` varchar(255) NOT NULL,
  `eventKey` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `entity1` varchar(255) DEFAULT NULL,
  `entity2` varchar(255) DEFAULT NULL,
  `entity3` varchar(255) DEFAULT NULL,
  `entity4` varchar(255) DEFAULT NULL,
  `value1` double DEFAULT NULL,
  `value2` double DEFAULT NULL,
  `value3` double DEFAULT NULL,
  `value4` double DEFAULT NULL,
  `value5` double DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `evt_date` (`date`),
  KEY `evt_key` (`provider`,`eventKey`,`date`),
  KEY `entity1` (`entity1`),
  KEY `entity2` (`entity2`),
  KEY `entity3` (`entity3`),
  KEY `entity4` (`entity4`)
) ENGINE=InnoDB AUTO_INCREMENT=38123 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `kom_evt_storage_attr` (
  `id` int(11) NOT NULL,
  `attrName` varchar(60) NOT NULL,
  `attrValue` varchar(255) NOT NULL,
  KEY `att` (`attrName`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 MIN_ROWS=10000000 PACK_KEYS=1;

CREATE TABLE IF NOT EXISTS `kom_evt_storage_blob` (
  `id` int(11) NOT NULL,
  `blob` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
