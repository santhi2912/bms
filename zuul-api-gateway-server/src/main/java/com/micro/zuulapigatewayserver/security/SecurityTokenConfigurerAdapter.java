package com.micro.zuulapigatewayserver.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.micro.authconfigservice.security.JwtConfig;
import com.micro.authconfigservice.security.JwtTokenAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


 private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
  new AntPathRequestMatcher("/api/**")
 );

 AuthenticationProvider provider;

 public SecurityConfiguration(final AuthenticationProvider authenticationProvider) {
  super();
  this.provider = authenticationProvider;
 }

 @Override
 protected void configure(final AuthenticationManagerBuilder auth) {
  auth.authenticationProvider(provider);
 }

 @Override
 public void configure(final WebSecurity webSecurity) {
  webSecurity.ignoring().antMatchers("/token/**");
 }

 @Override
 public void configure(HttpSecurity http) throws Exception {
  http.sessionManagement()
   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
   .and()
   .exceptionHandling()
   .and()
   .authenticationProvider(provider)
   .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
   .authorizeRequests()
   .requestMatchers(PROTECTED_URLS)
   .authenticated()
   .and()
   .csrf().disable()
   .formLogin().disable()
   .httpBasic().disable()
   .logout().disable();
 }

 @Bean
 AuthenticationFilter authenticationFilter() throws Exception {
  final AuthenticationFilter filter = new AuthenticationFilter(PROTECTED_URLS);
  filter.setAuthenticationManager(authenticationManager());
  //filter.setAuthenticationSuccessHandler(successHandler());
  return filter;
 }

 @Bean
 AuthenticationEntryPoint forbiddenEntryPoint() {
  return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
 }
}