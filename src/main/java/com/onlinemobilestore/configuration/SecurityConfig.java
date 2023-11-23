package com.onlinemobilestore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoDetailsService();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable()).headers().disable()
                .authorizeHttpRequests((auth) -> {
//                    auth.requestMatchers("/css/**","/fonts/**","/images/**","/js/**","/vendor/**").permitAll();
//                    auth.requestMatchers("/homepage","/register","/admin/**").permitAll();
//                    auth.requestMatchers("/cart","/checkout","/myfavoriteproduct").authenticated();
                    auth.requestMatchers("/**").permitAll();
               })
                .formLogin((login) ->{
                    login.loginPage("/loginpage");
                    login.loginProcessingUrl("/perform_login");
                    login.defaultSuccessUrl("/homepage",true);
                    login.failureUrl("/loginpageerr");
                    login.usernameParameter("email");
                    login.passwordParameter("password");
                    login.permitAll();
                })
                .oauth2Login((oauth2) ->{
                    oauth2.loginPage("/loginpage");
                    oauth2.defaultSuccessUrl("/homepage",true);
                    oauth2.failureUrl("/loginpageerr");
                    oauth2.permitAll();
                })
                .logout((logout) -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/homepage");
                    logout.clearAuthentication(true);
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("JSESSIONID");
                })
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    @Configuration
//    public class WebConfig implements WebMvcConfigurer {
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/admin/**")
//                    .allowedOrigins("http://localhost:3000")
//                    .allowedMethods("GET", "POST", "PUT", "DELETE")
//                    .allowedHeaders("Origin", "Content-Type", "Accept");
//        }
//    }

}
