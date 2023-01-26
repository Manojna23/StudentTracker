package org.group.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

//    @Autowired
//    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService); // ONE WAY : Authentication with JPA.: https://www.youtube.com/watch?v=TNt3GHuayXs

//        auth.jdbcAuthentication()        // 2nd WAY: Authentication with inMemory/jdbc: https://www.youtube.com/watch?v=LKvrFltAgCQ
//                .dataSource(dataSource)  //by default - H2
//                .withDefaultSchema()  //this populates embedded h2 db with user and authority tables.
//                .withUser(
//                        User.withUsername("user")
//                        .password("pass")
//                        .roles("USER")
//                )
//                .withUser(
//                        User.withUsername("admin")
//                        .password("pass")
//                        .roles("ADMIN")
//                );

//        auth.jdbcAuthentication()   //Third WAY - JDBC mysql call
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled" +
//                        "from users" +
//                        "where username = ?")
//                .authoritiesByUsernameQuery("select username, authority" +
//                        "from authorities" +
//                        "where username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/students/").hasAnyRole("ADMIN", "USER")
                .antMatchers("/").permitAll()
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
