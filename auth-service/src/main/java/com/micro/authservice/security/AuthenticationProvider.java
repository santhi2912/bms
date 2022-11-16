package com.micro.authservice.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.micro.authconfigservice.security.JwtConfig;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

 @Autowired
 CustomerService customerService;

 @Override
 protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
  //
 }

 @Override
 protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

  Object token = usernamePasswordAuthenticationToken.getCredentials();
  return Optional
   .ofNullable(token)
   .map(String::valueOf)
   .flatMap(customerService::findByToken)
   .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
 }