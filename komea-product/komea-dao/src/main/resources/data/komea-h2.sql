
-- -----------------------------------------------------
-- Table `komea`.`kom_customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_proj`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_proj` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `projectKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  `idCustomer` INT NULL,
  `icon` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_pegr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pegr` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `personGroupKey` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  `idPersonGroupParent` INT NULL,
  `type` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_pero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pero` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `roleKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_pe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pe` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idPersonGroup` INT NULL,
  `idPersonRole` INT NULL,
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `login` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL DEFAULT '',
  `userBdd` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_kpi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_kpi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `kpiKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NOT NULL,
  `valueMin` DOUBLE NULL,
  `valueMax` DOUBLE NULL,
  `valueDirection` VARCHAR(255) NOT NULL,
  `valueType` VARCHAR(255) NOT NULL,
  `entityType` VARCHAR(255) NULL,
  `esperRequest` MEDIUMTEXT NOT NULL,
  `entityID` INT NULL,
  `cronExpression` VARCHAR(60) NULL,
  `evictionRate` INT NOT NULL,
  `evictionType` VARCHAR(255) NOT NULL,
  `objective` DOUBLE NULL,
  `providerType` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_msr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_msr` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idKpi` INT NOT NULL,
  `date` TIMESTAMP NOT NULL,
  `step` VARCHAR(2) NOT NULL,
  `formula` VARCHAR(10) NOT NULL,
  `idProject` INT NULL,
  `idPerson` INT NULL,
  `idPersonGroup` INT NULL,
  `value` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_kpia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_kpia` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idKpi` INT NOT NULL,
  `kpiAlertKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  `severity` VARCHAR(255) NOT NULL,
  `value` DOUBLE NOT NULL,
  `averageSince` DATE NULL,
  `operator` VARCHAR(255) NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_pvd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pvd` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `providerType` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `icon` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_link` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `idProject` INT NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_evt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_evt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `eventKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `severity` VARCHAR(255) NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  `description` VARCHAR(2048) NULL,
  `entityType` VARCHAR(255) NULL,
  `providerType` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_setting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `settingKey` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_pe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_pe` (
  `idProject` INT NOT NULL,
  `idPerson` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idPerson`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_pvds`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pvds` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `providerSettingKey` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  `idProvider` INT NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_tag` (
  `idProject` INT NOT NULL,
  `idTag` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idTag`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_pegr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_pegr` (
  `idProject` INT NOT NULL,
  `idPersonGroup` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idPersonGroup`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_acfi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_acfi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `entityType` VARCHAR(255) NOT NULL,
  `idEntity` INT NULL,
  `infoRetention` VARCHAR(255) NOT NULL,
  `minorRetention` VARCHAR(255) NOT NULL,
  `majorRetention` VARCHAR(255) NOT NULL,
  `criticalRetention` VARCHAR(255) NOT NULL,
  `blockerRetention` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_sfac`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_sfac` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `successFactorKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_sfac_kpi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_sfac_kpi` (
  `idSuccessFactor` INT NOT NULL,
  `idKpi` INT NOT NULL,
  PRIMARY KEY (`idSuccessFactor`, `idKpi`))
;
