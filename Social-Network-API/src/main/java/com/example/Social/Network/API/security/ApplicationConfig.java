package com.example.Social.Network.API.security;

import com.example.Social.Network.API.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer   {

    @Autowired
    private final UserRepo userRepo;
//    @Autowired
//    private TokenInterceptor tokenInterceptor;
    public ApplicationConfig( UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Can not fine the user"));
    }
//    it is used to verify user credentials during the authentication process
    @Bean
    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider retrieves the user’s credentials, such as username and password, from the database and compares them to the credentials provided by the user during login. If the credentials match,
//        the provider creates an Authentication object representing the authenticated user.
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
//        BCryptPasswordEncoder is a concrete implementation of this the PasswordEncoder interface that use the Bcrypt algorithm for pw hashing
        return new BCryptPasswordEncoder();
    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
//    }
}
