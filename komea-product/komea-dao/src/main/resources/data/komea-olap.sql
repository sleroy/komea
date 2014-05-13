
CREATE SCHEMA IF NOT EXISTS `komea` DEFAULT CHARACTER SET utf8 ;
USE `komea` ;

-- -----------------------------------------------------
-- Table `komea`.`kom_customer`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_customer` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_proj`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_proj` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `projectKey` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(2048) NULL ,
  `idCustomer` INT NULL ,
  `icon` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `key_UNIQUE` ON `komea`.`kom_proj` (`projectKey` ASC) ;

CREATE INDEX `fk_Project_Customer1_idx` ON `komea`.`kom_proj` (`idCustomer` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_pegr`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_pegr` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `personGroupKey` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(2048) NULL ,
  `idPersonGroupParent` INT NULL ,
  `type` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `key_UNIQUE` ON `komea`.`kom_pegr` (`personGroupKey` ASC) ;

CREATE INDEX `fk_UserGroup_UserGroup1_idx` ON `komea`.`kom_pegr` (`idPersonGroupParent` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_pero`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_pero` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `roleKey` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `roleKey_UNIQUE` ON `komea`.`kom_pero` (`roleKey` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_pe`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_pe` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `idPersonGroup` INT NULL ,
  `idPersonRole` INT NULL ,
  `firstName` VARCHAR(255) NOT NULL ,
  `lastName` VARCHAR(255) NOT NULL ,
  `email` VARCHAR(255) NOT NULL ,
  `login` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(255) NOT NULL DEFAULT '' ,
  `userBdd` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE INDEX `fk_User_UserGroup1_idx` ON `komea`.`kom_pe` (`idPersonGroup` ASC) ;

CREATE UNIQUE INDEX `Personcol_UNIQUE` ON `komea`.`kom_pe` (`login` ASC) ;

CREATE INDEX `fk_kom_pe_kom_pero1_idx` ON `komea`.`kom_pe` (`idPersonRole` ASC) ;

CREATE INDEX `person-email-index` ON `komea`.`kom_pe` (`email` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_kpi`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_kpi` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `kpiKey` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(2048) NOT NULL ,
  `valueMin` DOUBLE NULL ,
  `valueMax` DOUBLE NULL ,
  `valueDirection` VARCHAR(255) NOT NULL ,
  `valueType` VARCHAR(255) NOT NULL ,
  `entityType` VARCHAR(255) NOT NULL ,
  `esperRequest` MEDIUMTEXT NOT NULL ,
  `cronExpression` VARCHAR(60) NOT NULL ,
  `providerType` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `key_UNIQUE` ON `komea`.`kom_kpi` (`kpiKey` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_msr`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_msr` (
  `id` INT NOT NULL ,
  `idKpi` INT NOT NULL ,
  `year` INT NOT NULL ,
  `month` INT NOT NULL ,
  `week` INT NOT NULL ,
  `day` INT NOT NULL ,
  `hour` INT NOT NULL ,
  `entityID` INT NOT NULL ,
  `value` DOUBLE NOT NULL ,
  `date` TIMESTAMP NOT NULL ,
  `sprint` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE INDEX `fk_Measure_Metric1_idx` ON `komea`.`kom_msr` (`idKpi` ASC) ;

CREATE INDEX `fk_Measure_Project1_idx` ON `komea`.`kom_msr` (`entityID` ASC) ;

CREATE INDEX `dateIndex` ON `komea`.`kom_msr` (`date` ASC) ;

CREATE INDEX `measure-complex-index` ON `komea`.`kom_msr` (`year` ASC, `idKpi` ASC, `month` ASC, `week` ASC, `day` ASC, `hour` ASC, `entityID` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_kpia`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_kpia` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `idKpi` INT NOT NULL ,
  `kpiAlertKey` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(2048) NULL ,
  `severity` VARCHAR(255) NOT NULL ,
  `value` DOUBLE NOT NULL ,
  `averageSince` DATE NULL ,
  `operator` VARCHAR(255) NOT NULL ,
  `enabled` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE INDEX `fk_MetricAlert_Metric1_idx` ON `komea`.`kom_kpia` (`idKpi` ASC) ;

CREATE UNIQUE INDEX `key_UNIQUE` ON `komea`.`kom_kpia` (`kpiAlertKey` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_pvd`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_pvd` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `providerType` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `icon` VARCHAR(255) NOT NULL ,
  `url` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(2048) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `url_UNIQUE` ON `komea`.`kom_pvd` (`url` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_link`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_link` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `url` VARCHAR(255) NOT NULL ,
  `idProject` INT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE INDEX `fk_Tag_Project_idx` ON `komea`.`kom_link` (`idProject` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_evt`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_evt` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `eventKey` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `severity` VARCHAR(255) NOT NULL ,
  `enabled` TINYINT(1) NOT NULL ,
  `description` VARCHAR(2048) NULL ,
  `entityType` VARCHAR(255) NULL ,
  `providerType` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `key_UNIQUE` ON `komea`.`kom_evt` (`eventKey` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_setting`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_setting` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `settingKey` VARCHAR(255) NOT NULL ,
  `value` VARCHAR(255) NOT NULL ,
  `type` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(2048) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `key_UNIQUE` ON `komea`.`kom_setting` (`settingKey` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_tag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_tag` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_pe`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_has_proj_pe` (
  `idProject` INT NOT NULL ,
  `idPerson` INT NOT NULL ,
  PRIMARY KEY (`idProject`, `idPerson`) )
