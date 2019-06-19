package it.uniroma3.siw.progetto.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment environment;
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//serve per definire CHI può accedere e a QUALI pagine
			.authorizeRequests()
				//tutti possono accedere alla home page
				.antMatchers(HttpMethod.GET, "/home").permitAll()
				//solo chi è admin può accedere(da modificare)
				.antMatchers(HttpMethod.GET, "/daModificare").hasAnyAuthority("ADMIN")
			//definisce come si fa il login
			.and().formLogin()
				//dopo il login avvenuto con successo, va alla pagina di benvenuto(da modificare)
				.defaultSuccessUrl("/daModificare)")
			//definisco la parte logout
			.and().logout()
				//mando una get alla pagina di logout
				.logoutUrl("/logout")
				//se il logout avviene con successo, torna alla home
				.logoutSuccessUrl("/home");
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.buildDatasource())
			.authoritiesByUsernameQuery("SELECT username, role from funzionario WHERE username=?)")
			.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM funzionario WHERE username=?");
	}
	@Bean
	DataSource buildDatasource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(environment.getProperty("spring.datasource.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.password"));
		return dataSource;
	}
}