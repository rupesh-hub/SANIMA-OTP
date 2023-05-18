package com.infodev.sanimaotp.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@Order(1)
@EnableWebSecurity
public class ApiConfiguration extends WebSecurityConfigurerAdapter {

    private static String REALM = "SANIMA_OTP_REALM";

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        //username and password to be used while accessing every api in basic auth
        auth.inMemoryAuthentication().withUser("apiuser").password("$2a$10$8MQp54eLsTtK6DROPsuDzeJWP4IvlbcUf2Ni45gFvqz4a7DvRBmn.").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").hasRole("USER")
                .antMatchers("/resources/**").permitAll()
                .antMatchers("**/qrCodes/**").permitAll()
                .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }


}
