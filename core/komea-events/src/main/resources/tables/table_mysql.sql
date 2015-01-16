CREATE TABLE IF NOT EXISTS `#table#` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `data` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX (`date`)
);