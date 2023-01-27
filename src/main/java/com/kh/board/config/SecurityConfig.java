package com.kh.board.config;

import com.kh.board.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/signUp", "/", "/api").permitAll()
                .anyRequest().authenticated();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                .mvcMatchers(HttpMethod.GET,"/")
//                                .permitAll()
//                                .anyRequest().authenticated())
//                .formLogin().and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .and().build();
//    }

//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository) {
//        return username -> userRepository
//                .findByUserId(username)
//                .map(UserDto::from)
//                .map(BoardPrincipal::from)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. 사용자 이름 : " + username));
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
