package org.komea.product.eventory;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@MapperScan(basePackages = "org.komea.product.eventory.database.dao")
public class MyBatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(final DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setConfigLocation(new ClassPathResource(
				"database/mybatis-conf.xml"));
		return sessionFactory.getObject();
	}

}
