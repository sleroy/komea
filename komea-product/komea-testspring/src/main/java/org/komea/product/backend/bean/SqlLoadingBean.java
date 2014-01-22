/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.backend.bean;

import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rgalerme
 */
@Repository
public class SqlLoadingBean {
    @Autowired
    private DataSource dataSource;
    
   @PostConstruct
    public void init() throws SQLException {
        final ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
        rdp.addScript(new ClassPathResource("/data/komea-h2.sql"));
        rdp.populate(dataSource.getConnection());
    }
}
