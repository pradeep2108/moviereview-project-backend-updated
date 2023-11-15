package com.reelreview.movieproject.configuration;

import com.reelreview.movieproject.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class WebSecurityConfig {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

    http.sessionManagement((config)-> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests((request)->{
        request.requestMatchers(HttpMethod.POST,"/api/reelreview/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/reelreview/public/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/reelreview/admin/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
               .anyRequest().authenticated();
    });


        http.addFilterBefore(new JWTAuthenticationFilter(userDataService, jwtTokenHelper), UsernamePasswordAuthenticationFilter.class);
            http.csrf((csrf)->csrf.disable());
            http.formLogin(Customizer.withDefaults());
            http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
