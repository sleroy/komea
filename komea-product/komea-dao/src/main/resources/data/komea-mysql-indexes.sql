
call komea.createIndex( 'kom_proj','key_UNIQUE','projectKey ASC');
call komea.createIndex( 'kom_proj','fk_Project_Customer1_idx','projectKey ASC');



call komea.createIndex( 'kom_pegr','key_UNIQUE','personGroupKey ASC');
call komea.createIndex( 'kom_pegr','fk_UserGroup_UserGroup1_idx','idPersonGroupParent ASC');


call komea.createIndex('kom_pero','roleKey_UNIQUE','roleKey ASC');


call komea.createIndex( 'kom_pe','fk_User_UserGroup1_idx','idPersonGroup ASC');
call komea.createIndex( 'kom_pe','Personcol_UNIQUE','login ASC');
call komea.createIndex( 'kom_pe','fk_kom_pe_kom_pero1_idx','idPersonRole ASC');
call komea.createIndex( 'kom_pe','person_email_index','email ASC');


call komea.createIndex( 'kom_kpi','key_UNIQUE','kpiKey ASC');


call komea.createIndex( 'kom_msr','fk_Measure_Metric1_idx','idKpi ASC');
call komea.createIndex( 'kom_msr', 'fk_Measure_Metric1_idx','idKpi ASC');
call komea.createIndex( 'kom_msr','dateIndex', 'date ASC');
call komea.createIndex( 'kom_msr','dateIndex', 'year ASC, idKpi ASC, month ASC, week ASC, day ASC, hour ASC, entityID ASC');


call komea.createIndex( 'kom_kpia','fk_MetricAlert_Metric1_idx', 'idKpi ASC');
call komea.createIndex( 'kom_kpia','key_UNIQUE','kpiAlertKey ASC');



call komea.createIndex( 'kom_pvd','url_UNIQUE','url ASC');


call komea.createIndex( 'kom_link','fk_Tag_Project_idx','idProject ASC');


call komea.createIndex( 'kom_evt','key_UNIQUE','eventKey ASC');


call komea.createIndex( 'kom_setting','ey_UNIQUE','settingKey ASC');


call komea.createIndex( 'kom_has_proj_pe','fk_Project_has_User_User1_idx','idPerson ASC');
call komea.createIndex( 'kom_has_proj_pe','fk_Project_has_User_Project1_idx','idProject ASC');


call komea.createIndex( 'kom_pvds','key_UNIQUE','providerSettingKey ASC');
call komea.createIndex( 'kom_pvds','fk_ProviderSetting_Provider1_idx','idProvider ASC');


call komea.createIndex( 'kom_has_proj_tag','fk_Project_has_Tag_Tag1_idx','idTag ASC');
call komea.createIndex( 'kom_has_proj_tag', 'fk_Project_has_Tag_Project1_id','idProject ASC');


call komea.createIndex( 'kom_has_proj_pegr','fk_kom_proj_has_kom_pegr_kom_pegr1_idx','idPersonGroup ASC');
call komea.createIndex( 'kom_has_proj_pegr','fk_kom_proj_has_kom_pegr_kom_proj1_idx','idProject ASC');


call komea.createIndex( 'kom_acfi','key_UNIQUE','id ASC');


call komea.createIndex( 'kom_sfac','key_UNIQUE','id ASC');
call komea.createIndex( 'kom_sfac','successFactoryKey','successFactorKey ASC');


call komea.createIndex( 'kom_has_sfac_kpi','fk_kom_sfac_has_kom_kpi_kom_kpi1_idx','idKpi ASC');
call komea.createIndex( 'kom_has_sfac_kpi','fk_kom_sfac_has_kom_kpi_kom_sfac1_idx','idSuccessFactor ASC');

call komea.createIndex( 'kom_kpigoal','fk_kom_kpigoal_kom_kpi1_idx','idKpi ASC');


