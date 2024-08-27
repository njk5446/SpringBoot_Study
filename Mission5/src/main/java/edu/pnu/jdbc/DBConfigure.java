package edu.pnu.jdbc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfigure {

	@Bean
	@ConditionalOnMissingBean
	DBManager getDBManager() {
		DBManager manager = new DBManager();
		manager.setUrl("jdbc:mysql://localhost:3306/musthave");
		manager.setUser("scott");
		manager.setPass("tiger");
		return manager;
	}
}
