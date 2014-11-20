CREATE TABLE  `kom_evt_storage` (
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
);


--CREATE INDEX  `evt_provider` ON `kom_evt_storage` (`provider` ASC) ;
--CREATE INDEX  `evt_date` ON `kom_evt_storage` (`date` ASC) ;
--CREATE INDEX  `evt_key` ON `kom_evt_storage` (`eventKey` ASC) ;
--

CREATE TABLE  `kom_evt_storage_attr` (
  `id` int(11) NOT NULL,
  `attrName` varchar(60) NOT NULL,
  `attrValue` varchar(255) NOT NULL,
  KEY `att` (`id`, `attrName`)
); 

ALTER TABLE `kom_evt_storage_attr` 
ADD CONSTRAINT `attr_fk`
  FOREIGN KEY (id)
  REFERENCES `kom_evt_storage` (id)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

CREATE INDEX  `evt_attr_id` ON `kom_evt_storage_attr` (`id` ASC) ;

CREATE TABLE `kom_evt_storage_blob` (
  `id` int(11) NOT NULL,
  `textmessage` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`id`)
); 

ALTER TABLE `kom_evt_storage_blob` 
ADD CONSTRAINT `blob_fk`
  FOREIGN KEY (id)
  REFERENCES `kom_evt_storage` (id)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;

CREATE INDEX  `evt_attr_blob_id` ON `kom_evt_storage_blob` (`id` ASC) ;