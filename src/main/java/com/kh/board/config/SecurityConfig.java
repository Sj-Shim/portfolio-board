package com.kh.board.config;

import com.kh.board.domain.User;
import com.kh.board.dto.UserDto;
import com.kh.board.repository.UserRepository;
import com.kh.board.security.BoardPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().disable();
//        http.formLogin().loginPage("/u/login").permitAll();
////        http.httpBasic().disable()
////                .csrf().disable()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
////                .authorizeRequests()
////                .antMatchers("/u/login", "/u/signup", "/", "/api").permitAll()
////                .anyRequest().authenticated();
//        http
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                .mvcMatchers(HttpMethod.GET,"/")
//                                .permitAll()
//                                .anyRequest().authenticated())
//                .formLogin().and()
//                .logout()
//                .logoutSuccessUrl("/");
//
//    }
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/**", "/js/**", "/assets/**");
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .mvcMatchers(HttpMethod.GET,"/", "/{slug}", "/{slug}/{postId}", "/css/**", "/js/**", "/assets/**")
                                .permitAll()
                                .anyRequest().authenticated())
                .formLogin().and()
                .logout()
                .logoutSuccessUrl("/")
                .and().build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository
                .findByUserId(username)
                .map(UserDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. 사용자 이름 : " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Autowired UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository
//                .findByUserId(username)
//                .map(UserDto::from)
//                .map(BoardPrincipal::from)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. 사용자 이름 : " + username));
//    }

}
