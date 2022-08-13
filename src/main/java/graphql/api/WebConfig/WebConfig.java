package graphql.api.WebConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import graphql.api.JwtFilter.JwtFilter;
import graphql.api.Services.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig {
 @Autowired
 UserDetailsServiceImp userDetailsServiceImp;
 @Autowired
 private JwtFilter jwtFilter;
 @Bean
   public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder(11);
    }
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
  }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder
    .userDetailsService(userDetailsServiceImp)
    .passwordEncoder(passwordEncoder());

    AuthenticationManager authenticationManager
    = authenticationManagerBuilder. build();

    http
    .cors()
    .and()
    .csrf()
    .disable();
    http
        .authorizeRequests()
        
        .antMatchers("/graphql","/graphiql").permitAll()
        .anyRequest().permitAll()
        
        .and()
        .authenticationManager(authenticationManager)
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
  }
}
//.authenticationManager(authenticationManager)