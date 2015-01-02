CREATE TABLE IF NOT EXISTS `events` (
`id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `date` DATETIME NOT NULL,
  `provider` varchar(128) NOT NULL,
  `eventType` varchar(255) NOT NULL,
  `data` blob NOT NULL
);

CREATE INDEX IF NOT EXISTS `events_date` ON `events`(`date` ASC);
