package com.tmquang.score.security;

import com.tmquang.score.enums.ERole;
import com.tmquang.score.security.jwt.AuthEntryPointJwt;
import com.tmquang.score.security.jwt.AuthTokenFilter;
import com.tmquang.score.security.services.CustomUserDetailsService;
import com.tmquang.score.security.services.UserPrincipal;
import com.tmquang.score.security.services.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
        (securedEnabled = true,
                jsr250Enabled = true,
                prePostEnabled = true) // by default
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

//  @Override
//  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//  }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

//  @Bean
//  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.cors().and().csrf().disable()
//      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//      .antMatchers("/api/test/**").permitAll()
//      .anyRequest().authenticated();
//
//    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//  }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/user/signin").permitAll()
                                .requestMatchers("/user/verifyToken").permitAll()
                                .requestMatchers("/department/**").hasAuthority(String.valueOf(ERole.ADMIN))
                                .requestMatchers("/year/create", "/year/update/**", "/year/delete/**").hasAuthority(String.valueOf(ERole.ADMIN))
                                .requestMatchers("/year/**").permitAll()
                                .requestMatchers("/semester/create", "/semester/update/**", "/semester/delete/**").hasAuthority(String.valueOf(ERole.ADMIN))
                                .requestMatchers("/subject/**").hasAuthority(String.valueOf(ERole.ADMIN))
                                .requestMatchers("/schedule/create", "/schedule/update/**", "/schedule/delete/**").hasAnyAuthority(String.valueOf(ERole.ADMIN), String.valueOf(ERole.TEACHER), String.valueOf(ERole.STAFF))
                                .requestMatchers("/enrollment/**").hasAnyAuthority(String.valueOf(ERole.ADMIN),String.valueOf(ERole.STAFF), String.valueOf(ERole.STUDENT))
                                .requestMatchers("/score/**", "/schedule/**").hasAnyAuthority(String.valueOf(ERole.ADMIN), String.valueOf(ERole.MANAGER), String.valueOf(ERole.STAFF), String.valueOf(ERole.TEACHER), String.valueOf(ERole.STUDENT))
                                .requestMatchers("/major/create", "/major/update", "/major/delete").hasAuthority(String.valueOf(ERole.ADMIN))
                                .requestMatchers("/student/create", "/student/update", "/student/delete").hasAuthority(String.valueOf(ERole.ADMIN))
                                .requestMatchers("/major/all", "major/details/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}