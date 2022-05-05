package co.oshunbeauty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Value("${aut0.audience}")
	private String apiAudience;
	
	@Value("${auth0.issuer}")
	private String issuer;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JwtWebSecurityConfigurer
				.forRS256(apiAudience, issuer)
				.configure(http)
				.authorizeRequests()
				.antMatchers("**/rs/products/**").hasAuthority("search:products")
				.antMatchers("**/rs/sales/**").hasAuthority("add:sales")
				.antMatchers("**/rs/purchases/**").hasAuthority("add:purchases")
				.antMatchers("**/rs/brands/**").hasAuthority("crud:admin")
				.antMatchers("**/rs/categories/**").hasAuthority("crud:admin")
				.antMatchers("**/rs/customers/**").hasAuthority("crud:admin")
				.antMatchers("**/rs/keywords/**").hasAuthority("crud:admin")
				.antMatchers("**/rs/payments/**").hasAuthority("crud:admin")
				.antMatchers("**/rs/suppliers/**").hasAuthority("crud:admin")
				.anyRequest()
				.authenticated();
				
	}
}
