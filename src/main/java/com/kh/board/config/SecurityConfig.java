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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations() ).permitAll()
//                                .mvcMatchers(HttpMethod.GET,"/", "/signup", "/{slug}", "/{slug}/post/{postId}")
//                                .permitAll()
//                                .anyRequest().authenticated())
//                .formLogin().and().cors().and().csrf().disable()
//                .logout()
//                .logoutSuccessUrl("/")
//                .and().build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests().anyRequest().fullyAuthenticated();
//        http.authorizeRequests().antMatchers("/login", "/", "/signup", "/{slug}", "/{slug}/post/{postId}").permitAll()
//                .anyRequest().authenticated()
//                .and().formLogin()
//                .usernameParameter("userId")
//                .permitAll()
//                .and()
//                .logout().
//                logoutSuccessUrl("/");
//        http.headers().frameOptions().sameOrigin();
//        return http.build();

        http.csrf().disable().authorizeRequests().antMatchers("/login", "/", "/signup", "/{slug}", "/{slug}/post/{postId}").permitAll();
        http.authorizeRequests().anyRequest().fullyAuthenticated()
                .and().formLogin()
                .permitAll()
                .and()
                .logout().
                logoutSuccessUrl("/");
        http.headers().frameOptions().sameOrigin();
        return http.build();


    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository
                .findById(username)
                .map(UserDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. 사용자 이름 : " + username));
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/assets/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
