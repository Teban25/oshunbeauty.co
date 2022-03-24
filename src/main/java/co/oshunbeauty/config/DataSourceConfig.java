package co.oshunbeauty.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	@Value("${spring.datasource.url}")
	private String url2;
	
	@Value("${spring.datasource.username}")
	private String userName2;
	
	@Value("${spring.datasource.password}")
	private String password2;
	
	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.postgresql.Driver")
				.url(url2)
				.username(userName2)
				.password(password2);
		
		return dataSourceBuilder.build();
	}
}
