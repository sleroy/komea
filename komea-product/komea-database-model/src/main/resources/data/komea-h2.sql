

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
  `description` VARCHAR(2048) NULL,
  `idCustomer` INT NULL,
  PRIMARY KEY (`id`))
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
  `description` VARCHAR(2048) NULL,
  `idPersonGroupParent` INT NULL,
  `idGroupKind` INT NULL,
  PRIMARY KEY (`id`))
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
  `idPersonRole` INT NULL,
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `login` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
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
  `description` VARCHAR(2048) NULL,
  `idProvider` INT NOT NULL,
  `minValue` DOUBLE NULL,
  `maxValue` DOUBLE NULL,
  `valueDirection` INT NOT NULL,
  `valueType` INT NOT NULL,
  `entityType` INT NOT NULL,
  `esperRequest` TEXT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_MSR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_MSR` (
  `id` INT NOT NULL,
  `idKpi` INT NOT NULL,
  `date` DATE NOT NULL,
  `idPersonGroup` INT NULL,
  `idPerson` INT NULL,
  `idProject` INT NULL,
  `value` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_KPIA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_KPIA` (
  `id` INT NOT NULL,
  `idKpi` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2048) NULL,
  `severity` INT NOT NULL,
  `value` DOUBLE NOT NULL,
  `averageSince` DATE NULL,
  `operator` INT NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_LINK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_LINK` (
  `id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `idProject` INT NOT NULL,
  PRIMARY KEY (`id`))
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
  `description` VARCHAR(2048) NULL,
  `category` VARCHAR(255) NOT NULL,
  `entityType` INT NOT NULL,
  PRIMARY KEY (`id`))
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
  PRIMARY KEY (`idProject`, `idPerson`))
;


-- -----------------------------------------------------
-- Table `KOM_PVDS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_PVDS` (
  `id` INT NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  `idProvider` INT NOT NULL,
  PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `KOM_HAS_PROJ_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_HAS_PROJ_TAG` (
  `idProject` INT NOT NULL,
  `idTag` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idTag`))
;


-- -----------------------------------------------------
-- Table `KOM_HAS_PROJ_KPIA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_HAS_PROJ_KPIA` (
  `idProject` INT NOT NULL,
  `idKpiAlertType` INT NOT NULL,
  PRIMARY KEY (`idProject`, `idKpiAlertType`))
;


-- -----------------------------------------------------
-- Table `KOM_HAS_KPIA_PEGR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_HAS_KPIA_PEGR` (
  `idKpiAlertType` INT NOT NULL,
  `idPersonGroup` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPersonGroup`))
;


-- -----------------------------------------------------
-- Table `KOM_HAS_KPIA_PE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `KOM_HAS_KPIA_PE` (
  `idKpiAlertType` INT NOT NULL,
  `idPerson` INT NOT NULL,
  PRIMARY KEY (`idKpiAlertType`, `idPerson`))
;

