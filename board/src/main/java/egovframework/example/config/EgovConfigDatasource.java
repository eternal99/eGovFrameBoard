package egovframework.example.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class EgovConfigDatasource {

	 @Bean
	 @Primary
	 @ConfigurationProperties(prefix = "spring.datasource")
	    public DataSource dataSource() {
	        return new DriverManagerDataSource();
	    }	

}
