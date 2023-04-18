package LGCNS.RestfulWebService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.PushBuilder;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().headers().frameOptions().disable();

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                 User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("userPass"))
                .roles("user")
                .build());
        manager.createUser(
                User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("adminPass"))
                .roles("user", "admin")
                .build());
        manager.createUser(
                User.withUsername("kenneth")
                .password(bCryptPasswordEncoder.encode("test1234"))
                .roles("user")
                .build());
        return manager;
    }

    @Bean
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }

}
