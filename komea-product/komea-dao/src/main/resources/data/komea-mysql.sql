SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `komea` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `komea` ;

-- -----------------------------------------------------
-- Table `komea`.`kom_customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_proj`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_proj` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `projectKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  `idCustomer` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `key_UNIQUE` (`projectKey` ASC),
  INDEX `fk_Project_Customer1_idx` (`idCustomer` ASC))
ENGINE = InnoDB;


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
  PRIMARY KEY (`id`),
  UNIQUE INDEX `key_UNIQUE` (`personGroupKey` ASC),
  INDEX `fk_UserGroup_UserGroup1_idx` (`idPersonGroupParent` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_pero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pero` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `roleKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `roleKey_UNIQUE` (`roleKey` ASC))
ENGINE = InnoDB;


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
  PRIMARY KEY (`id`),
  INDEX `fk_User_UserGroup1_idx` (`idPersonGroup` ASC),
  UNIQUE INDEX `Personcol_UNIQUE` (`login` ASC),
  INDEX `fk_kom_pe_kom_pero1_idx` (`idPersonRole` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_pvd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pvd` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `providerType` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `icon` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `url_UNIQUE` (`url` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_kpi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_kpi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `kpiKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NOT NULL,
  `idProvider` INT NULL,
  `valueMin` DOUBLE NULL,
  `valueMax` DOUBLE NULL,
  `valueDirection` VARCHAR(255) NOT NULL,
  `valueType` VARCHAR(255) NOT NULL,
  `entityType` VARCHAR(255) NOT NULL,
  `esperRequest` MEDIUMTEXT NOT NULL,
  `entityID` INT NULL,
  `cronExpression` VARCHAR(60) NULL,
  `evictionRate` INT NOT NULL,
  `evictionType` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `key_UNIQUE` (`kpiKey` ASC),
  INDEX `fk_Metric_Plugin1_idx` (`idProvider` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_msr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_msr` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idKpi` INT NOT NULL,
  `date` DATE NOT NULL,
  `idPersonGroup` INT NULL,
  `idPerson` INT NULL,
  `idProject` INT NULL,
  `value` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Measure_Metric1_idx` (`idKpi` ASC),
  INDEX `fk_Measure_UserGroup1_idx` (`idPersonGroup` ASC),
  INDEX `fk_Measure_User1_idx` (`idPerson` ASC),
  INDEX `fk_Measure_Project1_idx` (`idProject` ASC))
ENGINE = InnoDB;


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
  PRIMARY KEY (`id`),
  INDEX `fk_MetricAlert_Metric1_idx` (`idKpi` ASC),
  UNIQUE INDEX `key_UNIQUE` (`kpiAlertKey` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_link` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `idProject` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Tag_Project_idx` (`idProject` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_evt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_evt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idProvider` INT NOT NULL,
  `eventKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `severity` VARCHAR(255) NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  `description` VARCHAR(2048) NULL,
  `category` VARCHAR(255) NOT NULL,
  `entityType` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Event_Plugin1_idx` (`idProvider` ASC),
  UNIQUE INDEX `key_UNIQUE` (`eventKey` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_setting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `settingKey` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `key_UNIQUE` (`settingKey` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_pe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_pe` (
  `idProject` INT NOT NULL,
  `idPerson` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idPerson`),
  INDEX `fk_Project_has_User_User1_idx` (`idPerson` ASC),
  INDEX `fk_Project_has_User_Project1_idx` (`idProject` ASC))
ENGINE = InnoDB;


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
  PRIMARY KEY (`id`),
  UNIQUE INDEX `key_UNIQUE` (`providerSettingKey` ASC),
  INDEX `fk_ProviderSetting_Provider1_idx` (`idProvider` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_tag` (
  `idProject` INT NOT NULL,
  `idTag` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idTag`),
  INDEX `fk_Project_has_Tag_Tag1_idx` (`idTag` ASC),
  INDEX `fk_Project_has_Tag_Project1_idx` (`idProject` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_kpia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_kpia` (
  `idProject` INT NOT NULL,
  `idKpiAlertType` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idKpiAlertType`),
  INDEX `fk_KOM_PROJ_has_KOM_KPIA_KOM_KPIA1_idx` (`idKpiAlertType` ASC),
  INDEX `fk_KOM_PROJ_has_KOM_KPIA_KOM_PROJ1_idx` (`idProject` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_kpia_pegr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_kpia_pegr` (
  `idKpiAlertType` INT NOT NULL,
  `idPersonGroup` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPersonGroup`),
  INDEX `fk_KOM_KPIA_has_KOM_PEGR_KOM_PEGR1_idx` (`idPersonGroup` ASC),
  INDEX `fk_KOM_KPIA_has_KOM_PEGR_KOM_KPIA1_idx` (`idKpiAlertType` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_kpia_pe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_kpia_pe` (
  `idKpiAlertType` INT NOT NULL,
  `idPerson` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPerson`),
  INDEX `fk_KOM_KPIA_has_KOM_PE_KOM_PE1_idx` (`idPerson` ASC),
  INDEX `fk_KOM_KPIA_has_KOM_PE_KOM_KPIA1_idx` (`idKpiAlertType` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_pegr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_pegr` (
  `idProject` INT NOT NULL,
  `idPersonGroup` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idPersonGroup`),
  INDEX `fk_kom_proj_has_kom_pegr_kom_pegr1_idx` (`idPersonGroup` ASC),
  INDEX `fk_kom_proj_has_kom_pegr_kom_proj1_idx` (`idProject` ASC))
ENGINE = InnoDB;


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
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
