package io.muzoo.ssc.project.backend.config;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import io.muzoo.ssc.project.backend.auth.OurUserDetailService;
import io.muzoo.ssc.project.backend.util.AjaxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private OurUserDetailService ourUserDetailService;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// disable csrf, shouldn't do this but life is easier without it
		http.csrf().disable();
		// permit root and /api/login and /api/logout
		http.authorizeRequests()
				.antMatchers("/", "api/login", "/api/logout")
				.permitAll();
		// permit all Options
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
		// handle error output as JSON unauthorized access
		http.exceptionHandling()
				.authenticationEntryPoint(new JsonHttp403ForbiddenEntryPoint());

		//set every other path to require authentication
		http.authorizeRequests().antMatchers("/**").authenticated();


	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	public UserDetailsService userDetailsService() {
		return ourUserDetailService;
	}

	class JsonHttp403ForbiddenEntryPoint implements AuthenticationEntryPoint{

		@Override
		public void commence(HttpServletRequest request,
							 HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
			// out JSON message
			String ajaxJson = AjaxUtils.convertToString(
					SimpleResponseDTO
							.builder()
							.success(true)
							.message("Forbidden")
							.build()
			);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().println(ajaxJson);
		}
	}
}