ENGINE = InnoDB;

CREATE INDEX `fk_Project_has_User_User1_idx` ON `komea`.`kom_has_proj_pe` (`idPerson` ASC) ;

CREATE INDEX `fk_Project_has_User_Project1_idx` ON `komea`.`kom_has_proj_pe` (`idProject` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_pvds`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_pvds` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `providerSettingKey` VARCHAR(255) NOT NULL ,
  `value` VARCHAR(255) NOT NULL ,
  `idProvider` INT NOT NULL ,
  `type` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(2048) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `key_UNIQUE` ON `komea`.`kom_pvds` (`providerSettingKey` ASC) ;

CREATE INDEX `fk_ProviderSetting_Provider1_idx` ON `komea`.`kom_pvds` (`idProvider` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_tag`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_has_proj_tag` (
  `idProject` INT NOT NULL ,
  `idTag` INT NOT NULL ,
  PRIMARY KEY (`idProject`, `idTag`) )
ENGINE = InnoDB;

CREATE INDEX `fk_Project_has_Tag_Tag1_idx` ON `komea`.`kom_has_proj_tag` (`idTag` ASC) ;

CREATE INDEX `fk_Project_has_Tag_Project1_idx` ON `komea`.`kom_has_proj_tag` (`idProject` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_pegr`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_has_proj_pegr` (
  `idProject` INT NOT NULL ,
  `idPersonGroup` INT NOT NULL ,
  PRIMARY KEY (`idProject`, `idPersonGroup`) )
ENGINE = InnoDB;

CREATE INDEX `fk_kom_proj_has_kom_pegr_kom_pegr1_idx` ON `komea`.`kom_has_proj_pegr` (`idPersonGroup` ASC) ;

CREATE INDEX `fk_kom_proj_has_kom_pegr_kom_proj1_idx` ON `komea`.`kom_has_proj_pegr` (`idProject` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_acfi`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_acfi` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `entityType` VARCHAR(255) NOT NULL ,
  `idEntity` INT NULL ,
  `infoRetention` VARCHAR(255) NOT NULL ,
  `minorRetention` VARCHAR(255) NOT NULL ,
  `majorRetention` VARCHAR(255) NOT NULL ,
  `criticalRetention` VARCHAR(255) NOT NULL ,
  `blockerRetention` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `komea`.`kom_acfi` (`id` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_sfac`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_sfac` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `successFactorKey` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(2048) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `komea`.`kom_sfac` (`id` ASC) ;

CREATE UNIQUE INDEX `successFactoryKey_UNIQUE` ON `komea`.`kom_sfac` (`successFactorKey` ASC) ;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_sfac_kpi`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `komea`.`kom_has_sfac_kpi` (
  `idSuccessFactor` INT NOT NULL ,
  `idKpi` INT NOT NULL ,
  PRIMARY KEY (`idSuccessFactor`, `idKpi`) )
ENGINE = InnoDB;

CREATE INDEX `fk_kom_sfac_has_kom_kpi_kom_kpi1_idx` ON `komea`.`kom_has_sfac_kpi` (`idKpi` ASC) ;

CREATE INDEX `fk_kom_sfac_has_kom_kpi_kom_sfac1_idx` ON `komea`.`kom_has_sfac_kpi` (`idSuccessFactor` ASC) ;

USE `komea` ;

