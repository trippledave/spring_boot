/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cibek.mscasa.webrtc;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class MultiHttpSecurityConfig {
//
//    @Configuration
//    @Order(1)
//    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//        private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//        @Autowired
//        private CustomUserDetailsService userDetailsService;
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth
//                    .userDetailsService(userDetailsService)
//                    .passwordEncoder(bCryptPasswordEncoder);
//        }
//
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf()
//                    .disable()
//                    .antMatcher("/api/**")
//                    .authorizeRequests()
//                    .antMatchers("/api/v1/user/signup").permitAll()
//                    .anyRequest()
//                    .authenticated()
//                    .and()
//                    .exceptionHandling()
//                    .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                    .and()
//                    .addFilter(new ApiJWTAuthenticationFilter(authenticationManager()))
//                    .addFilter(new ApiJWTAuthorizationFilter(authenticationManager()))
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        }
//    }
//
//    @Order(2)
//    @Configuration
//    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//        private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//        @Autowired
//        private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
//
//        @Autowired
//        private CustomUserDetailsService userDetailsService;
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth
//                    .userDetailsService(userDetailsService)
//                    .passwordEncoder(bCryptPasswordEncoder);
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .cors()
//                    .and()
//                    .csrf()
//                    .disable()
//                    .authorizeRequests()
//                    .antMatchers("/").permitAll()
//                    .antMatchers("/login").permitAll()
//                    .antMatchers("/signup").permitAll()
//                    .antMatchers("/dashboard/**").hasAuthority("ADMIN")
//                    .anyRequest()
//                    .authenticated()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .failureUrl("/login?error=true")
//                    .usernameParameter("email")
//                    .passwordParameter("password")
//                    .successHandler(customAuthenticationSuccessHandler)
//                    .and()
//                    .logout()
//                    .permitAll()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutSuccessHandler(new CustomLogoutSuccessHandler())
//                    .deleteCookies("JSESSIONID")
//                    .logoutSuccessUrl("/")
//                    .and()
//                    .exceptionHandling();
//        }
//
//        @Override
//        public void configure(WebSecurity web) throws Exception {
//            web.ignoring().antMatchers(
//                    "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**",
//                    "/resources/static/**", "/css/**", "/js/**", "/img/**", "/fonts/**",
//                    "/images/**", "/scss/**", "/vendor/**", "/favicon.ico", "/auth/**", "/favicon.png",
//                    "/v2/api-docs", "/configuration/ui", "/configuration/security", "/swagger-ui.html",
//                    "/webjars/**", "/swagger-resources/**", "/swagge‌​r-ui.html", "/actuator",
//                    "/actuator/**");
//        }
//    }
}
