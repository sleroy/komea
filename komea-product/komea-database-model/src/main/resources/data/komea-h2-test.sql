SET MODE MySQL;



-- -----------------------------------------------------
-- Table `KOM_CUSTOMER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_CUSTOMER` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_PROJ`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_PROJ` (
  `id` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `idCustomer` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Project_Customer1`
    FOREIGN KEY (`idCustomer`)
    REFERENCES `KOM_CUSTOMER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_GRK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_GRK` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_PEGR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_PEGR` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `idPersonGroupParent` INT NULL,
  `idGroupKind` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_UserGroup_UserGroup1`
    FOREIGN KEY (`idPersonGroupParent`)
    REFERENCES `KOM_PEGR` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_Group_Group_Kind1`
    FOREIGN KEY (`idGroupKind`)
    REFERENCES `KOM_GRK` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_PERO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_PERO` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_PE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_PE` (
  `id` INT NOT NULL,
  `idPersonGroup` INT NULL,
  `idPersonRole` INT NOT NULL,
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `login` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_User_UserGroup1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `KOM_PEGR` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_UserRole1`
    FOREIGN KEY (`idPersonRole`)
    REFERENCES `KOM_PERO` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_PVD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_PVD` (
  `id` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `icon` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_KPI`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_KPI` (
  `id` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
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
    REFERENCES `KOM_PVD` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_MSR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_MSR` (
  `id` INT NOT NULL,
  `idMetric` INT NOT NULL,
  `date` DATE NOT NULL,
  `idPersonGroup` INT NULL,
  `idPerson` INT NULL,
  `idProject` INT NULL,
  `value` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Measure_Metric1`
    FOREIGN KEY (`idMetric`)
    REFERENCES `KOM_KPI` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measure_UserGroup1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `KOM_PEGR` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measure_User1`
    FOREIGN KEY (`idPerson`)
    REFERENCES `KOM_PE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Measure_Project1`
    FOREIGN KEY (`idProject`)
    REFERENCES `KOM_PROJ` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_KPIA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_KPIA` (
  `id` INT NOT NULL,
  `idKpi` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT NULL,
  `severity` INT NOT NULL,
  `value` DOUBLE NOT NULL,
  `averageSince` DATE NULL,
  `operator` INT NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_MetricAlert_Metric1`
    FOREIGN KEY (`idKpi`)
    REFERENCES `KOM_KPI` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_LINK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_LINK` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `idProject` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Tag_Project`
    FOREIGN KEY (`idProject`)
    REFERENCES `KOM_PROJ` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_EVT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_EVT` (
  `id` INT NOT NULL,
  `idProvider` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `severity` INT NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  `description` TEXT NULL,
  `category` VARCHAR(255) NOT NULL,
  `entityType` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Event_Plugin1`
    FOREIGN KEY (`idProvider`)
    REFERENCES `KOM_PVD` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_SETTING`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_SETTING` (
  `id` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_TAG` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_HAS_PROJ_PE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_HAS_PROJ_PE` (
  `idProject` INT NOT NULL,
  `idPerson` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idPerson`),
  CONSTRAINT `fk_Project_has_User_Project1`
    FOREIGN KEY (`idProject`)
    REFERENCES `KOM_PROJ` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_User_User1`
    FOREIGN KEY (`idPerson`)
    REFERENCES `KOM_PE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_PVDS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_PVDS` (
  `id` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  `idProvider` INT NOT NULL,
  CONSTRAINT `fk_ProviderSetting_Provider1`
    FOREIGN KEY (`idProvider`)
    REFERENCES `KOM_PVD` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_HAS_PROJ_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_HAS_PROJ_TAG` (
  `idProject` INT NOT NULL,
  `idTag` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idTag`),
  CONSTRAINT `fk_Project_has_Tag_Project1`
    FOREIGN KEY (`idProject`)
    REFERENCES `KOM_PROJ` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_Tag_Tag1`
    FOREIGN KEY (`idTag`)
    REFERENCES `KOM_TAG` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_HAS_PROJ_KPIA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_HAS_PROJ_KPIA` (
  `idProject` INT NOT NULL,
  `idKpiAlertType` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idKpiAlertType`),
  CONSTRAINT `fk_KOM_PROJ_has_KOM_KPIA_KOM_PROJ1`
    FOREIGN KEY (`idProject`)
    REFERENCES `KOM_PROJ` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_KOM_PROJ_has_KOM_KPIA_KOM_KPIA1`
    FOREIGN KEY (`idKpiAlertType`)
    REFERENCES `KOM_KPIA` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_HAS_KPIA_PEGR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_HAS_KPIA_PEGR` (
  `idKpiAlertType` INT NOT NULL,
  `idPersonGroup` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPersonGroup`),
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PEGR_KOM_KPIA1`
    FOREIGN KEY (`idKpiAlertType`)
    REFERENCES `KOM_KPIA` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PEGR_KOM_PEGR1`
    FOREIGN KEY (`idPersonGroup`)
    REFERENCES `KOM_PEGR` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `KOM_HAS_KPIA_PE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_HAS_KPIA_PE` (
  `idKpiAlertType` INT NOT NULL,
  `idPerson` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPerson`),
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PE_KOM_KPIA1`
    FOREIGN KEY (`idKpiAlertType`)
    REFERENCES `KOM_KPIA` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_KOM_KPIA_has_KOM_PE_KOM_PE1`
    FOREIGN KEY (`idPerson`)
    REFERENCES `KOM_PE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


