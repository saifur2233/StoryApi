package com.example.storyapi.security;

import com.example.storyapi.Filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;
    @Value("${apiPrefix}")
    private String urlPrefix;
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));


        http.cors().configurationSource(request -> corsConfiguration).and().csrf()
                .disable()
                .authorizeRequests()
                .antMatchers( urlPrefix+"/signup",urlPrefix+"/signin",urlPrefix+"/signout")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers( urlPrefix+"/stories",urlPrefix+"/stories/search/{id}",urlPrefix+"/stories/{email}")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers( urlPrefix+"/users", urlPrefix+"/users/{id}", urlPrefix+"/verifyuser")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .deleteCookies("lexus")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
