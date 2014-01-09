SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `komea` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `komea` ;

-- -----------------------------------------------------
-- Table `komea`.`KOM_CUSTOMER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_CUSTOMER` (
  `idCustomer` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idCustomer`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_PROJ`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_PROJ` (
  `idProject` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `idCustomer` INT NOT NULL,
  PRIMARY KEY (`idProject`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC),
  INDEX `fk_Project_Customer1_idx` (`idCustomer` ASC),
  CONSTRAINT `fk_Project_Customer1`
    FOREIGN KEY (`idCustomer`)
    REFERENCES `komea`.`KOM_CUSTOMER` (`idCustomer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_GRK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_GRK` (
  `idGroupKind` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idGroupKind`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_PEGR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_PEGR` (
  `idPersonGroup` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `idPersonGroupParent` INT NULL,
  `idGroupKind` INT NOT NULL,
  PRIMARY KEY (`idPersonGroup`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC),
  INDEX `fk_UserGroup_UserGroup1_idx` (`idPersonGroupParent` ASC),
  INDEX `fk_Person_Group_Group_Kind1_idx` (`idGroupKind` ASC),
  CONSTRAINT `fk_UserGroup_UserGroup1`
    FOREIGN KEY (`idPersonGroupParent`)
    REFERENCES `komea`.`KOM_PEGR` (`idPersonGroup`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_Group_Group_Kind1`
    FOREIGN KEY (`idGroupKind`)
    REFERENCES `komea`.`KOM_GRK` (`idGroupKind`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_PERO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_PERO` (
  `idPersonRole` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idPersonRole`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_PE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_PE` (
  `idPerson` INT NOT NULL AUTO_INCREMENT,
  `idPersonGroup` INT NULL,
  `idPersonRole` INT NOT NULL,
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `login` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idPerson`),
  INDEX `fk_User_UserGroup1_idx` (`idPersonGroup` ASC),
  INDEX `fk_User_UserRole1_idx` (`idPersonRole` ASC),
  UNIQUE INDEX `Personcol_UNIQUE` (`login` ASC),
  CONSTRAINT `fk_User_UserGroup1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `komea`.`KOM_PEGR` (`idPersonGroup`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_UserRole1`
    FOREIGN KEY (`idPersonRole`)
    REFERENCES `komea`.`KOM_PERO` (`idPersonRole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_PVD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_PVD` (
  `idProvider` INT NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `icon` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idProvider`),
  UNIQUE INDEX `name_UNIQUE` (`key` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_KPI`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_KPI` (
  `idKpi` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `idProvider` INT NOT NULL,
  `minValue` DOUBLE NULL,
  `maxValue` DOUBLE NULL,
  `biggerBetter` INT NOT NULL,
  `valueType` INT NOT NULL,
  `entityType` INT NOT NULL,
  `esperRequest` TEXT NULL,
  PRIMARY KEY (`idKpi`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC),
  INDEX `fk_Metric_Plugin1_idx` (`idProvider` ASC),
  CONSTRAINT `fk_Metric_Plugin1`
    FOREIGN KEY (`idProvider`)
    REFERENCES `komea`.`KOM_PVD` (`idProvider`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_MSR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_MSR` (
  `idMeasure` INT NOT NULL AUTO_INCREMENT,
  `idMetric` INT NOT NULL,
  `date` DATE NOT NULL,
  `idPersonGroup` INT NULL,
  `idPerson` INT NULL,
  `idProject` INT NULL,
  `value` DOUBLE NOT NULL,
  PRIMARY KEY (`idMeasure`),
  INDEX `fk_Measure_Metric1_idx` (`idMetric` ASC),
  INDEX `fk_Measure_UserGroup1_idx` (`idPersonGroup` ASC),
  INDEX `fk_Measure_User1_idx` (`idPerson` ASC),
  INDEX `fk_Measure_Project1_idx` (`idProject` ASC),
  CONSTRAINT `fk_Measure_Metric1`
    FOREIGN KEY (`idMetric`)
    REFERENCES `komea`.`KOM_KPI` (`idKpi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measure_UserGroup1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `komea`.`KOM_PEGR` (`idPersonGroup`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measure_User1`
    FOREIGN KEY (`idPerson`)
    REFERENCES `komea`.`KOM_PE` (`idPerson`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measure_Project1`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`KOM_PROJ` (`idProject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_KPIA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_KPIA` (
  `idKpiAlertType` INT NOT NULL AUTO_INCREMENT,
  `idKpi` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `severity` INT NOT NULL,
  `value` DOUBLE NOT NULL,
  `averageSince` DATE NULL,
  `operator` INT NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`idKpiAlertType`),
  INDEX `fk_MetricAlert_Metric1_idx` (`idKpi` ASC),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC),
  CONSTRAINT `fk_MetricAlert_Metric1`
    FOREIGN KEY (`idKpi`)
    REFERENCES `komea`.`KOM_KPI` (`idKpi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_LINK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_LINK` (
  `idLink` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `idProject` INT NOT NULL,
  PRIMARY KEY (`idLink`),
  INDEX `fk_Tag_Project_idx` (`idProject` ASC),
  CONSTRAINT `fk_Tag_Project`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`KOM_PROJ` (`idProject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_EVT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_EVT` (
  `idEventType` INT NOT NULL,
  `idProvider` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `severity` INT NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  `description` TEXT NULL,
  `category` VARCHAR(255) NOT NULL,
  `entityType` INT NOT NULL,
  PRIMARY KEY (`idEventType`),
  INDEX `fk_Event_Plugin1_idx` (`idProvider` ASC),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC),
  CONSTRAINT `fk_Event_Plugin1`
    FOREIGN KEY (`idProvider`)
    REFERENCES `komea`.`KOM_PVD` (`idProvider`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_SETTING`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_SETTING` (
  `idSetting` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idSetting`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_TAG` (
  `idTag` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idTag`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_HAS_PROJ_PE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_HAS_PROJ_PE` (
  `idProject` INT NOT NULL,
  `idPerson` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idPerson`),
  INDEX `fk_Project_has_User_User1_idx` (`idPerson` ASC),
  INDEX `fk_Project_has_User_Project1_idx` (`idProject` ASC),
  CONSTRAINT `fk_Project_has_User_Project1`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`KOM_PROJ` (`idProject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_User_User1`
    FOREIGN KEY (`idPerson`)
    REFERENCES `komea`.`KOM_PE` (`idPerson`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_PVDS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_PVDS` (
  `idProviderSetting` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  `idProvider` INT NOT NULL,
  PRIMARY KEY (`idProviderSetting`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC),
  INDEX `fk_ProviderSetting_Provider1_idx` (`idProvider` ASC),
  CONSTRAINT `fk_ProviderSetting_Provider1`
    FOREIGN KEY (`idProvider`)
    REFERENCES `komea`.`KOM_PVD` (`idProvider`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_HAS_PROJ_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_HAS_PROJ_TAG` (
  `idProject` INT NOT NULL,
  `idTag` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idTag`),
  INDEX `fk_Project_has_Tag_Tag1_idx` (`idTag` ASC),
  INDEX `fk_Project_has_Tag_Project1_idx` (`idProject` ASC),
  CONSTRAINT `fk_Project_has_Tag_Project1`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`KOM_PROJ` (`idProject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_Tag_Tag1`
    FOREIGN KEY (`idTag`)
    REFERENCES `komea`.`KOM_TAG` (`idTag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_HAS_PROJ_KPIA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_HAS_PROJ_KPIA` (
  `iidProject` INT NOT NULL,
  `idKpiAlertType` INT NOT NULL,
  PRIMARY KEY (`iidProject`, `idKpiAlertType`),
  INDEX `fk_KOM_PROJ_has_KOM_KPIA_KOM_KPIA1_idx` (`idKpiAlertType` ASC),
  INDEX `fk_KOM_PROJ_has_KOM_KPIA_KOM_PROJ1_idx` (`iidProject` ASC),
  CONSTRAINT `fk_KOM_PROJ_has_KOM_KPIA_KOM_PROJ1`
    FOREIGN KEY (`iidProject`)
    REFERENCES `komea`.`KOM_PROJ` (`idProject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_KOM_PROJ_has_KOM_KPIA_KOM_KPIA1`
    FOREIGN KEY (`idKpiAlertType`)
    REFERENCES `komea`.`KOM_KPIA` (`idKpiAlertType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_HAS_KPIA_PEGR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_HAS_KPIA_PEGR` (
  `idKpiAlertType` INT NOT NULL,
  `idPersonGroup` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPersonGroup`),
  INDEX `fk_KOM_KPIA_has_KOM_PEGR_KOM_PEGR1_idx` (`idPersonGroup` ASC),
  INDEX `fk_KOM_KPIA_has_KOM_PEGR_KOM_KPIA1_idx` (`idKpiAlertType` ASC),
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PEGR_KOM_KPIA1`
    FOREIGN KEY (`idKpiAlertType`)
    REFERENCES `komea`.`KOM_KPIA` (`idKpiAlertType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PEGR_KOM_PEGR1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `komea`.`KOM_PEGR` (`idPersonGroup`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`KOM_HAS_KPIA_PE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`KOM_HAS_KPIA_PE` (
  `idKpiAlertType` INT NOT NULL,
  `idPerson` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPerson`),
  INDEX `fk_KOM_KPIA_has_KOM_PE_KOM_PE1_idx` (`idPerson` ASC),
  INDEX `fk_KOM_KPIA_has_KOM_PE_KOM_KPIA1_idx` (`idKpiAlertType` ASC),
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PE_KOM_KPIA1`
    FOREIGN KEY (`idKpiAlertType`)
    REFERENCES `komea`.`KOM_KPIA` (`idKpiAlertType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PE_KOM_PE1`
    FOREIGN KEY (`idPerson`)
    REFERENCES `komea`.`KOM_PE` (`idPerson`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;