


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
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Project_Customer1`
    FOREIGN KEY (`idCustomer`)
    REFERENCES `komea`.`kom_customer` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_grk`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_grk` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
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
  `idGroupKind` INT NULL,
  `depth` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_UserGroup_UserGroup1`
    FOREIGN KEY (`idPersonGroupParent`)
    REFERENCES `komea`.`kom_pegr` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_Group_Group_Kind1`
    FOREIGN KEY (`idGroupKind`)
    REFERENCES `komea`.`kom_grk` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_pero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pero` (
  `id` INT NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_User_UserGroup1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `komea`.`kom_pegr` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_UserRole1`
    FOREIGN KEY (`idPersonRole`)
    REFERENCES `komea`.`kom_pero` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_pvd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pvd` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `providerKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `icon` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `komea`.`kom_kpi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_kpi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `kpiKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  `idProvider` INT NOT NULL,
  `minValue` DOUBLE NULL,
  `maxValue` DOUBLE NULL,
  `valueDirection` INT NOT NULL,
  `valueType` INT NOT NULL,
  `entityType` INT NOT NULL,
  `esperRequest` TEXT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Metric_Plugin1`
    FOREIGN KEY (`idProvider`)
    REFERENCES `komea`.`kom_pvd` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


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
  CONSTRAINT `fk_Measure_Metric1`
    FOREIGN KEY (`idKpi`)
    REFERENCES `komea`.`kom_kpi` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measure_UserGroup1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `komea`.`kom_pegr` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measure_User1`
    FOREIGN KEY (`idPerson`)
    REFERENCES `komea`.`kom_pe` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measure_Project1`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`kom_proj` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
  `severity` INT NOT NULL,
  `value` DOUBLE NOT NULL,
  `averageSince` DATE NULL,
  `operator` INT NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_MetricAlert_Metric1`
    FOREIGN KEY (`idKpi`)
    REFERENCES `komea`.`kom_kpi` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_link` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `idProject` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Tag_Project`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`kom_proj` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_evt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_evt` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idProvider` INT NOT NULL,
  `eventKey` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `severity` INT NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  `description` VARCHAR(2048) NULL,
  `category` VARCHAR(255) NOT NULL,
  `entityType` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Event_Plugin1`
    FOREIGN KEY (`idProvider`)
    REFERENCES `komea`.`kom_pvd` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_setting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `settingKey` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
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
  PRIMARY KEY (`idProject`, `idPerson`),
  CONSTRAINT `fk_Project_has_User_Project1`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`kom_proj` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_User_User1`
    FOREIGN KEY (`idPerson`)
    REFERENCES `komea`.`kom_pe` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_pvds`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_pvds` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `providerSettingKey` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  `idProvider` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_ProviderSetting_Provider1`
    FOREIGN KEY (`idProvider`)
    REFERENCES `komea`.`kom_pvd` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_tag` (
  `idProject` INT NOT NULL,
  `idTag` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idTag`),
  CONSTRAINT `fk_Project_has_Tag_Project1`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`kom_proj` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_Tag_Tag1`
    FOREIGN KEY (`idTag`)
    REFERENCES `komea`.`kom_tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_kpia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_kpia` (
  `idProject` INT NOT NULL,
  `idKpiAlertType` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idKpiAlertType`),
  CONSTRAINT `fk_KOM_PROJ_has_KOM_KPIA_KOM_PROJ1`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`kom_proj` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_KOM_PROJ_has_KOM_KPIA_KOM_KPIA1`
    FOREIGN KEY (`idKpiAlertType`)
    REFERENCES `komea`.`kom_kpia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_kpia_pegr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_kpia_pegr` (
  `idKpiAlertType` INT NOT NULL,
  `idPersonGroup` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPersonGroup`),
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PEGR_KOM_KPIA1`
    FOREIGN KEY (`idKpiAlertType`)
    REFERENCES `komea`.`kom_kpia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PEGR_KOM_PEGR1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `komea`.`kom_pegr` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_kpia_pe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_kpia_pe` (
  `idKpiAlertType` INT NOT NULL,
  `idPerson` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPerson`),
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PE_KOM_KPIA1`
    FOREIGN KEY (`idKpiAlertType`)
    REFERENCES `komea`.`kom_kpia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PE_KOM_PE1`
    FOREIGN KEY (`idPerson`)
    REFERENCES `komea`.`kom_pe` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `komea`.`kom_has_proj_pegr`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `komea`.`kom_has_proj_pegr` (
  `idProject` INT NOT NULL,
  `idPersonGroup` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idPersonGroup`),
  CONSTRAINT `fk_kom_proj_has_kom_pegr_kom_proj1`
    FOREIGN KEY (`idProject`)
    REFERENCES `komea`.`kom_proj` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_kom_proj_has_kom_pegr_kom_pegr1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `komea`.`kom_pegr` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

