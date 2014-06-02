
CREATE UNIQUE INDEX IF NOT EXISTS `key_UNIQUE1` ON `komea`.`kom_proj` (`projectKey` ASC) ;

CREATE INDEX IF NOT EXISTS `fk_Project_Customer1_idx` ON `komea`.`kom_proj` (`idCustomer` ASC) ;


CREATE UNIQUE INDEX IF NOT EXISTS `key_UNIQUE2` ON `komea`.`kom_pegr` (`personGroupKey` ASC) ;

CREATE INDEX IF NOT EXISTS `fk_UserGroup_UserGroup1_idx` ON `komea`.`kom_pegr` (`idPersonGroupParent` ASC) ;


CREATE INDEX IF NOT EXISTS `fk_User_UserGroup1_idx` ON `komea`.`kom_pe` (`idPersonGroup` ASC) ;

CREATE UNIQUE INDEX IF NOT EXISTS `Personcol_UNIQUE` ON `komea`.`kom_pe` (`login` ASC) ;

CREATE INDEX IF NOT EXISTS `fk_kom_pe_kom_pero1_idx` ON `komea`.`kom_pe` (`idPersonRole` ASC) ;

CREATE INDEX IF NOT EXISTS `person-email-index` ON `komea`.`kom_pe` (`email` ASC) ;



CREATE UNIQUE INDEX IF NOT EXISTS `key_UNIQUE3` ON `komea`.`kom_kpi` (`kpiKey` ASC) ;



CREATE INDEX IF NOT EXISTS `fk_Measure_Project1_idx` ON `komea`.`kom_msr` (`entityID` ASC) ;

CREATE INDEX IF NOT EXISTS `dateIndex` ON `komea`.`kom_msr` (`date` ASC) ;

CREATE INDEX IF NOT EXISTS `measure-complex-index` ON `komea`.`kom_msr` (`year` ASC, `idKpi` ASC, `month` ASC, `week` ASC, `day` ASC, `hour` ASC, `entityID` ASC) ;


CREATE INDEX IF NOT EXISTS `fk_MetricAlert_Metric1_idx` ON `komea`.`kom_kpia` (`idKpi` ASC) ;

CREATE UNIQUE INDEX IF NOT EXISTS `key_UNIQUE4` ON `komea`.`kom_kpia` (`kpiAlertKey` ASC) ;


CREATE UNIQUE INDEX IF NOT EXISTS `url_UNIQUE` ON `komea`.`kom_pvd` (`url` ASC) ;


CREATE INDEX IF NOT EXISTS `fk_Tag_Project_idx` ON `komea`.`kom_link` (`idProject` ASC) ;


CREATE UNIQUE INDEX IF NOT EXISTS `key_UNIQUE5` ON `komea`.`kom_evt` (`eventKey` ASC) ;


CREATE UNIQUE INDEX IF NOT EXISTS `key_UNIQUE6` ON `komea`.`kom_setting` (`settingKey` ASC) ;


CREATE INDEX IF NOT EXISTS `fk_Project_has_User_User1_idx` ON `komea`.`kom_has_proj_pe` (`idPerson` ASC) ;

CREATE INDEX IF NOT EXISTS `fk_Project_has_User_Project1_idx` ON `komea`.`kom_has_proj_pe` (`idProject` ASC) ;



CREATE UNIQUE INDEX IF NOT EXISTS `key_UNIQUE7` ON `komea`.`kom_pvds` (`providerSettingKey` ASC) ;

CREATE INDEX IF NOT EXISTS `fk_ProviderSetting_Provider1_idx` ON `komea`.`kom_pvds` (`idProvider` ASC) ;


CREATE INDEX IF NOT EXISTS `fk_Project_has_Tag_Tag1_idx` ON `komea`.`kom_has_proj_tag` (`idTag` ASC) ;

CREATE INDEX IF NOT EXISTS `fk_Project_has_Tag_Project1_idx` ON `komea`.`kom_has_proj_tag` (`idProject` ASC) ;



CREATE INDEX IF NOT EXISTS `fk_kom_proj_has_kom_pegr_kom_pegr1_idx` ON `komea`.`kom_has_proj_pegr` (`idPersonGroup` ASC) ;

CREATE INDEX IF NOT EXISTS `fk_kom_proj_has_kom_pegr_kom_proj1_idx` ON `komea`.`kom_has_proj_pegr` (`idProject` ASC) ;


CREATE UNIQUE INDEX IF NOT EXISTS `id_UNIQUE` ON `komea`.`kom_acfi` (`id` ASC) ;



CREATE UNIQUE INDEX IF NOT EXISTS `id_UNIQUE` ON `komea`.`kom_sfac` (`id` ASC) ;

CREATE UNIQUE INDEX IF NOT EXISTS `successFactoryKey_UNIQUE` ON `komea`.`kom_sfac` (`successFactorKey` ASC) ;


CREATE INDEX IF NOT EXISTS `fk_kom_sfac_has_kom_kpi_kom_kpi1_idx` ON `komea`.`kom_has_sfac_kpi` (`idKpi` ASC) ;

CREATE INDEX IF NOT EXISTS `fk_kom_sfac_has_kom_kpi_kom_sfac1_idx` ON `komea`.`kom_has_sfac_kpi` (`idSuccessFactor` ASC) ;



