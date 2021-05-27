package de.cibek.mscasa.common.security.config;

import de.cibek.mscasa.common.security.CasaPasswordEncoder;
import de.cibek.mscasa.common.security.PaulCookieServices;
import de.cibek.mscasa.common.security.service.AuthenticatedUserService;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AuthenticatedUserService.class)
public class CommonSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false); //needed for PAUL-Cookie //todo remove when old casa not used anymore
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(getAllowedPaths().toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
                .and()
                .rememberMe().alwaysRemember(true).tokenValiditySeconds(Integer.MAX_VALUE).rememberMeServices(new PaulCookieServices("casa", userDetailsService))
                .and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CasaPasswordEncoder();
    }

    protected List<String> getAllowedPaths() {
        return new LinkedList<>(Arrays.asList("/actuator/*", "/internal-api/**", "/api/v1/test"));
    }
}
