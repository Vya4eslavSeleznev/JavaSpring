package main;

import main.security.jwt.JwtSecurityConfigurer;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception
  {
    return super.authenticationManagerBean();
  }

  @Bean
  PasswordEncoder getEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http.httpBasic().disable()
      .csrf().disable()
      .formLogin().disable()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
      .antMatchers("/auth/signin").permitAll()
      //.antMatchers(HttpMethod.GET, "/bt/application{id}").hasRole("USER")
      //.antMatchers(HttpMethod.POST, "/bt/addApplication").hasRole("ADMIN")
      //.antMatchers(HttpMethod.GET, "/bt/applications").permitAll()
      .anyRequest().authenticated()
      .and()
      .apply(new JwtSecurityConfigurer(jwtTokenProvider));
  }

  @Autowired
  private JwtTokenProvider jwtTokenProvider;
}
