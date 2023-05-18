package com.infodev.sanimaotp.configurations;

import com.infodev.sanimaotp.services.utils.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String[] PUBLIC_MATCHERS =
            {
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/newUser",
                    "/forgetPassword",
                    "/login",
                    "**/uploads/**",
                    "/assets/**",
                    "/api/**",
                    "**/qrCodes/**",
                    "/view/**",
                    "**/mobileApps/**",
                    "/resources/**",
                    "/qrCodes/**",
                    "/download/qr/**"
            };


    @Autowired
    @Qualifier("getUserDetails")
    UserDetailsService userDetailsService;

    @Autowired
    private SecurityHandler securityHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated();


        http
                .csrf().disable()
                .authorizeRequests()
                .and()
                .formLogin().loginPage("/login").permitAll().failureUrl("/login?error")
                .successHandler(securityHandler)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    private BCryptPasswordEncoder   passwordEncoder() {
        return SecurityUtils.passwordEncoder();
    }
}
