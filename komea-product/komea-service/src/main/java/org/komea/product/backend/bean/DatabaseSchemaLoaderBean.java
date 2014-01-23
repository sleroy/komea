/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.backend.bean;



import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;



/**
 * @author rgalerme
 */
@Repository
public class DatabaseSchemaLoaderBean
{
    
    
    @Value("${database_sqlfile}")
    private String        sqlDataFile;
    
    @Autowired
    private DataSource    dataSource;
    
    
    private static Logger LOGGER = LoggerFactory.getLogger(DatabaseSchemaLoaderBean.class);
    
    
    
    @PostConstruct
    public void init() throws SQLException {
    
    
        try {
            LOGGER.info("=======> CHECK DATABASE SCHEMA");
            final ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
            rdp.addScript(new ClassPathResource("/data/komea-h2.sql"));
            rdp.populate(dataSource.getConnection());
            LOGGER.info("<======= SCHEMA CHECKED");
        } catch (final Exception e) {
            LOGGER.error("Fail to populate the database.", e);
        }
    }
}
