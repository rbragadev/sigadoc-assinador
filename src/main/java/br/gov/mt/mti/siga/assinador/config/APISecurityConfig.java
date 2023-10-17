//package br.gov.mt.mti.siga.assinador.config;
//
//import br.gov.mt.mti.siga.assinador.filter.APIKeyAuthFilter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//
///**
// * Configuration para Security Provider
// *
// * @author Ricardo Santos
// */
//@Configuration
//@EnableWebSecurity
//@Order(1)
//public class APISecurityConfig {
//
//    @Value("${siga.assinador.client.x-api-key-name}")
//    private String principalRequestHeader;
//
//    @Value("${siga.assinador.client.x-api-key}")
//    private String principalRequestValue;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
//        filter.setAuthenticationManager(authentication -> {
//            String principal = (String) authentication.getPrincipal();
//            if (!principalRequestValue.equals(principal)) {
//                throw new BadCredentialsException("The API key não foi encontrada ou não possui o valor esperado.");
//            }
//            authentication.setAuthenticated(true);
//            return authentication;
//        });
//        http.antMatcher("/api/**").
//                csrf().disable().
//                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
//                and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
//
//        return http.build();
//    }
//}